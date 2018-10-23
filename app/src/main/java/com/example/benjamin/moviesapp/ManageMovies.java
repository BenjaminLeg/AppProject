package com.example.benjamin.moviesapp;

import android.util.Log;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class ManageMovies {
    private static final String API_KEY_1="dfebda7a85afbbabd15fbb9651fac50c";
    private static final String API_KEY_2=""; //Ben key

    private TmdbApi APIInstance;
    private TmdbMovies movies;

    public ManageMovies(){
        this.APIInstance=new TmdbApi(API_KEY_1);
        this.movies=APIInstance.getMovies();
    }

    public List<MovieDb> getPopular(){
        return movies.getPopularMovies("fr",1).getResults();
    }

    public List<MovieDb> getNowPlaying(){
        return movies.getNowPlayingMovies("fr",1,"France").getResults();
    }

    public List<MovieDb> getTrending(){
        return movies.getTopRatedMovies("fr",1).getResults();
    }

    /*public List<MovieDb> getRandom(){
        int randomPage=
        return movies.getPopularMovies();
    }*/

}
