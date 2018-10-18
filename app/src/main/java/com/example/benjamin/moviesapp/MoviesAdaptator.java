package com.example.benjamin.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.service.chooser.ChooserTarget;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;


public class MoviesAdaptator extends RecyclerView.Adapter<MoviesAdaptator.ViewHolder> {

    private List<Movie> listMovies;
    private File favoris;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView moviePlot;
        private TextView movieTitle;
        private ImageView moviePoster;
        private Button movieShare;
        private Button movieExplore;
        private ImageButton movieFavorite;
        private static Context context;

        public ViewHolder (View v){
            super(v);
            context = v.getContext();
            this.moviePlot=v.findViewById(R.id.moviePlot);
            this.movieTitle=v.findViewById(R.id.movieTitle);
            this.moviePoster=v.findViewById(R.id.moviePoster);
            this.movieShare=v.findViewById(R.id.shareButton);
            this.movieExplore=v.findViewById(R.id.exploreButton);


        }
    }

    public MoviesAdaptator(List<Movie> moviesResults){
        listMovies=moviesResults;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_cell, viewGroup, false);
        favoris = new File(viewGroup.getContext().getFilesDir(), "favoris");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        String posterURL="https://image.tmdb.org/t/p/w500"+listMovies.get(i).getPoster_path();
        MoviePosterLoadTask poster=new MoviePosterLoadTask(posterURL,holder.moviePoster);
        poster.execute();
        holder.movieTitle.setText(listMovies.get(i).getTitle());
        holder.moviePlot.setText(listMovies.get(i).getOverview());
        holder.movieShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String title = v.getResources().getString(R.string.chooser_title);
                String url = "https://www.themoviedb.org/movie/";
                intent.putExtra(Intent.EXTRA_TEXT, "Please check this movie : "+ url + listMovies.get(i).getId() );
                intent.setType("text/plain");
                Intent chooser = Intent.createChooser(intent, title);
                v.getContext().startActivity(chooser);

            }
        });
        holder.movieExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = listMovies.get(i).getId();
                Intent intent = new Intent(v.getContext(), MoviePresentation.class);
                intent.putExtra("EXTRA_ID", id );
                v.getContext().startActivity(intent);
            }
        });
    };


    

    @Override
    public int getItemCount() {

        return listMovies.size();
    }


}
