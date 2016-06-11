package com.orpheusdroid.moviebox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.orpheusdroid.moviebox.Adapter.MovieDataHolder;
import com.orpheusdroid.moviebox.Adapter.MovieDetailsHandler;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private MovieDataHolder movie;
    CollapsingToolbarLayout collapsingToolbar;
    private ImageView iv;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        movie = getIntent().getParcelableExtra(MovieDetailFragment.ARG_ITEM_ID);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(movie.getTitle());
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_details_recycler);
        iv = (ImageView) findViewById(R.id.movie_banner);

        new MovieDetailsHandler(this, mRecyclerView, collapsingToolbar, iv,movie, true).
                execute(movie.getBackdrop());
    }

    public void fabOnClick (View v){
        if (!movie.getTrailer().equals(""))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.getTrailer())));
        else
            Snackbar.make(MovieDetailActivity.this.findViewById(android.R.id.content), "Oops! No trailer found", Snackbar.LENGTH_SHORT)
                    .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
