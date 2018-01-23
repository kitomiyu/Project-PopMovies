package com.example.android.popmovies.data;

import android.provider.BaseColumns;

public class FavoriteMovieContract {

    public static final class MovieData implements BaseColumns {
        public static final String TABLE_NAME = "favoriteMovieList";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_NAME = "name";
        public static final String COLUMN_MOVIE_URL = "imageUrl";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_RATING = "vote_average";
        public static final String COLUMN_MOVIE_RELEASEDATE = "release_date";
    }
}
