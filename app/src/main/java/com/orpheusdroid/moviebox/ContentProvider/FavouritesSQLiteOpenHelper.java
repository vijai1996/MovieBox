package com.orpheusdroid.moviebox.ContentProvider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.orpheusdroid.moviebox.BuildConfig;
import com.orpheusdroid.moviebox.ContentProvider.favourites.FavouritesColumns;

public class FavouritesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = FavouritesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 1;
    private static FavouritesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final FavouritesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVOURITES = "CREATE TABLE IF NOT EXISTS "
            + FavouritesColumns.TABLE_NAME + " ( "
            + FavouritesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavouritesColumns.TITLE + " TEXT, "
            + FavouritesColumns.POSTER_PATH + " TEXT, "
            + FavouritesColumns.BACKDROP + " TEXT, "
            + FavouritesColumns.OVERVIEW + " TEXT, "
            + FavouritesColumns.USER_RATING + " TEXT, "
            + FavouritesColumns.RELEASE_DATE + " TEXT, "
            + FavouritesColumns.MOVIE_ID + " TEXT, "
            + FavouritesColumns.TRAILER + " TEXT "
            + ", CONSTRAINT unique_name UNIQUE (movie_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_FAVOURITES_MOVIE_ID = "CREATE INDEX IDX_FAVOURITES_MOVIE_ID "
            + " ON " + FavouritesColumns.TABLE_NAME + " ( " + FavouritesColumns.MOVIE_ID + " );";

    // @formatter:on

    public static FavouritesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static FavouritesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static FavouritesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new FavouritesSQLiteOpenHelper(context);
    }

    private FavouritesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new FavouritesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static FavouritesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new FavouritesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private FavouritesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new FavouritesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVOURITES);
        db.execSQL(SQL_CREATE_INDEX_FAVOURITES_MOVIE_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
