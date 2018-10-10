package com.example.benjamin.moviesapp;

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

public class GetMoviesParseTask extends AsyncTask<Void,Void,Void> {
    private static final String API_KEY_1 = "c34c9bc397499bb9c5cf79c5f73350d6";
    private static final String myURL = "https://api.themoviedb.org/3/";
    private static final String optionTrending = "trending/movie/day?";
    private static final String TAG = MainScrolling.class.getSimpleName();

    private final OnLoadingListener listener;
    public ArrayList<HashMap<String,String>> movieList;

    public GetMoviesParseTask(OnLoadingListener listener, ArrayList movieList) {
        this.listener = listener;
        this.movieList = movieList;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(MainScrolling.context, "Json Data is downloading", Toast.LENGTH_LONG).show();
    }
    @Override
    protected Void doInBackground(Void... params) {
            String url = myURL + optionTrending + "api_key=" + API_KEY_1;
            String callResult = makeServiceCall(url);

        Log.e(TAG, "Response from url: " + callResult);
        if (callResult != null) {
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
                    // tmp hash map for single contact
                    HashMap<String, String> movie = new HashMap<>();

                    // adding each child node to HashMap key => value
                    movie.put("id", id);
                    movie.put("title", title);
                    movie.put("release_date", release_date);
                    movie.put("original_language", original_language);
                    movie.put("overview", overview);
                    movie.put("posterSrc",posterSrc);
                    movie.put("voteAvg",voteAvg);


                    // adding contact to contact list
                    movieList.add(movie);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }

        return null;
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
    }
}
