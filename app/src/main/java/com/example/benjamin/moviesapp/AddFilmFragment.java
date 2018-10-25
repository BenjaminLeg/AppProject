package com.example.benjamin.moviesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.example.benjamin.moviesapp.elements.Movie;


import io.realm.Realm;



public class AddFilmFragment extends DialogFragment {
    private EditText title;
    private EditText lang;
    private EditText date;
    private EditText rate;
    private EditText plot;
    private int addNb;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.dialog_addmovie, null);
        builder.setView(rootView)
                .setPositiveButton("Add movie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title= rootView.findViewById(R.id.title);
                        lang= rootView.findViewById(R.id.lang);
                        date= rootView.findViewById(R.id.date);
                        rate= rootView.findViewById(R.id.rate);
                        plot= rootView.findViewById(R.id.plot);



                        String dateStr=date.getText().toString();
                        String voteStr=rate.getText().toString();
                        String titleStr=title.getText().toString();
                        if(dateStr.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}") && voteStr.matches("[0-9]{2}") && !(titleStr.isEmpty())) {
                            Realm realm= Realm.getDefaultInstance();
                            realm.beginTransaction();
                            Movie movie = realm.createObject(Movie.class);
                            addNb=(int)realm.where(Movie.class).count();
                            addNb++;

                            movie.setId("add" + addNb);
                            movie.setTitle(titleStr);
                            movie.setRelease_date(dateStr);
                            movie.setOriginal_language(lang.getText().toString());
                            movie.setOverview(plot.getText().toString());
                            movie.setVoteAvg(voteStr);
                            movie.setPosterSrc("R.drawable.bobinefilm");
                            realm.commitTransaction();

                            Toast.makeText(rootView.getContext(), "Movie added", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(rootView.getContext(), "Wrong movie's information", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return builder.create();
    }
}
