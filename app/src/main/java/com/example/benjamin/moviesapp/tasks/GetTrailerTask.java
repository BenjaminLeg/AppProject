package com.example.benjamin.moviesapp.tasks;

import android.os.AsyncTask;

import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.interfaces.OnResponseListener;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class GetTrailerTask extends AsyncTask<Void,Void,Response> {

    private static final String API_KEY_1 = "c34c9bc397499bb9c5cf79c5f73350d6";
    private static final String myURL = "https://api.themoviedb.org/3/";
    private static String id;
    private final OnResponseListener listener;
    private static Boolean success = false;

    public GetTrailerTask(String id, OnResponseListener listener){
        this.id = id;
        this.listener = listener;
    }


    @Override
    protected Response doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Boolean reply = false;
        Response response = null;

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/"+this.id+"/videos?language=en-US&api_key="+API_KEY_1)
                .get()
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()){
                this.success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response aVoid) {
        if(aVoid != null) {
            try {
                listener.onReponse(aVoid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
