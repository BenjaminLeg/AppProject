package com.example.benjamin.moviesapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements OnLoadingListener {
    List<Movie> list;
    RecyclerView recyclerView;
    Context context;


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void loadChange(Boolean success) {
      /*  recyclerView = findViewById(R.id.moviePresentation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(success) {
            recyclerView.setAdapter(new MovieAdaptor(moviesList.get(0)));
        }
        else {
            Snackbar.make(this.recyclerView, "No Internet Connexion", Snackbar.LENGTH_LONG).show();
        }*/
    }
}
