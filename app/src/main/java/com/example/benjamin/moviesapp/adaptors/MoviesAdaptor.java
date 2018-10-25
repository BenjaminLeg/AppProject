package com.example.benjamin.moviesapp.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benjamin.moviesapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.benjamin.moviesapp.activities.MoviePresentation;
import com.example.benjamin.moviesapp.elements.FavoriRealmObj;
import com.example.benjamin.moviesapp.elements.Movie;
import com.example.benjamin.moviesapp.interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.tasks.MoviePosterLoadTask;


public class MoviesAdaptor extends RecyclerView.Adapter<MoviesAdaptor.ViewHolder> {

    private static final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Movie> listMovies;
    private List<FavoriRealmObj> listfavoris;
    private int lastVisibleItem, totalItemCount;
    private OnLoadingListener onLoadingListener;
    private int visibleThreshold = 1;
    private boolean loading;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView moviePlot;
        private TextView movieTitle;
        private ImageView moviePoster;
        private Button movieShare;
        private Button movieExplore;
        private static Context context;

        public ViewHolder (View v){
            super(v);
            context = v.getContext();
            this.moviePlot=v.findViewById(R.id.moviePlot);
            this.movieTitle=v.findViewById(R.id.movieTitle);
            this.moviePoster=v.findViewById(R.id.moviePoster);
            this.movieShare=v.findViewById(R.id.shareButton);
            this.movieExplore=v.findViewById(R.id.exploreButton);


        }
    }

    public MoviesAdaptor() {
    }

    public MoviesAdaptor(List<Movie> moviesResults, RecyclerView recyclerView)  {
        listMovies = moviesResults;
        listfavoris =new ArrayList<>();
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadingListener != null) {
                            onLoadingListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    public void MoviesAdaptor2(List<FavoriRealmObj> favorites,RecyclerView recyclerView){
        listfavoris = favorites;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadingListener != null) {
                            onLoadingListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }



    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
    }


    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
        if(listfavoris.isEmpty()) {
            return listMovies.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        }else {
            return listfavoris.get(position)!= null ? VIEW_ITEM :VIEW_PROG;
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.movie_cell, viewGroup, false);
      //  favoris = new File(viewGroup.getContext().getFilesDir(), "favoris");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        if(listfavoris.isEmpty()) {
            if (listMovies.get(i).getId().startsWith("add")) {
                holder.moviePoster.setImageResource(R.drawable.bobinefilm);
            } else {
                String posterURL = "https://image.tmdb.org/t/p/w500" + listMovies.get(i).getPoster_path();
                MoviePosterLoadTask poster = new MoviePosterLoadTask(posterURL, holder.moviePoster);
                poster.execute();
            }
            holder.movieTitle.setText(listMovies.get(i).getTitle());
            holder.moviePlot.setText(listMovies.get(i).getOverview());
            holder.movieShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String title = v.getResources().getString(R.string.chooser_title);
                    String url = "https://www.themoviedb.org/movie/";
                    intent.putExtra(Intent.EXTRA_TEXT, "Please check this movie : " + url + listMovies.get(i).getId());
                    intent.setType("text/plain");
                    Intent chooser = Intent.createChooser(intent, title);
                    v.getContext().startActivity(chooser);

                }
            });

            holder.movieExplore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = listMovies.get(i).getId();
                    Intent intent = new Intent(v.getContext(), MoviePresentation.class);
                    intent.putExtra("EXTRA_ID", id);
                    v.getContext().startActivity(intent);
                }
            });


        }else {
            if(listfavoris.get(i).getMovieId().startsWith("add")){
                holder.moviePoster.setImageResource(R.drawable.bobinefilm);
            }else{
                String posterURL = "https://image.tmdb.org/t/p/w500" + listfavoris.get(i).getPosterSrc();
                MoviePosterLoadTask poster = new MoviePosterLoadTask(posterURL, holder.moviePoster);
                poster.execute();
            }
            holder.movieTitle.setText(listfavoris.get(i).getTitle());
            holder.moviePlot.setText(listfavoris.get(i).getOverview());
            holder.movieShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String title = v.getResources().getString(R.string.chooser_title);
                    String url = "https://www.themoviedb.org/movie/";
                    intent.putExtra(Intent.EXTRA_TEXT, "Please check this movie : " + url + listfavoris.get(i).getMovieId());
                    intent.setType("text/plain");
                    Intent chooser = Intent.createChooser(intent, title);
                    v.getContext().startActivity(chooser);

                }
            });

            holder.movieExplore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = listfavoris.get(i).getMovieId();
                    Intent intent = new Intent(v.getContext(), MoviePresentation.class);
                    intent.putExtra("EXTRA_ID", id);
                    v.getContext().startActivity(intent);
                }
            });
        }
    };


    

    @Override
    public int getItemCount() {
        if(listfavoris.isEmpty()){
            return listMovies.size();
        }else{
            return listfavoris.size();
        }

    }





}
