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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orpheusdroid.moviebox.Adapter.MovieDataHolder;
import com.orpheusdroid.moviebox.Adapter.MovieDetailsHandler;

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

    private MovieDataHolder movie;

    private CollapsingToolbarLayout collapsingToolbar;

    //private Bitmap banner;
    private ImageView iv;
    private RecyclerView mRecyclerView;
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
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            movie = getArguments().getParcelable(ARG_ITEM_ID);

            Activity activity = this.getActivity();

            collapsingToolbar = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (collapsingToolbar != null) {
                collapsingToolbar.setTitle(movie.getTitle());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Show the dummy content as text in a TextView.
        iv = (ImageView) rootView.findViewById(R.id.movie_banner);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_details_recycler);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick(v);
            }
        });

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(movie.getTitle());
        new MovieDetailsHandler(getActivity(), mRecyclerView, collapsingToolbar, iv,movie, false).
                execute(movie.getBackdrop());

        return rootView;
    }

    public void fabOnClick (View v){
        if (!movie.getTrailer().equals(""))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.getTrailer())));
        else
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Oops! No trailer found", Snackbar.LENGTH_SHORT)
                    .show();
    }
}
