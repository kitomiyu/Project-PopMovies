package com.example.android.popmovies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();


    private GridView gridView;
    private ProgressBar mLoadingIndicator;
    private MovieImagesAdapter imagesAdapter;
    private ArrayList<HashMap<String, String>> moviesInfo;

    /**
     * ArrayList to store
     * original title
     * movie poster image thumbnail
     * A plot synopsis (called overview in the api)
     * user rating (called vote_average in the api)
     * release date
     */

    /**
     * Set variables
     */
    private String API_KEY = "13a909a18259e3653d6885c366f6b369";
    private String sortBy_upcoming = "upcoming";
    private String sortBy_popular = "popular";
    private String sortBy_top_rated = "top_rated";

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Initialize with empty data
        moviesInfo = new ArrayList<>();
        imagesAdapter = new MovieImagesAdapter(getActivity(), moviesInfo);
        // Get a reference to the ListView, and attach this adapter to it.
        gridView = rootView.findViewById(R.id.image_grid);
        //Initialize with empty data
        gridView.setAdapter(imagesAdapter);

        //Calling the AsyncTask class to start to execute.
        new FetchLoadingTask().execute(sortBy_upcoming);

        return rootView;

    }


    // AsyncTask<Params, Progress, Result>
    public class FetchLoadingTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>{

//        Show progress bar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

//        Start loading images on background
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String sortOrder = params[0];
            URL requestUrl = NetworkUtils.buildUrl(API_KEY, sortOrder);


            try {
                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(requestUrl);

                Log.v(TAG, "jsonResponse: " + jsonResponse);

                // Get Base_URL
                JSONObject getMovieInfo = new JSONObject(jsonResponse);
                JSONArray resultDetail = getMovieInfo.getJSONArray("results");

                Log.v(TAG, "JSON Result: " + resultDetail.toString());

                // looping through All Contacts
                for (int i = 0; i < resultDetail.length(); i++) {

                    JSONObject r = resultDetail.getJSONObject(i);

                    String id = r.getString("id");
                    String title = r.getString("title");
                    String popularity = r.getString("popularity");
                    String poster_path = r.getString("poster_path");
                    String imageUrl = "http://image.tmdb.org/t/p/w185/" + poster_path;

                    HashMap<String, String> movieInfo = new HashMap<>();
                    // adding each child node to HashMap key => value
                    movieInfo.put("id", id);
                    movieInfo.put("title", title);
                    movieInfo.put("popularity", popularity);
                    movieInfo.put("imageUrl", imageUrl);

                    // adding contact to movieInfo list
                    moviesInfo.add(movieInfo);

                }
                Log.v(TAG, "MoviesInfo: "+ moviesInfo.toString());
                return moviesInfo;
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
            if (hashMaps != null && !hashMaps.equals("")) {
                Log.v(TAG, "onPost is executed" + hashMaps.toString());
                imagesAdapter.setGridData(hashMaps);

            }else {
                Toast.makeText(getContext(), R.string.error_msg_loading, Toast.LENGTH_SHORT);
            }
        }
    }
}