package com.example.benjamin.moviesapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjamin.moviesapp.R;
import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.interfaces.OnResponseListener;
import com.example.benjamin.moviesapp.tasks.GetTrailerTask;
import com.squareup.okhttp.Response;

public class PlayerActivity extends AppCompatActivity implements OnResponseListener {


    private GetTrailerTask playerParser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerParser = new GetTrailerTask(getIntent().getExtras().getString("EXTRA_ID"),this);
        playerParser.execute();

    }

    @Override
    public void onReponse(Response response) {
        TextView text = findViewById(R.id.text);
        text.setText("It worked");
    }
}
