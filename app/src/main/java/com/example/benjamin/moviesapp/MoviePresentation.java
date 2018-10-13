package com.example.benjamin.moviesapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoviePresentation extends AppCompatActivity implements OnLoadingListener {

    private Movie movie;
    private List<Movie> moviesList;
    private GetMoviesParseTask mMovieParser = null;
    private String id = "";
    private static Context context;
    private RecyclerView recyclerView;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_presentation);
        context = this;
        Bundle extras = getIntent().getExtras();
        this.id= extras.getString("EXTRA_ID");

        moviesList = new ArrayList<Movie>();
        mMovieParser = new GetMoviesParseTask(this, moviesList, id );
        mMovieParser.execute();
    }

    @Override
    public void loadChange(Void success) {
        recyclerView = findViewById(R.id.moviePresentation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MovieAdaptor(moviesList.get(0)));
    }

}
