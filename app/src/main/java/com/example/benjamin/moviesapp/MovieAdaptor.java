package com.example.benjamin.moviesapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.ViewHolder> {

    private Movie movie;
    private File favoris;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView moviePlot;
        private TextView movieTitle;
        private TextView movieLanguage;
        private TextView movieRate;
        private TextView movieDate;
        private ImageView moviePoster;
        private Button movieShare;
        private ImageButton movieFavorite;
        private static Context context;
        private static PackageManager packet;


        public ViewHolder(View v) {

            super(v);
            context = v.getContext();
            Intent intent = new Intent(Intent.ACTION_SEND);
            String title = v.getResources().getString(R.string.chooser_title);
            this.moviePlot = v.findViewById(R.id.moviePlotPresentation);
            this.movieTitle = v.findViewById(R.id.movieTitlePresentation);
            this.moviePoster = v.findViewById(R.id.moviePosterPresentation);
            this.movieShare = v.findViewById(R.id.shareButtonPresentation);
            this.movieLanguage = v.findViewById(R.id.movieLanguagePresentation);
            this.movieRate = v.findViewById(R.id.movieRatePresentation);
            this.movieDate = v.findViewById(R.id.movieDatePresentation);
            //this.movieFavorite=v.findViewById(R.id.favoriteButton);

        }

    }

    public MovieAdaptor(Movie movieResult){
        this.movie=movieResult;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie, viewGroup, false);
        favoris = new File(viewGroup.getContext().getFilesDir(), "favoris");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        String posterURL="https://image.tmdb.org/t/p/w500"+movie.getPoster_path();
        MoviePosterLoadTask poster=new MoviePosterLoadTask(posterURL,viewHolder.moviePoster);
        poster.execute();
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.moviePlot.setText(movie.getOverview());
        switch (movie.getOriginal_language()){
            case "en":
                viewHolder.movieLanguage.setText("English");
                break;
            case "fr":
                viewHolder.movieLanguage.setText("French");
                break;
            default :
                viewHolder.movieLanguage.setText("Unknown");
                break;
        }
        viewHolder.movieRate.setText(movie.getVoteAvg()+ "/10");
        viewHolder.movieDate.setText(movie.getRelease_date());
        viewHolder.movieShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String title = v.getResources().getString(R.string.chooser_title);
                String url = "https://www.themoviedb.org/movie/";
                intent.putExtra(Intent.EXTRA_TEXT, "Please check this movie : "+ url + movie.getId() );
                intent.setType("text/plain");
                Intent chooser = Intent.createChooser(intent, title);
                v.getContext().startActivity(chooser);

            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
