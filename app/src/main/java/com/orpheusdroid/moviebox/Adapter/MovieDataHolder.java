package com.orpheusdroid.moviebox.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vijai on 07-06-2016.
 */
public class MovieDataHolder implements Parcelable{
    private String Title;
    private String PosterPath;
    private String Backdrop;
    private String OverView;
    private String UserRating;
    private String ReleaseDate;
    private String id;
    private String trailer;

    public MovieDataHolder(String Title, String PosterPath, String Overview, String UserRating,
                           String ReleaseDate, String Backdrop, String id, String trailer){
        this.Title = Title;
        this.PosterPath = PosterPath;
        this.OverView = Overview;
        this.UserRating = UserRating;
        this.ReleaseDate = ReleaseDate;
        this.Backdrop = Backdrop;
        this.trailer = trailer;
    }

    public String getBackdrop() {
        return Backdrop;
    }

    public String getTitle() {
        return Title;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public String getOverView() {
        return OverView;
    }

    public String getUserRating() {
        return UserRating;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public String getId() {
        return id;
    }

    public String getTrailer() {
        return trailer;
    }

    //parcel part
    public MovieDataHolder(Parcel in){
        String[] data= new String[8];

        in.readStringArray(data);
        this.Title= data[0];
        this.PosterPath= data[1];
        this.Backdrop= data[2];
        this.OverView = data[3];
        this.UserRating = data[4];
        this.ReleaseDate = data[5];
        this.id = data[6];
        this.trailer = data[7];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.Title,this.PosterPath
                ,this.Backdrop, this.OverView, this.UserRating, this.ReleaseDate, this.id, this.trailer });
    }

    public static final Creator<MovieDataHolder> CREATOR= new Creator<MovieDataHolder>() {

        @Override
        public MovieDataHolder createFromParcel(Parcel source) {
            return new MovieDataHolder(source);  //using parcelable constructor
        }

        @Override
        public MovieDataHolder[] newArray(int size) {
            return new MovieDataHolder[size];
        }
    };
}
