package com.example.benjamin.moviesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* Classe inspiré par: https://stackoverflow.com/questions/18953632/how-to-set-image-from-url-for-imageview
* */

public class MoviePosterLoadTask extends AsyncTask <Void, Void, Bitmap>{

    private String url;
    private ImageView imageView;

    public MoviePosterLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }
    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}
