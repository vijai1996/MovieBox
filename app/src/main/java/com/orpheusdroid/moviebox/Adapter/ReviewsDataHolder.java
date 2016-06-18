package com.orpheusdroid.moviebox.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vijai on 12-06-2016.
 */
public class ReviewsDataHolder implements Parcelable {
    public static final Parcelable.Creator<ReviewsDataHolder> CREATOR = new Parcelable.Creator<ReviewsDataHolder>() {

        @Override
        public ReviewsDataHolder createFromParcel(Parcel source) {
            return new ReviewsDataHolder(source);  //using parcelable constructor
        }

        @Override
        public ReviewsDataHolder[] newArray(int size) {
            return new ReviewsDataHolder[size];
        }
    };
    private String movie_id;
    private String author;
    private String review;
    private String review_id;

    public ReviewsDataHolder(String movie_id, String author, String review, String review_id) {
        this.movie_id = movie_id;
        this.author = author;
        this.review = review;
        this.review_id = review_id;
    }

    public ReviewsDataHolder(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.movie_id = data[0];
        this.author = data[1];
        this.review = data[2];
        this.review_id = data[3];
    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public String getReview_id() {
        return review_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.movie_id, this.author
                , this.review, this.review_id});
    }
}
