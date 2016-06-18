package com.orpheusdroid.moviebox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orpheusdroid.moviebox.R;

import java.util.ArrayList;

/**
 * Created by vijai on 12-06-2016.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.DataObjectHolder> {
    private ArrayList<ReviewsDataHolder> reviews;

    public ReviewsAdapter(ArrayList<ReviewsDataHolder> reviews) {
        this.reviews = reviews;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_reviews_card, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        int i = 0;
        while (i < reviews.size()) {
            holder.author.setText(reviews.get(position).getAuthor());
            holder.review.setText(reviews.get(position).getReview());
            i++;
        }
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView review;

        public DataObjectHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            review = (TextView) itemView.findViewById(R.id.review);
        }
    }
}
