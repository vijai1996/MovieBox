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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.orpheusdroid.moviebox.Adapter.MovieDataHolder;
import com.orpheusdroid.moviebox.Adapter.MovieDetailsHandler;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesColumns;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesContentValues;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesCursor;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesSelection;

import co.mobiwise.materialintro.MaterialIntroConfiguration;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

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
    private FavouritesSelection selection;
    public static String INTRO_TRAILER_ID = "movie_detail_trailer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //Get the selected movie from the intent
        movie = getIntent().getParcelableExtra(MovieDetailFragment.ARG_ITEM_ID);

        selection = new FavouritesSelection();
        selection.movieId(movie.getId());


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(movie.getTitle());
        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_details_recycler);
        iv = (ImageView) findViewById(R.id.movie_banner);

        //Let's do some fetching of data again and set the data

        showIntro();

        new MovieDetailsHandler(this, mRecyclerView, collapsingToolbar, iv,movie, true).
                execute(movie.getBackdrop());
    }

    private void showIntro() {
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(getResources().getString(R.string.tutorial_trailer))
                .setTarget(this.findViewById(R.id.fab))
                .setUsageId(INTRO_TRAILER_ID) //THIS SHOULD BE UNIQUE ID
                .show();
    }

    //Set an intent to play the trailer is available or show a snackbar it isn't available.
    public void fabOnClick (View v){
        if (!movie.getTrailer().equals(""))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.getTrailer())));
        else
            Snackbar.make(MovieDetailActivity.this.findViewById(android.R.id.content), "Oops! No trailer found", Snackbar.LENGTH_SHORT)
                    .show();
    }

    private void addFavourite(MenuItem item) {
        FavouritesContentValues favMovie = new FavouritesContentValues();
        favMovie.putMovieId(movie.getId())
                .putBackdrop(movie.getBackdrop())
                .putPosterPath(movie.getPosterPath())
                .putOverview(movie.getOverView())
                .putReleaseDate(movie.getReleaseDate())
                .putTitle(movie.getTitle())
                .putTrailer(movie.getTrailer())
                .putUserRating(movie.getUserRating());
        this.getContentResolver().insert(FavouritesColumns.CONTENT_URI, favMovie.values());
        item.setIcon(R.drawable.ic_favorite_accent);
    }

    private void deleteFavourite(MenuItem item) {
        selection.delete(getContentResolver());
        item.setIcon(R.drawable.ic_favorite_border_accent);
    }

    private FavouritesCursor getFavourites(){
        return selection.query(getContentResolver());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite_item, menu);
        MenuItem favourite = menu.findItem(R.id.favourite);
        if (getFavourites().getCount() >0)
            favourite.setIcon(R.drawable.ic_favorite_accent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
                return true;
            case R.id.favourite:
                if (getFavourites().getCount() >0){
                    deleteFavourite(item);
                } else {
                    addFavourite(item);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
