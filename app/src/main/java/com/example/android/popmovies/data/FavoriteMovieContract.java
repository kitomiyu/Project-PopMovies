package com.example.android.popmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteMovieContract {

        /* Add content provider constants to the Contract
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the directory
        4) Content URI for data
      */

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.popmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "favoriteMovieList";

    public static final class MovieData implements BaseColumns {

        // Content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Task table and column names
        public static final String TABLE_NAME = "favoriteMovieList";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the six below
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_NAME = "name";
        public static final String COLUMN_MOVIE_URL = "imageUrl";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_RATING = "vote_average";
        public static final String COLUMN_MOVIE_RELEASEDATE = "release_date";
    }
}
