package com.example.android.popmovies;

import android.widget.ImageView;

import java.net.URI;
import java.net.URL;

/**
 * Created by toda on 2017/10/05.
 */

public class MovieImages {

    String movieTitle;
    String userRating;
    String releaseDate;
    String imageUrl;

    public MovieImages(String movieTitle, String userRating, String releaseDate, String imageUrl) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.imageUrl = imageUrl;
    }



}
