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

import com.example.android.popmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toda on 2017/12/18.
 */

public class TrailersActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener {

    private static final String TAG = TrailersActivity.class.getSimpleName();

    private static TrailersAdapter mAdapter;
    private RecyclerView mTrailersList;
    String mId;
    private static ArrayList<HashMap<String, String>> trailersInfo = new ArrayList<>();

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

        mAdapter = new TrailersAdapter(this);
        mTrailersList.setAdapter(mAdapter);

        loadMovieData();

        //FIX: After removing the Parent activity definitions from Manifest,
        // add this line to show an up arrow on the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    //FIX: On clicking of back arrow, simply finish the activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    //Allow users to view and play trailers in a web browser.
    @Override
    public void onListItemClick(String TrailersUrl) {
        Log.v(TAG, TrailersUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(TrailersUrl));
        startActivity(intent);
    }

    private void loadMovieData() {
        new FetchLoadingTaskDetail().execute(getString(R.string.action_get_trailers), mId);
    }

    private void showMovieDataView() {
        mTrailersList.setVisibility(View.VISIBLE);
    }

    //FIX: Moving the Async Task here and referencing the correct adapter to load the trailers.
    // The issue was with the AsyncTask refering to a wrong adapter.
    private static class FetchLoadingTaskDetail extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        private String API_KEY = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //        Start loading images on background
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String sortOrder = params[0];
            String mId = params[1];

            // clear the data of ArrayList if it's not empty
            if (trailersInfo.size() > 0) {
                trailersInfo.clear();
            }

            URL requestUrl = NetworkUtils.buildUrl_detail(API_KEY, sortOrder, mId);

            try {
                //Store JsonResponse
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(requestUrl);

                Log.v(TAG, "jsonResponse: " + jsonResponse);

                //Find items in "Result"
                JSONObject getMovieInfo = new JSONObject(jsonResponse);
//                Log.v(TAG, getMovieInfo.getString("page"));
                JSONArray resultDetail = getMovieInfo.getJSONArray("results");

                Log.v(TAG, "JSON Result: " + resultDetail.toString());

                // looping through All Contacts
                for (int i = 0; i < resultDetail.length(); i++) {

                    JSONObject r = resultDetail.getJSONObject(i);

                    String id = r.getString("id");
                    String key = r.getString("key");
                    String name = r.getString("name");
                    String type = r.getString("type");
                    String trailersUrl = "https://www.youtube.com/watch?v=" + key;

                    HashMap<String, String> movieInfo = new HashMap<>();
                    // adding each child node to HashMap key => value

                    movieInfo.put("id", id);
                    movieInfo.put("name", name);
                    movieInfo.put("trailersUrl", trailersUrl);

                    // adding contact to movieInfo list
                    trailersInfo.add(movieInfo);
                }
                Log.v(TAG, "MoviesInfo: " + trailersInfo.toString());
                return trailersInfo;
            } catch (Exception e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        // Post the loading data
        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);
            if (hashMaps != null) {
                Log.v(TAG, "onPost is executed" + hashMaps.toString());
                mAdapter.setTrailersData(hashMaps);
            }
        }
    }
}
