package com.orpheusdroid.moviebox.contentprovider.favourites;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.orpheusdroid.moviebox.contentprovider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favourites} table.
 */
public class FavouritesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavouritesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavouritesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavouritesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FavouritesContentValues putTitle(@Nullable String value) {
        mContentValues.put(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesContentValues putTitleNull() {
        mContentValues.putNull(FavouritesColumns.TITLE);
        return this;
    }

    public FavouritesContentValues putPosterPath(@Nullable String value) {
        mContentValues.put(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesContentValues putPosterPathNull() {
        mContentValues.putNull(FavouritesColumns.POSTER_PATH);
        return this;
    }

    public FavouritesContentValues putBackdrop(@Nullable String value) {
        mContentValues.put(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesContentValues putBackdropNull() {
        mContentValues.putNull(FavouritesColumns.BACKDROP);
        return this;
    }

    public FavouritesContentValues putOverview(@Nullable String value) {
        mContentValues.put(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesContentValues putOverviewNull() {
        mContentValues.putNull(FavouritesColumns.OVERVIEW);
        return this;
    }

    public FavouritesContentValues putUserRating(@Nullable String value) {
        mContentValues.put(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesContentValues putUserRatingNull() {
        mContentValues.putNull(FavouritesColumns.USER_RATING);
        return this;
    }

    public FavouritesContentValues putReleaseDate(@Nullable String value) {
        mContentValues.put(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesContentValues putReleaseDateNull() {
        mContentValues.putNull(FavouritesColumns.RELEASE_DATE);
        return this;
    }

    public FavouritesContentValues putMovieId(@Nullable String value) {
        mContentValues.put(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesContentValues putMovieIdNull() {
        mContentValues.putNull(FavouritesColumns.MOVIE_ID);
        return this;
    }

    public FavouritesContentValues putTrailer(@Nullable String value) {
        mContentValues.put(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesContentValues putTrailerNull() {
        mContentValues.putNull(FavouritesColumns.TRAILER);
        return this;
    }
}
