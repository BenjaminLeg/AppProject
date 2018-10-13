package com.example.benjamin.moviesapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetMoviesParseTask extends AsyncTask<Void,Void,Void> {
    private static final String API_KEY_1 = "c34c9bc397499bb9c5cf79c5f73350d6";
    private static final String myURL = "https://api.themoviedb.org/3/";
    private static final String optionTrending = "trending/movie/day";
    private static final String optionMovie = "movie/";
    private static final String TAG = MainPage.class.getSimpleName();
    private static String id = "";
    private static String option = "";
    public static ProgressDialog progress;


    private final OnLoadingListener listener;
    public List<Movie> movieList;

    public GetMoviesParseTask(OnLoadingListener listener, List<Movie> movieList) {
        this.listener = listener;
        this.movieList = movieList;
        this.option = optionTrending;
        this.id="";
    }
    public GetMoviesParseTask(OnLoadingListener listener, List<Movie> movieList, String id) {
        this.listener = listener;
        this.movieList = movieList;
        this.option = optionMovie;
        this.id = id;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(option == optionTrending)
            progress = new ProgressDialog(MainPage.context);
        else
            progress = new ProgressDialog(MoviePresentation.getContext());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }
    @Override
    protected Void doInBackground(Void... params) {
            String url = myURL + option + id + "?" + "api_key=" + API_KEY_1;
            String callResult = makeServiceCall(url);

        if (callResult != null) {
            if(option == optionTrending){
                TrendingBehavior(callResult);
            }
            else{
                SearchBehavior(callResult);
            }
        }

        return null;
        }

    private void SearchBehavior(String callResult) {
        try {
            JSONObject movies = new JSONObject(callResult);

            // looping through All Contacts

                String id = movies.getString("id");
                String title = movies.getString("title");
                String release_date = movies.getString("release_date");
                String original_language = movies.getString("original_language");
                String overview = movies.getString("overview");
                String posterSrc = movies.getString("poster_path");
                String voteAvg = movies.getString("vote_average");
                Movie movieElm=new Movie(id,title,release_date,original_language,overview,posterSrc,voteAvg);


                // adding contact to contact list
                movieList.add(movieElm);
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());

        }
    }

    private String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    @Override
    protected void onPostExecute(Void aBoolean) {
        listener.loadChange(aBoolean);
        progress.dismiss();
    }

    private void TrendingBehavior(String callResult){
        try {

            JSONObject jsonObj = new JSONObject(callResult);

            // Getting JSON Array node
            JSONArray movies = jsonObj.getJSONArray("results");

            // looping through All Contacts
            for (int i = 0; i < movies.length(); i++) {
                JSONObject c = movies.getJSONObject(i);
                String id = c.getString("id");
                String title = c.getString("title");
                String release_date = c.getString("release_date");
                String original_language = c.getString("original_language");
                String overview = c.getString("overview");
                String posterSrc = c.getString("poster_path");
                String voteAvg = c.getString("vote_average");
                Movie movieElm=new Movie(id,title,release_date,original_language,overview,posterSrc,voteAvg);


                // adding contact to contact list
                movieList.add(movieElm);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());

        }

    }
}
