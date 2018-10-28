package com.example.benjamin.moviesapp.adaptors;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjamin.moviesapp.R;

import java.io.File;
import com.example.benjamin.moviesapp.activities.MoviePresentation;
import com.example.benjamin.moviesapp.activities.PlayerActivity;
import com.example.benjamin.moviesapp.elements.FavoriRealmObj;
import com.example.benjamin.moviesapp.elements.Movie;
import com.example.benjamin.moviesapp.tasks.MoviePosterLoadTask;

import io.realm.Realm;
import io.realm.RealmResults;

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
        private Button actionMovieRate;
        private Button actionSeeTrailer;
        private ImageButton movieFavorite;
        private static Context context;
        private static PackageManager packet;


        public ViewHolder(View v) {

            super(v);
            context = v.getContext();
            this.moviePlot = v.findViewById(R.id.moviePlotPresentation);
            this.movieTitle = v.findViewById(R.id.movieTitlePresentation);
            this.moviePoster = v.findViewById(R.id.moviePosterPresentation);
            this.movieShare = v.findViewById(R.id.shareButtonPresentation);
            this.movieLanguage = v.findViewById(R.id.movieLanguagePresentation);
            this.movieRate = v.findViewById(R.id.movieRatePresentation);
            this.movieDate = v.findViewById(R.id.movieDatePresentation);
            this.actionMovieRate = v.findViewById(R.id.RateButtonPresentation);
            this.movieFavorite=v.findViewById(R.id.favoriteButton);
            this.actionSeeTrailer = v.findViewById(R.id.buttonVideo);
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
        if(movie.getId().startsWith("add")){
            viewHolder.moviePoster.setImageResource(R.drawable.bobinefilm);
        }else{
            String posterURL="https://image.tmdb.org/t/p/w500"+movie.getPoster_path();
            MoviePosterLoadTask poster=new MoviePosterLoadTask(posterURL,viewHolder.moviePoster);
            poster.execute();
        }
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.moviePlot.setText(movie.getOverview());
        switch (movie.getOriginal_language().toLowerCase()){
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
                String url="";
                if(!(movie.getId().startsWith("add"))) {
                    url = "https://www.themoviedb.org/movie/";
                }
                intent.putExtra(Intent.EXTRA_TEXT, "Please check this movie : "+ url + movie.getId() );
                intent.setType("text/plain");
                Intent chooser = Intent.createChooser(intent, title);
                v.getContext().startActivity(chooser);

            }
        });
        viewHolder.actionMovieRate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog rateMessage = new Dialog(MoviePresentation.getContext());
                rateMessage.setTitle("Hello");
                rateMessage.show();
            }
        });

        Realm realm=Realm.getDefaultInstance();
        RealmResults<FavoriRealmObj> favorisLocaux=realm.where(FavoriRealmObj.class).equalTo("movieId",movie.getId()).findAll();
        if(!favorisLocaux.isEmpty()){
            viewHolder.movieFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            viewHolder.movieFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.where(FavoriRealmObj.class)
                            .equalTo("movieId", movie.getId())
                            .findAll()
                            .get(0)
                            .deleteFromRealm();
                    realm.commitTransaction();
                    viewHolder.movieFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                }
            });
        }else{
                viewHolder.movieFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                viewHolder.movieFavorite.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Realm realm=Realm.getDefaultInstance();
                        realm.beginTransaction();
                        FavoriRealmObj favori=realm.createObject(FavoriRealmObj.class);
                        favori.setMovieId(movie.getId());
                        favori.setTitle(movie.getTitle());
                        favori.setOriginal_language(movie.getOriginal_language());
                        favori.setOverview(movie.getOverview());
                        favori.setPosterSrc(movie.getPosterSrc());
                        favori.setRelease_date(movie.getRelease_date());
                        favori.setVoteAvg(movie.getVoteAvg());
                        favori.setHomePageUrl(movie.getHomePageUrl());
                        realm.commitTransaction();
                        viewHolder.movieFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                    }
                });
        }




        viewHolder.actionSeeTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviePresentation.getContext(),PlayerActivity.class);
                intent.putExtra("EXTRA_ID", movie.getId());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
