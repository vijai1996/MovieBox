package com.orpheusdroid.moviebox;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.orpheusdroid.moviebox.Adapter.ReviewsAdapter;
import com.orpheusdroid.moviebox.Adapter.ReviewsDataHolder;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    public static String REVIEW_ARGS = "reviews";
    public static String MOVIE_TITLE_ARG = "title";
    private ArrayList<ReviewsDataHolder> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviews = getIntent().getParcelableArrayListExtra(REVIEW_ARGS);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.review_recycler);
        /*for (int i = 0; i<reviews.size(); i++){
            Log.d("Review Activity", reviews.get(i).getAuthor());
        }*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getIntent().getStringExtra(MOVIE_TITLE_ARG));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ReviewsAdapter mAdapter = new ReviewsAdapter(reviews);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }
}
