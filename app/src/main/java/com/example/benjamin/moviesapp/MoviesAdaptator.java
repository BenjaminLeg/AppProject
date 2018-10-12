package com.example.benjamin.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.service.chooser.ChooserTarget;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
        private TextView movieDate;
        private TextView movieLanguage;
        private TextView movieRate;
        private ImageView moviePoster;
        private Button movieShare;
        private ImageButton movieFavorite;

        public ViewHolder (View v){
            super(v);
            //TODO correct the different id to make the findViewById working => maybe use ButterKnife ?
            this.moviePlot=v.findViewById(R.id.moviePlot);
            this.movieTitle=v.findViewById(R.id.movieTitle);
          //  this.movieDate=v.findViewById(R.id.movieDate);
           // this.movieRate=v.findViewById(R.id.movieRate);
           // this.movieLanguage=v.findViewById(R.id.movieLanguage);
            this.moviePoster=v.findViewById(R.id.moviePoster);
            this.movieShare=v.findViewById(R.id.shareButton);
           // this.movieFavorite=v.findViewById(R.id.favoriteButton);

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
       // holder.movieDate.setText(listMovies.get(i).getRelease_date());
        holder.moviePlot.setText(listMovies.get(i).getOverview());

     /*   switch(listMovies.get(i).getOriginal_language()){
            case "en":
                holder.movieLanguage.setText("English");
                break;
            case "fr":
                holder.movieLanguage.setText("French");
                break;
            default :
                holder.movieLanguage.setText("Unknown language");

        }*/
        //holder.movieRate.setText("Vote: "+listMovies.get(i).getVoteAvg());
        holder.movieShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.context, SendSms.class);
                MainPage.context.startActivity(intent);

            }
        });

      /*  holder.movieFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filename="favoris";
                String content=listMovies.get(i).getId();
                FileOutputStream stream;
                try{
                    stream = v.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    stream.write(content.getBytes());
                    stream.close();
                    Log.e("DebugPerso","favori ajouté");
                    holder.movieFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/
    }
    

    @Override
    public int getItemCount() {

        return listMovies.size();
    }


}
