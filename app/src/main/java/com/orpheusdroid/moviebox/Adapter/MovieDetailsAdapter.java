package com.orpheusdroid.moviebox.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orpheusdroid.moviebox.R;

import java.util.ArrayList;

/**
 * Created by vijai on 08-06-2016.
 */
public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.DataObjectHolder> {
    private MovieDataHolder movie;
    private static Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView Title;
        TextView Content;
        RatingBar rating;

        public DataObjectHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.contentIdentifier);
            Content = (TextView) itemView.findViewById(R.id.content);
            rating = (RatingBar) itemView.findViewById(R.id.movieRating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getLayoutPosition() == 3){
                Toast.makeText(context, "Lets start a review activity later!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public MovieDetailsAdapter(Context context, MovieDataHolder myDataset) {
        this.context = context;
        movie = myDataset;
    }

    public void swap(MovieDataHolder newMovie){
        movie = newMovie;
        notifyDataSetChanged();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_details_cards, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        switch (position){
            case 0:
                holder.Title.setText("User Rating");
                holder.Content.setText(movie.getUserRating());
                holder.rating.setVisibility(View.VISIBLE);
                holder.rating.setRating(Float.parseFloat(movie.getUserRating()));
                holder.rating.setIsIndicator(true);
                break;
            case 1:
                holder.Title.setText("Plot Synopsis");
                holder.Content.setText(movie.getOverView());
                break;
            case 2:
                holder.Title.setText("Release Date");
                holder.Content.setText(movie.getReleaseDate());
                break;
            case 3:
                holder.Title.setText("User Review");
                holder.Content.setText("The Loren Ipsum text");
                break;
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
