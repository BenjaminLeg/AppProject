package com.example.benjamin.moviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MoviesAdaptator extends RecyclerView.Adapter<MoviesAdaptator.ViewHolder> {

    private List<MovieDb> movies;

    public MoviesAdaptator() {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView moviePlot;
        private TextView movieTitle;
        private TextView movieDate;
        private TextView movieRate;

        public ViewHolder (View v){
            super(v);
            //TODO correct the different id to make the findViewById working => maybe use ButterKnife ?
            this.moviePlot=v.findViewById(R.id.moviePlot);
            this.movieTitle=v.findViewById(R.id.movieTitle);
            this.movieDate=v.findViewById(R.id.movieDate);
            this.movieRate=v.findViewById(R.id.movieRate);


        }
    }

    public MoviesAdaptator(List<MovieDb> moviesResults){
        movies=moviesResults;

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
        holder.movieTitle.setText(movies.get(i).getTitle());
        holder.movieDate.setText(movies.get(i).getReleaseDate());
        holder.moviePlot.setText(movies.get(i).getOverview());
        holder.movieRate.setText(Float.toString(movies.get(i).getPopularity()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


}
