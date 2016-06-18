package com.orpheusdroid.moviebox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orpheusdroid.moviebox.adapter.MovieDataHolder;
import com.orpheusdroid.moviebox.adapter.MovieDetailsHandler;
import com.orpheusdroid.moviebox.contentprovider.favourites.FavouritesColumns;
import com.orpheusdroid.moviebox.contentprovider.favourites.FavouritesContentValues;
import com.orpheusdroid.moviebox.contentprovider.favourites.FavouritesCursor;
import com.orpheusdroid.moviebox.contentprovider.favourites.FavouritesSelection;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.sw
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "movie";
    public static final String ARG_TWO_PANE = "two_pane";

    private MovieDataHolder movie;

    private CollapsingToolbarLayout collapsingToolbar;

    private ImageView iv;
    private RecyclerView mRecyclerView;
    private FavouritesSelection selection;
    private boolean mTwoPane;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            movie = getArguments().getParcelable(ARG_ITEM_ID);
            mTwoPane = getArguments().getBoolean(ARG_TWO_PANE, false);

            Activity activity = this.getActivity();

            selection = new FavouritesSelection();
            selection.movieId(movie.getId());

            collapsingToolbar = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (collapsingToolbar != null) {
                collapsingToolbar.setTitle(movie.getTitle());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (mTwoPane) {
            rootView = inflater.inflate(R.layout.movie_details_recycler, container, false);
            iv = ((MovieDetailActivity) getActivity()).getIv();
        } else {
            rootView = inflater.inflate(R.layout.movie_detail, container, false);
            iv = (ImageView) rootView.findViewById(R.id.movie_banner);

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fabOnClick();
                }
            });

            setHasOptionsMenu(true);

            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(movie.getTitle());

            showIntro(fab);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_details_recycler);
        //Let's do some fetching of data again and set the data*/
        new MovieDetailsHandler(getActivity(), mRecyclerView, collapsingToolbar, iv, movie, mTwoPane).
                execute(movie.getBackdrop(),
                        Constants.API_BASE_URL + movie.getId() + "/reviews?api_key=" + Constants.API_KEY);

        return rootView;
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
        getActivity().getContentResolver().insert(FavouritesColumns.CONTENT_URI, favMovie.values());
        item.setIcon(R.drawable.ic_favorite_accent);
    }

    private void deleteFavourite(MenuItem item) {
        selection.delete(getActivity().getContentResolver());
        item.setIcon(R.drawable.ic_favorite_border_accent);
    }

    private FavouritesCursor getFavourites() {
        return selection.query(getActivity().getContentResolver());
    }

    private void showIntro(FloatingActionButton fab) {
        new MaterialIntroView.Builder(getActivity())
                .enableDotAnimation(true)
                .enableIcon(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(getResources().getString(R.string.tutorial_trailer))
                .setTarget(fab)
                .setUsageId(MovieDetailActivity.INTRO_TRAILER_ID) //THIS SHOULD BE UNIQUE ID
                .show();
    }

    public void fabOnClick (){
        if (!movie.getTrailer().equals(""))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.getTrailer())));
        else
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Oops! No trailer found", Snackbar.LENGTH_SHORT)
                    .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_favourite_item, menu);
        MenuItem favourite = menu.findItem(R.id.favourite);
        if (getFavourites().getCount() > 0)
            favourite.setIcon(R.drawable.ic_favorite_accent);
        //return super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.favourite:
                if (getFavourites().getCount() > 0) {
                    deleteFavourite(item);
                } else {
                    addFavourite(item);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
