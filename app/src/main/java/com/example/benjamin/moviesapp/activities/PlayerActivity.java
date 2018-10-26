package com.example.benjamin.moviesapp.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.benjamin.moviesapp.R;
import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.interfaces.OnResponseListener;
import com.example.benjamin.moviesapp.tasks.GetTrailerTask;
import com.fasterxml.jackson.core.JsonParser;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerActivity extends AppCompatActivity implements OnResponseListener {

    private static final String LOG_TAG = "Playing Video";
    MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;
    private GetTrailerTask playerParser = null;
    private MediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerParser = new GetTrailerTask(getIntent().getExtras().getString("EXTRA_ID"),this);
        playerParser.execute();


        mMediaSession = new MediaSessionCompat(this, LOG_TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
       // mMediaSession.setMetadata();

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCalback());

        MediaControllerCompat mediaController = new MediaControllerCompat(this, mMediaSession);
        MediaControllerCompat.setMediaController(this, mediaController);

      //  mMediaPlayer = MediaPlayer.create(this, Settings.System.CONTENT_URI);
     //   mMediaPlayer.start();


        VideoView videoView = findViewById(R.id.videoPlayer);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Settings.System.DEFAULT_RINGTONE_URI);
        videoView.isPlaying();

    }

    @Override
    public void onReponse(Response response) throws IOException {
        TextView text = findViewById(R.id.text);
        InputStreamReader reader = new InputStreamReader(response.body().byteStream());
        BufferedReader buffer = new BufferedReader(reader);
    }
}
