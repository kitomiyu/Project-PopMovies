package com.example.android.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by toda on 2017/10/11.
 */

public class DetailsActivity extends AppCompatActivity{

    private static final String TAG = DetailsActivity.class.getSimpleName();

    TextView mMovieTitle;
    String mTitle;
    private HashMap<String, String> currentMovieData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = (TextView) findViewById(R.id.mv_display_title);

        currentMovieData = (HashMap<String, String>) getIntent().getSerializableExtra("currentMovieData");
        Log.v(TAG, currentMovieData.get("original_title"));


        if (currentMovieData != null) {
            mTitle = currentMovieData.get("original_title");
            mMovieTitle.setText(mTitle);
            Log.v(TAG, mTitle);
        }

    }
}
