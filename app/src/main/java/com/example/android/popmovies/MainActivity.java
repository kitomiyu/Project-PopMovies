package com.example.android.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String sortBy_popular = "popular";
    private String sortBy_top_rated = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_toprated) {
            Log.v(TAG, "sort_toprated item is tapped");
            new MainActivityFragment.FetchLoadingTask().execute(sortBy_top_rated);
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_popular) {
            Log.v(TAG, "popular item is tapped");
            new MainActivityFragment.FetchLoadingTask().execute(sortBy_popular);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
