package com.example.android.popmovies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private ProgressBar mLoadingIndicator;

    private MovieImagesAdapter imagesAdapter;

    MovieImages[] movieImages = {
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieImages("Title", "5.0", "ReleaseDate", "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
    };

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        imagesAdapter = new MovieImagesAdapter(getActivity(), Arrays.asList(movieImages));

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = rootView.findViewById(R.id.image_grid);
        gridView.setAdapter(imagesAdapter);

        return rootView;

    }
}