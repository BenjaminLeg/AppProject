package Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.SEND_SMS;
import android.widget.ProgressBar;

import Elements.Movie;
import Fragments.BlankFragment;
import Tasks.GetMoviesParseTask;
import Enums.MovieOption;
import Adaptors.MoviesAdaptor;
import Interfaces.OnLoadingListener;
import com.example.benjamin.moviesapp.R;

public class MainPage extends AppCompatActivity implements OnLoadingListener {
    public static Context context;
    private GetMoviesParseTask mMovieParser = null;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static PackageManager packageMan;

    List<Movie> moviesList;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    private ProgressBar spinner;


    @RequiresApi(api = Build.VERSION_CODES.M)
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

        moviesList = new ArrayList<Movie>();
        mMovieParser = new GetMoviesParseTask(this,MovieOption.TRENDING, moviesList, "");
        mMovieParser.execute();

        NavigationView nav = findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.isChecked();
                Intent intent;

                switch (menuItem.getItemId()) {
                    case R.id.nav_local:
                        Toast.makeText(MainPage.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                        //intent = new Intent(MainPage.context,LocalMovies.class);
                        //startActivity(intent);
                        // TO DO : Make new view to the Local Movies Page
                        return true;

                    case R.id.nav_favorites:
                        Toast.makeText(MainPage.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                        //intent = new Intent(MainPage.context,FavoritePage.class);
                        //startActivity(intent);
                        // TO DO : Make new view to the Favorites Page
                        return true;

                    case R.id.nav_explore:
                        intent = new Intent(MainPage.context,MainPage.class);
                        startActivity(intent);
                        // TO DO : Make new view to the Explore Page
                        return true;

                    case R.id.nav_discover:
                        setTitle("Blank Fragment");
                        BlankFragment fragment = new BlankFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.linear, fragment).commit();
                        drawerLayout.closeDrawers();
                        // TO DO : Make new view to the Explore Page
                        return true;

                    case R.id.nav_about:
                        Toast.makeText(MainPage.context,"Remove comments once class is implemented", Toast.LENGTH_SHORT).show();
                        //intent = new Intent(MainPage.context,AboutPage.class);
                        //startActivity(intent);
                        // TO DO : Make new view to the About Page
                        return true;

                    default:
                        drawerLayout.closeDrawers();
                        return true;
                }

            }
        });


        request_Permissions();

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
      /*  Button share = findViewById(R.id.shareButton);
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = "favoris";
             //   Movie content = new Movie();
                FileOutputStream stream;
                try {
                    stream = v.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    // stream.write(content.getBytes());
                    stream.close();
                    Log.e("DebugPerso", "Film ajouté");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public void loadChange(Boolean success) {
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(success) {
            recyclerView.setAdapter(new MoviesAdaptor(moviesList));
        }
        else {
            Snackbar.make(this.recyclerView, "No Internet Connexion", Snackbar.LENGTH_LONG).show();
        }
        
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void request_Permissions(){
        if(checkSelfPermission(SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, PERMISSION_REQUEST_CODE);
        }
    }
}
