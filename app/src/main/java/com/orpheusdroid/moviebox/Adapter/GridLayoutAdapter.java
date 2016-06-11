package com.orpheusdroid.moviebox.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.orpheusdroid.moviebox.MovieDetailActivity;
import com.orpheusdroid.moviebox.MovieDetailFragment;
import com.orpheusdroid.moviebox.MovieListActivity;
import com.orpheusdroid.moviebox.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vijai on 07-06-2016.
 */
public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.Holder> {
    private MovieListActivity mContext;
    private ArrayList<MovieDataHolder> movies;
    private int imageWidth, imageHeight;
    private boolean mTwoPane;

    public GridLayoutAdapter(MovieListActivity activity, ArrayList<MovieDataHolder> movies,
                             boolean twoPane) {
        mContext = activity;
        this.movies = movies;
        mTwoPane = twoPane;

        //Set the image sizes for table and phone views
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (mTwoPane) {
            imageWidth = size.y / 3;
            imageHeight = size.x / 4;
        } else {
            imageWidth = size.x / 3;
            imageHeight = size.y / 4;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_list_content, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Holder myHolder = holder;

        //Load the image into grids using picasso library
        Picasso.with(mContext)
                .load(movies.get(position).getPosterPath())
                .error(R.mipmap.ic_launcher)
                .resize(imageWidth, imageHeight)
                .into((myHolder.images), new ImageLoadedCallback(myHolder.loader){
                    @Override
                    public void onSuccess(){
                        //Set the progressbar placeholder visibility to gone once the image completes loading
                        if (this.progressBar != null) {
                            this.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        /*
        On image click, bring up the fragment to show the detail or start a new activity for phone view
         */
        myHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDataHolder movie = movies.get(position);
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID, movie);
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    mContext.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, movie);
                    context.startActivity(intent);
                }
            }
        });

    }

    //Swap method to change the data and reload the grids
    public void swap(ArrayList<MovieDataHolder> newMovies){
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }

    /*
    View holder class for holding the views used in the gridlayout
     */
    public class Holder extends RecyclerView.ViewHolder {
        private ImageView images;
        private ProgressBar loader;
        private View mView;

        public Holder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.ivItemGridImage);
            loader = (ProgressBar) itemView.findViewById(R.id.progressBar);
            mView = itemView;
        }
    }
}
