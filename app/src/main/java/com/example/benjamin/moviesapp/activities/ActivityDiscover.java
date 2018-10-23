package com.example.benjamin.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.benjamin.moviesapp.R;
import com.example.benjamin.moviesapp.adaptors.MoviesAdaptor;
import com.example.benjamin.moviesapp.elements.Movie;
import com.example.benjamin.moviesapp.enums.MovieOption;
import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.tasks.GetMoviesParseTask;

import java.util.ArrayList;
import java.util.List;


public class ActivityDiscover extends AppCompatActivity implements OnLoadingListener, NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    private GetMoviesParseTask mMovieParser = null;

    List<Movie> moviesList;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    private int page = 1;
    private MoviesAdaptor mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton addBtn;
        setSupportActionBar(toolbar);
        context = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        moviesList = new ArrayList<Movie>();
        mMovieParser = new GetMoviesParseTask(this,MovieOption.DISCOVER, moviesList, "" , 1);
        mMovieParser.execute();

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);



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
    }

    @Override
    public void loadChange(Boolean success) {

        if(success) {
            if(page == 1){
                recyclerView = (RecyclerView) findViewById(R.id.list);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter = new MoviesAdaptor(moviesList, recyclerView);
                mAdapter.setOnLoadingListener(this);
                recyclerView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();

        }
        else {
            Snackbar.make(this.recyclerView, "No Internet Connexion", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoadMore() {
        if(page <= 4) {
            page += 1;
            mMovieParser = new GetMoviesParseTask(this, MovieOption.DISCOVER, moviesList, "", page);
            mMovieParser.execute();
        }
        else {
            Toast.makeText(this, "You are at the bottom", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.isChecked();
        Intent intent;

        switch (menuItem.getItemId()) {
            case R.id.nav_local:
                Toast.makeText(ActivityTrending.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                //intent = new Intent(ActivityTrending.context,LocalMovies.class);
                //startActivity(intent);
                // TO DO : Make new view to the Local Movies Page
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_favorites:
                Toast.makeText(ActivityTrending.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                //intent = new Intent(ActivityTrending.context,FavoritePage.class);
                //startActivity(intent);
                // TO DO : Make new view to the Favorites Page
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_explore:
                intent = new Intent(this, ActivityTrending.class);
                startActivity(intent);
                // TO DO : Make new view to the Explore Page
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_discover:
                // TO DO : Make new view to the Explore Page
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_about:
                Toast.makeText(ActivityTrending.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                //intent = new Intent(ActivityTrending.context,AboutPage.class);
                //startActivity(intent);
                // TO DO : Make new view to the About Page
                drawerLayout.closeDrawers();
                return true;

            default:
                drawerLayout.closeDrawers();
                return true;
        }

    }

}
