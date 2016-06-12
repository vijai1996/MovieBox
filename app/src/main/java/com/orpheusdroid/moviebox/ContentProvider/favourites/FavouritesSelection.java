package com.orpheusdroid.moviebox.ContentProvider.favourites;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.orpheusdroid.moviebox.ContentProvider.base.AbstractSelection;

/**
 * Selection for the {@code favourites} table.
 */
public class FavouritesSelection extends AbstractSelection<FavouritesSelection> {
    @Override
    protected Uri baseUri() {
        return FavouritesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavouritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavouritesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavouritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavouritesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavouritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavouritesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavouritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavouritesCursor query(Context context) {
        return query(context, null);
    }


    public FavouritesSelection id(long... value) {
        addEquals("favourites." + FavouritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavouritesSelection idNot(long... value) {
        addNotEquals("favourites." + FavouritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavouritesSelection orderById(boolean desc) {
        orderBy("favourites." + FavouritesColumns._ID, desc);
        return this;
    }

    public FavouritesSelection orderById() {
        return orderById(false);
    }

    public FavouritesSelection title(String... value) {
        addEquals(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection titleNot(String... value) {
        addNotEquals(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection titleLike(String... value) {
        addLike(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection titleContains(String... value) {
        addContains(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection titleStartsWith(String... value) {
        addStartsWith(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection titleEndsWith(String... value) {
        addEndsWith(FavouritesColumns.TITLE, value);
        return this;
    }

    public FavouritesSelection orderByTitle(boolean desc) {
        orderBy(FavouritesColumns.TITLE, desc);
        return this;
    }

    public FavouritesSelection orderByTitle() {
        orderBy(FavouritesColumns.TITLE, false);
        return this;
    }

    public FavouritesSelection posterPath(String... value) {
        addEquals(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection posterPathNot(String... value) {
        addNotEquals(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection posterPathLike(String... value) {
        addLike(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection posterPathContains(String... value) {
        addContains(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection posterPathStartsWith(String... value) {
        addStartsWith(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection posterPathEndsWith(String... value) {
        addEndsWith(FavouritesColumns.POSTER_PATH, value);
        return this;
    }

    public FavouritesSelection orderByPosterPath(boolean desc) {
        orderBy(FavouritesColumns.POSTER_PATH, desc);
        return this;
    }

    public FavouritesSelection orderByPosterPath() {
        orderBy(FavouritesColumns.POSTER_PATH, false);
        return this;
    }

    public FavouritesSelection backdrop(String... value) {
        addEquals(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection backdropNot(String... value) {
        addNotEquals(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection backdropLike(String... value) {
        addLike(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection backdropContains(String... value) {
        addContains(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection backdropStartsWith(String... value) {
        addStartsWith(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection backdropEndsWith(String... value) {
        addEndsWith(FavouritesColumns.BACKDROP, value);
        return this;
    }

    public FavouritesSelection orderByBackdrop(boolean desc) {
        orderBy(FavouritesColumns.BACKDROP, desc);
        return this;
    }

    public FavouritesSelection orderByBackdrop() {
        orderBy(FavouritesColumns.BACKDROP, false);
        return this;
    }

    public FavouritesSelection overview(String... value) {
        addEquals(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection overviewNot(String... value) {
        addNotEquals(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection overviewLike(String... value) {
        addLike(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection overviewContains(String... value) {
        addContains(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection overviewStartsWith(String... value) {
        addStartsWith(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection overviewEndsWith(String... value) {
        addEndsWith(FavouritesColumns.OVERVIEW, value);
        return this;
    }

    public FavouritesSelection orderByOverview(boolean desc) {
        orderBy(FavouritesColumns.OVERVIEW, desc);
        return this;
    }

    public FavouritesSelection orderByOverview() {
        orderBy(FavouritesColumns.OVERVIEW, false);
        return this;
    }

    public FavouritesSelection userRating(String... value) {
        addEquals(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection userRatingNot(String... value) {
        addNotEquals(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection userRatingLike(String... value) {
        addLike(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection userRatingContains(String... value) {
        addContains(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection userRatingStartsWith(String... value) {
        addStartsWith(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection userRatingEndsWith(String... value) {
        addEndsWith(FavouritesColumns.USER_RATING, value);
        return this;
    }

    public FavouritesSelection orderByUserRating(boolean desc) {
        orderBy(FavouritesColumns.USER_RATING, desc);
        return this;
    }

    public FavouritesSelection orderByUserRating() {
        orderBy(FavouritesColumns.USER_RATING, false);
        return this;
    }

    public FavouritesSelection releaseDate(String... value) {
        addEquals(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection releaseDateNot(String... value) {
        addNotEquals(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection releaseDateLike(String... value) {
        addLike(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection releaseDateContains(String... value) {
        addContains(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection releaseDateStartsWith(String... value) {
        addStartsWith(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection releaseDateEndsWith(String... value) {
        addEndsWith(FavouritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavouritesSelection orderByReleaseDate(boolean desc) {
        orderBy(FavouritesColumns.RELEASE_DATE, desc);
        return this;
    }

    public FavouritesSelection orderByReleaseDate() {
        orderBy(FavouritesColumns.RELEASE_DATE, false);
        return this;
    }

    public FavouritesSelection movieId(String... value) {
        addEquals(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection movieIdNot(String... value) {
        addNotEquals(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection movieIdLike(String... value) {
        addLike(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection movieIdContains(String... value) {
        addContains(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection movieIdStartsWith(String... value) {
        addStartsWith(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection movieIdEndsWith(String... value) {
        addEndsWith(FavouritesColumns.MOVIE_ID, value);
        return this;
    }

    public FavouritesSelection orderByMovieId(boolean desc) {
        orderBy(FavouritesColumns.MOVIE_ID, desc);
        return this;
    }

    public FavouritesSelection orderByMovieId() {
        orderBy(FavouritesColumns.MOVIE_ID, false);
        return this;
    }

    public FavouritesSelection trailer(String... value) {
        addEquals(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection trailerNot(String... value) {
        addNotEquals(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection trailerLike(String... value) {
        addLike(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection trailerContains(String... value) {
        addContains(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection trailerStartsWith(String... value) {
        addStartsWith(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection trailerEndsWith(String... value) {
        addEndsWith(FavouritesColumns.TRAILER, value);
        return this;
    }

    public FavouritesSelection orderByTrailer(boolean desc) {
        orderBy(FavouritesColumns.TRAILER, desc);
        return this;
    }

    public FavouritesSelection orderByTrailer() {
        orderBy(FavouritesColumns.TRAILER, false);
        return this;
    }
}
