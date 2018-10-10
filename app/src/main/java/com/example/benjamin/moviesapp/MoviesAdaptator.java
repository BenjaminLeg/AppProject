package com.example.benjamin.moviesapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MoviesAdaptator extends RecyclerView.Adapter<MoviesAdaptator.ViewHolder> {

    private ArrayList<HashMap<String,String>> listMovies;



    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView moviePlot;
        private TextView movieTitle;
        private TextView movieDate;
        private TextView movieLanguage;
        private TextView movieRate;
        private ImageView moviePoster;

        public ViewHolder (View v){
            super(v);
            //TODO correct the different id to make the findViewById working => maybe use ButterKnife ?
            this.moviePlot=v.findViewById(R.id.moviePlot);
            this.movieTitle=v.findViewById(R.id.movieTitle);
            this.movieDate=v.findViewById(R.id.movieDate);
            this.movieRate=v.findViewById(R.id.movieRate);
            this.movieLanguage=v.findViewById(R.id.movieLanguage);
            this.moviePoster=v.findViewById(R.id.moviePoster);

        }
    }

    public MoviesAdaptator(ArrayList<HashMap<String,String>> moviesResults){
        listMovies=moviesResults;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String posterURL="https://image.tmdb.org/t/p/w500"+listMovies.get(i).get("posterSrc").toString();
        MoviePosterLoadTask poster=new MoviePosterLoadTask(posterURL,holder.moviePoster);
        poster.execute();
        holder.movieTitle.setText(listMovies.get(i).get("title").toString());
        holder.movieDate.setText(listMovies.get(i).get("release_date").toString());
        holder.moviePlot.setText(listMovies.get(i).get("overview").toString());
        holder.movieRate.setText(listMovies.get(i).get("vote_average"));
        switch(listMovies.get(i).get("original_language")){
            case "en":
                holder.movieLanguage.setText("English");
                break;
            case "fr":
                holder.movieLanguage.setText("French");
                break;
            default :
                holder.movieLanguage.setText("Unknown language");

        }
        holder.movieRate.setText("vote average: "+listMovies.get(i).get("voteAvg").toString());
    }

    @Override
    public int getItemCount() {

        return listMovies.size();
    }


}
