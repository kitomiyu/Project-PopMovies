package com.example.android.popmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toda on 2017/12/18.
 */

public class TrailersActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener{

    private static final String TAG = TrailersActivity.class.getSimpleName();

    private TrailersAdapter mAdapter;
    private RecyclerView mTrailersList;
    private static final int NUM_LIST_ITEMS = 20;
    String mId;
    private static ArrayList<HashMap<String, String>> trailersInfo;

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trailers_list);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            mId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            Log.v(TAG, "Get string Extra in TrailersActivity: " + mId);
        }

        mTrailersList = (RecyclerView) findViewById(R.id.recyclerview_trailers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrailersList.setLayoutManager(layoutManager);

        mTrailersList.setHasFixedSize(true);

        mAdapter = new TrailersAdapter(NUM_LIST_ITEMS, this);
        mTrailersList.setAdapter(mAdapter);

        loadMovieData();
    }

    @Override
    public void onListItemClick(String  TrailersUrl) {
        Log.v(TAG, TrailersUrl);
    }

    private void loadMovieData() {
        new MainActivityFragment.FetchLoadingTaskDetail().execute(getString(R.string.action_get_trailers),mId);
    }

    private void showMovieDataView() {
        mTrailersList.setVisibility(View.VISIBLE);
    }


}
