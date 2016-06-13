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
import android.util.Log;
import android.widget.ImageView;

import com.orpheusdroid.moviebox.Constants;
import com.orpheusdroid.moviebox.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vijai on 10-06-2016.
 */
public class MovieDetailsHandler extends AsyncTask<String, Void, ArrayList<ReviewsDataHolder>> {
    private MovieDataHolder movie;
    private CollapsingToolbarLayout collapsingToolbar;
    private Context mContext;
    private Bitmap banner;
    private boolean isActivity;
    private ImageView iv;
    private RecyclerView mRecyclerView;
    private MovieDetailsAdapter mAdapter;
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

    public static final int getColorResource(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    private void initList() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieDetailsAdapter(mContext, movie);
        mRecyclerView.setAdapter(mAdapter);
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

    private void getBitmap(String bannerUrl) {
        try {
            URL url = new URL(bannerUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            banner = BitmapFactory.decodeStream(input);
            connection.disconnect();
        } catch (IOException e) {
            // Log exception
        }
    }

    private JSONObject getReviews(String reviewsUrl) {
        try {
            URL url = new URL(reviewsUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            connection.disconnect();
            return new JSONObject(builder.toString());
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    protected ArrayList<ReviewsDataHolder> doInBackground(String... strings) {
        getBitmap(strings[0]);
        JSONObject topLevel = getReviews(strings[1]);
        ArrayList<ReviewsDataHolder> reviews = new ArrayList<>();
        try {
            JSONArray main = topLevel.getJSONArray("results");
            for (int i = 0; i < main.length(); i++) {
                JSONObject reviewJson = main.getJSONObject(i);
                ReviewsDataHolder review = new ReviewsDataHolder(movie.getId(),
                        reviewJson.getString(Constants.REVIEW_AUTHOR_TAG),
                        reviewJson.getString(Constants.REVIEW_CONTENT_TAG),
                        reviewJson.getString(Constants.REVIEW_ID_TAG));
                reviews.add(review);
                Log.d("Reviews added", "Review is added to dataSet");
            }
        } catch (JSONException | java.lang.NullPointerException e) {
            e.printStackTrace();
        }
        return reviews;
    }


    @Override
    protected void onPostExecute(ArrayList<ReviewsDataHolder> reviews) {
        iv.setImageBitmap(banner);
        if (isActivity)
            setImageToToolbar(banner);
        mAdapter.swap(movie, reviews);
    }
}