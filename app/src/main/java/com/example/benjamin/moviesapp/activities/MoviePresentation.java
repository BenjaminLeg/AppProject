package com.example.benjamin.moviesapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.benjamin.moviesapp.elements.Movie;
import com.example.benjamin.moviesapp.tasks.GetMoviesParseTask;
import com.example.benjamin.moviesapp.adaptors.MovieAdaptor;
import com.example.benjamin.moviesapp.enums.MovieOption;
import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MoviePresentation extends AppCompatActivity implements OnLoadingListener {

    private Movie movie;
    private List<Movie> moviesList;
    private GetMoviesParseTask mMovieParser = null;
    private String id = "";
    private static Context context;
    private RecyclerView recyclerView;
    private VideoView videoView;
    private ImageButton btnPlayPause;
    private ProgressDialog dialog;
    private String videoURL = "https://www.youtube.com/embed/827FNDpQWrQ";

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm =Realm.getDefaultInstance();
        setContentView(R.layout.activity_movie_presentation);
        context = this;
        Bundle extras = getIntent().getExtras();
        this.id= extras.getString("EXTRA_ID");

        moviesList = new ArrayList<Movie>();
        if(this.id.startsWith("add")){
            RealmResults<Movie> movieLocal=realm.where(Movie.class).equalTo("id",this.id).findAll();
            moviesList.addAll(movieLocal);
            recyclerView = findViewById(R.id.moviePresentation);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            Toast.makeText(ActivityTrending.context,"Before Movie adaptator", Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(new MovieAdaptor(moviesList.get(0)));
        }
        else{
            mMovieParser = new GetMoviesParseTask(this,MovieOption.SEARCH, moviesList, id, 0 );
            mMovieParser.execute();
        }
    }

    @Override
    public void loadChange(Boolean success) {
        recyclerView = findViewById(R.id.moviePresentation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(success||moviesList.get(0).getId().startsWith("add")) {
            recyclerView.setAdapter(new MovieAdaptor(moviesList.get(0)));
        }
        else {
            Snackbar.make(this.recyclerView, "No Internet Connexion", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoadMore() {

    }

}
