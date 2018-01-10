package com.example.android.popmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toda on 2018/01/10.
 */

public class ReviewsActivity extends AppCompatActivity implements ReviewsAdapter.ListItemClickListener{

    private static final String TAG = ReviewsActivity.class.getSimpleName();

    private static ReviewsAdapter mAdapter;
    private RecyclerView mReviewsList;
    String mId;
    private static ArrayList<HashMap<String, String>> reviewsInfo = new ArrayList<>();
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_list);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            mId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            Log.v(TAG, "Get string Extra in TrailersActivity: " + mId);
        }

        mReviewsList = (RecyclerView) findViewById(R.id.recyclerview_reviews);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mReviewsList.setLayoutManager(layoutManager);

        mReviewsList.setHasFixedSize(true);

        mAdapter = new ReviewsAdapter(this);
        mReviewsList.setAdapter(mAdapter);

        loadMovieData();

        // add this line to show an up arrow on the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onListItemClick(String mv_url) {
        Toast.makeText(this, "list item is clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    private void loadMovieData() {
        new TrailersActivity.FetchLoadingTaskDetail().execute(getString(R.string.action_get_reviews), mId);
    }


}
