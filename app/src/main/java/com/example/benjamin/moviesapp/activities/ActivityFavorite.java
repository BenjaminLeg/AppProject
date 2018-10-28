package com.example.benjamin.moviesapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.benjamin.moviesapp.AddFilmFragment;
import com.example.benjamin.moviesapp.R;
import com.example.benjamin.moviesapp.adaptors.MoviesAdaptor;
import com.example.benjamin.moviesapp.elements.FavoriRealmObj;
import com.example.benjamin.moviesapp.elements.Movie;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityFavorite extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Context context;

    List<FavoriRealmObj> favoritesList;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    private MoviesAdaptor mAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton addBtn;
        setSupportActionBar(toolbar);
        context = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        favoritesList = new ArrayList<FavoriRealmObj>();
        RealmResults<FavoriRealmObj> favoriteLocal = realm.where(FavoriRealmObj.class).findAll();
        favoritesList.addAll(favoriteLocal);


        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);


        addBtn = this.findViewById(R.id.addBtn);
        addBtn.setVisibility(View.INVISIBLE);/*
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addDiallog = new AddFilmFragment();
                addDiallog.show(getSupportFragmentManager(), "add Movie");
            }
        });*/

        if (favoritesList.size() > 0) {

            recyclerView = (RecyclerView) findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new MoviesAdaptor();
            mAdapter.MoviesAdaptor2(favoritesList, recyclerView);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.isChecked();
        Intent intent;

        switch (menuItem.getItemId()) {
            case R.id.nav_local:
                intent = new Intent(this, ActivityLocal.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_favorites:
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_explore:
                intent = new Intent(this, ActivityTrending.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;

            case R.id.nav_discover:
                intent = new Intent(this, ActivityDiscover.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;


            default:
                drawerLayout.closeDrawers();
                return true;
        }

    }
}
