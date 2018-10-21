package Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.VideoView;

import Elements.Movie;
import Tasks.GetMoviesParseTask;
import Adaptors.MovieAdaptor;
import Enums.MovieOption;
import Interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.R;

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
        mMovieParser = new GetMoviesParseTask(this,MovieOption.SEARCH, moviesList, id );
        mMovieParser.execute();


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
