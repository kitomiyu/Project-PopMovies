package com.example.android.popmovies;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popmovies.data.FavoriteMovieDbHelper;

import java.util.Timer;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up Timper
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        switch (id) {
            case R.id.action_sort_toprated:
                new MainActivityFragment.FetchLoadingTask().execute(getString(R.string.sort_order_topRated));
                return true;
            case R.id.action_sort_popular:
                new MainActivityFragment.FetchLoadingTask().execute(getString(R.string.sort_order_popular));
                return true;
            case R.id.action_sort_upcoming:
                new MainActivityFragment.FetchLoadingTask().execute(getString(R.string.sort_order_upcoming));
                return true;
            case R.id.action_sort_favorite:
                FavoriteMovieDbHelper mDbHelper = new FavoriteMovieDbHelper(this);
                // Keep a reference to the mDb until paused or killed. Get a writable database
                SQLiteDatabase database = mDbHelper.getWritableDatabase();
                new MainActivityFragment().displayFavoriteMovie(database);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
