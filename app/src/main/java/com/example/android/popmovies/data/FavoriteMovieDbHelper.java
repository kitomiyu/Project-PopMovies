package com.example.android.popmovies.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {
    private static final String TAG = FavoriteMovieDbHelper.class.getSimpleName();

    // The database name
    private static final String DATABASE_NAME = "favoriteMovie.db";

    // If we change the database schema, we must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create db at the first time
    //The onCreate() and onUpgrade() methods are be called by the Android OS. onCreate() method is called in case Android SQLite Database doesn’t exist and we want to get a connection to the database.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold favorite movie data
        final String SQL_CREATE_FAVORITEMOVIE_TABLE = "CREATE TABLE " + FavoriteMovieContract.MovieData.TABLE_NAME + " (" +
                FavoriteMovieContract.MovieData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_RELEASEDATE + " TEXT NOT NULL, " +
                FavoriteMovieContract.MovieData.COLUMN_MOVIE_URL + " TEXT NOT NULL" +
                "); ";

        Log.v(TAG, "DBhelper is called and onCreate: " +SQL_CREATE_FAVORITEMOVIE_TABLE);
        // Execute the query by calling execSQL on sqLiteDatabase
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITEMOVIE_TABLE);
    }

    //if the database already exists, but the database version is changed the onUpgrade method is invoked.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.MovieData.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}