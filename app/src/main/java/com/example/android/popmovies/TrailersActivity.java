package com.example.android.popmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

/**
 * Created by toda on 2017/12/18.
 */

public class TrailersActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener{

    private static final String TAG = TrailersActivity.class.getSimpleName();

    private TrailersAdapter mAdapter;
    private RecyclerView mNumbersList;
    private static final int NUM_LIST_ITEMS = 20;
    String mId;

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trailers_list);


        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                mId = null;
            } else {
                mId = extras.getString("id");
                Log.v(TAG, "id is set to get trailers:" + mId);
            }
        }

        mNumbersList = (RecyclerView) findViewById(R.id.recyclerview_trailers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        mNumbersList.setHasFixedSize(true);

        mAdapter = new TrailersAdapter(NUM_LIST_ITEMS, this);
        mNumbersList.setAdapter(mAdapter);

        loadMovieData();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

    private void loadMovieData() {

    }


}
