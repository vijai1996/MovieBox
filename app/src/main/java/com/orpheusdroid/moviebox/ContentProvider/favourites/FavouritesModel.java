package com.orpheusdroid.moviebox.ContentProvider.favourites;

import com.orpheusdroid.moviebox.ContentProvider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code favourites} table.
 */
public interface FavouritesModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterPath();

    /**
     * Get the {@code backdrop} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBackdrop();

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOverview();

    /**
     * Get the {@code user_rating} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUserRating();

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleaseDate();

    /**
     * Get the {@code movie_id} value.
     * Can be {@code null}.
     */
    @Nullable
    String getMovieId();

    /**
     * Get the {@code trailer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTrailer();
}
