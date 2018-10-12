package com.example.benjamin.moviesapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPage extends AppCompatActivity implements OnLoadingListener {
    public static Context context;
    private GetMoviesParseTask mMovieParser = null;
    private DrawerLayout drawerLayout;

    List<Movie> moviesList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton addBtn;
        setSupportActionBar(toolbar);
        context = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.isChecked();

                switch (menuItem.getItemId()) {
                    case R.id.nav_local:
                        // TO DO : Make new view to the Local Movies Page
                        return true;

                    case R.id.nav_favorites:
                        // TO DO : Make new view to the Favorites Page
                        return true;

                    case R.id.nav_explore:
                        // TO DO : Make new view to the Explore Page
                        return true;

                    case R.id.nav_about:
                        // TO DO : Make new view to the About Page
                        return true;

                    default:
                        drawerLayout.closeDrawers();
                        return true;
                }
            }
        });

        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        moviesList = new ArrayList<>();
        moviesList=new ArrayList<Movie>();
        mMovieParser = new GetMoviesParseTask(this, moviesList);
        mMovieParser.execute();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Button share = findViewById(R.id.shareButton);
        FloatingActionButton addBtn;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename="favoris";
                Movie content=new Movie;
                FileOutputStream stream;
                try{
                    stream = v.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    stream.write(content.getBytes());
                    stream.close();
                    Log.e("DebugPerso","Film ajouté");

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendSms();
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

   /* private void sendSms() {
        Intent share = new Intent(this, SendSms.class);
    }*/

    @Override
    public void loadChange(Void success) {
        Toast.makeText(this, "Loading complete", Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MoviesAdaptator(moviesList));


    }

}
