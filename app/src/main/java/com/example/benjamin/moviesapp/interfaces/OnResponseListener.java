package com.example.benjamin.moviesapp.interfaces;

import com.squareup.okhttp.Response;

import java.io.IOException;

public interface OnResponseListener {
    public void onReponse(Response response) throws IOException;
}
