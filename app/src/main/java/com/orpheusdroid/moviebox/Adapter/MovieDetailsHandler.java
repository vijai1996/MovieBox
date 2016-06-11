package com.orpheusdroid.moviebox.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.orpheusdroid.moviebox.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vijai on 10-06-2016.
 */
public class MovieDetailsHandler extends AsyncTask<String, Void, Bitmap> {
    private MovieDataHolder movie;
    private CollapsingToolbarLayout collapsingToolbar;
    private Context mContext;
    private boolean isActivity;
    private ImageView iv;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public MovieDetailsHandler(Context context, RecyclerView recyclerView, CollapsingToolbarLayout toolbarLayout,
                               ImageView banner, MovieDataHolder movie, boolean isActivity){
        mContext = context;
        mRecyclerView = recyclerView;
        collapsingToolbar = toolbarLayout;
        iv = banner;
        this.isActivity = isActivity;
        this.movie = movie;
    }

    private void initList() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieDetailsAdapter(mContext, movie);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static final int getColorResource(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    private void setImageToToolbar(Bitmap banner) {
        Palette.from(banner).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {

                collapsingToolbar.setContentScrimColor(palette.getMutedColor(
                        getColorResource(mContext, R.color.colorPrimary)));
                collapsingToolbar.setStatusBarScrimColor(palette.getMutedColor(
                        getColorResource(mContext, R.color.colorPrimaryDark)));
            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        initList();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap banner) {
        iv.setImageBitmap(banner);
        if (isActivity)
            setImageToToolbar(banner);
    }
}