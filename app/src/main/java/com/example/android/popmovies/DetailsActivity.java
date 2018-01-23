package com.example.android.popmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.FavoriteMovieContract;
import com.example.android.popmovies.data.FavoriteMovieDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toda on 2017/10/11.
 */

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    // Set variables
    Context mContext;
    TextView mMovieTitle;
    TextView mMovieReleaseDate;
    ImageView mMovieImage;
    TextView mMovieOverview;
    RatingBar mMovieRating;
    TextView mRatingNumber;
    Button mTrailers;
    // Holds on to the cursor
    private Cursor mCursor;

    String mReleaseDate;
    String mImage;
    String mTitle;
    String mOverview;
    String mRating;
    String mId;
    HashMap<String, String> currentMovieData;

    // local field member of type SQLiteDatabase called mDb
    private SQLiteDatabase mDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        currentMovieData = (HashMap<String, String>) getIntent().getSerializableExtra(getString(R.string.currentMovieData));

        mMovieTitle = findViewById(R.id.mv_display_title);
        mMovieReleaseDate = findViewById(R.id.mv_releaseDate);
        mMovieImage = findViewById(R.id.mv_image);
        mMovieOverview = findViewById(R.id.mv_overview);
        mMovieRating = findViewById(R.id.ratingBar);
        mRatingNumber = findViewById(R.id.mv_rating);
        mTrailers = findViewById(R.id.action_trailers);

        mId = currentMovieData.get("id");

        mTitle = currentMovieData.get("original_title");
        mMovieTitle.setText(mTitle);

        mReleaseDate = currentMovieData.get("release_date");
        mMovieReleaseDate.setText(mReleaseDate);

        mImage = currentMovieData.get("imageUrl");
        Picasso.with(mContext).load(mImage).into(mMovieImage);

        mOverview = currentMovieData.get("overview");
        mMovieOverview.setText(mOverview);

        mRating = currentMovieData.get("vote_average");
        mMovieRating.setNumStars(10);
        mMovieRating.setStepSize((float) 0.2);
        mMovieRating.setRating(Float.valueOf(mRating));
        mRatingNumber.setText(mRating);

        // Create a DB helper (this will create the DB if run for the first time)
        FavoriteMovieDbHelper dbHelper = new FavoriteMovieDbHelper(getApplicationContext());

        // Keep a reference to the mDb until paused or killed. Get a writable database
        mDb = dbHelper.getWritableDatabase();

        // When user click button to show trailers, open new activity
        mTrailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pass the data to new activity "TrailersActivity"
                Intent intent = new Intent(DetailsActivity.this, TrailersActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, currentMovieData.get("id"));
                //Start details activity
                startActivity(intent);
            }
        });

        // When user tap RatingBar, open new activity
        mMovieRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, event.toString());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Open new activity to show movie review
                    Intent intent = new Intent(DetailsActivity.this, ReviewsActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, currentMovieData.get("id"));
                    //Start details activity
                    startActivity(intent);
                }
                return false;
            }
        });

        // When user tap floatingActionButton, save it in local DB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor = getAllMovieData(mDb);

                //if the mv is not in favorite yet, allow users to mark a movie as a favorite in the details view by tapping a button(star).
                if (checkExistedData(mCursor, mTitle) == false) {
                    addNewFavoriteMovie();

                    Snackbar.make(view, "Save this move as your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                mCursor.close();
            }
        });
    }

    /**
     * Query the mDb and get all MovieUrls from the table
     *
     * @return Cursor containing the list
     */
    private Cursor getAllMovieData(SQLiteDatabase db) {
        return db.query(
                FavoriteMovieContract.MovieData.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Adds a new favorite movie to the mDb including the movie id/ name/ url
     */
    private long addNewFavoriteMovie() {
        ContentValues cv = new ContentValues();

        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_ID, mId);
        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_NAME, mTitle);
        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_URL, mImage);
        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_OVERVIEW, mOverview);
        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_RATING, mRating);
        cv.put(FavoriteMovieContract.MovieData.COLUMN_MOVIE_RELEASEDATE, mReleaseDate);

        return mDb.insert(FavoriteMovieContract.MovieData.TABLE_NAME, null, cv);
    }

    // Check existing db to know duplication
    private boolean checkExistedData(Cursor cursor, String name) {
        boolean isEof = cursor.moveToFirst();

        while (isEof) {
            // Update the view holder with the information needed to display
            String mvName = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.MovieData.COLUMN_MOVIE_NAME));
            if (mvName.equals(name)) {
                Toast.makeText(this, "This movie already exists in your favorite", Toast.LENGTH_SHORT).show();
                return true;
            }
            isEof = cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

}