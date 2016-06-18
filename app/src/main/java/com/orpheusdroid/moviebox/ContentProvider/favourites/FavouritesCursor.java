package com.orpheusdroid.moviebox.contentprovider.favourites;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.orpheusdroid.moviebox.contentprovider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favourites} table.
 */
public class FavouritesCursor extends AbstractCursor implements FavouritesModel {
    public FavouritesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavouritesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(FavouritesColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterPath() {
        String res = getStringOrNull(FavouritesColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code backdrop} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBackdrop() {
        String res = getStringOrNull(FavouritesColumns.BACKDROP);
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOverview() {
        String res = getStringOrNull(FavouritesColumns.OVERVIEW);
        return res;
    }

    /**
     * Get the {@code user_rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUserRating() {
        String res = getStringOrNull(FavouritesColumns.USER_RATING);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReleaseDate() {
        String res = getStringOrNull(FavouritesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code movie_id} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieId() {
        String res = getStringOrNull(FavouritesColumns.MOVIE_ID);
        return res;
    }

    /**
     * Get the {@code trailer} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTrailer() {
        String res = getStringOrNull(FavouritesColumns.TRAILER);
        return res;
    }
}
