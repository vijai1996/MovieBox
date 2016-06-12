package com.orpheusdroid.moviebox.ContentProvider.favourites;

import android.net.Uri;
import android.provider.BaseColumns;

import com.orpheusdroid.moviebox.ContentProvider.FavouritesProvider;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesColumns;

/**
 * Columns for the {@code favourites} table.
 */
public class FavouritesColumns implements BaseColumns {
    public static final String TABLE_NAME = "favourites";
    public static final Uri CONTENT_URI = Uri.parse(FavouritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String POSTER_PATH = "poster_path";

    public static final String BACKDROP = "backdrop";

    public static final String OVERVIEW = "overview";

    public static final String USER_RATING = "user_rating";

    public static final String RELEASE_DATE = "release_date";

    public static final String MOVIE_ID = "movie_id";

    public static final String TRAILER = "trailer";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            POSTER_PATH,
            BACKDROP,
            OVERVIEW,
            USER_RATING,
            RELEASE_DATE,
            MOVIE_ID,
            TRAILER
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(BACKDROP) || c.contains("." + BACKDROP)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(USER_RATING) || c.contains("." + USER_RATING)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(TRAILER) || c.contains("." + TRAILER)) return true;
        }
        return false;
    }

}
