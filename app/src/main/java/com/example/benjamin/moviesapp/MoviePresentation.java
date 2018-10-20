package com.example.benjamin.moviesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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
        setContentView(R.layout.activity_movie_presentation);
        context = this;
        Bundle extras = getIntent().getExtras();
        this.id= extras.getString("EXTRA_ID");

        moviesList = new ArrayList<Movie>();
        mMovieParser = new GetMoviesParseTask(this, moviesList, id );
        mMovieParser.execute();

   /*     videoView = findViewById(R.id.videoView);
        btnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MoviePresentation.this);
                dialog.setMessage("Please wait...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        try{
            if(!videoView.isPlaying()){
                Uri uri = Uri.parse(videoURL);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btnPlayPause.setImageResource(R.drawable.ic_play);
                    }
                });
            }
            else{
                    videoView.pause();
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
            }


        }
        catch(Exception e){

        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                dialog.dismiss();
                mp.setLooping(true);
                videoView.start();
            }
        });

*/


    }

    @Override
    public void loadChange(Boolean success) {
        recyclerView = findViewById(R.id.moviePresentation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(success) {
            recyclerView.setAdapter(new MovieAdaptor(moviesList.get(0)));
        }
        else {
            Snackbar.make(this.recyclerView, "No Internet Connexion", Snackbar.LENGTH_LONG).show();
        }
    }

}
