package com.orpheusdroid.moviebox.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orpheusdroid.moviebox.R;

/**
 * Created by vijai on 08-06-2016.
 */
public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.DataObjectHolder> {
    private MovieDataHolder movie;
    private static Context context;

    //Viewholder class holding views of the detailactivity layout implementing onClick
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

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_details_cards, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        //Set the data for each potion
        Resources resources = context.getResources();
        switch (position){
            case 0:
                holder.Title.setText(resources.getString(R.string.user_rating));
                holder.Content.setText(movie.getUserRating());
                holder.rating.setVisibility(View.VISIBLE);
                holder.rating.setRating(Float.parseFloat(movie.getUserRating()));
                holder.rating.setIsIndicator(true);
                break;
            case 1:
                holder.Title.setText(resources.getString(R.string.plot_synopsis));
                holder.Content.setText(movie.getOverView());
                break;
            case 2:
                holder.Title.setText(resources.getString(R.string.release_date));
                holder.Content.setText(movie.getReleaseDate());
                break;
            case 3:
                holder.Title.setText(resources.getString(R.string.user_review));
                holder.Content.setText(resources.getString(R.string.placeholder_text));
                break;
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
