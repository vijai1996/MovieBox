package com.orpheusdroid.moviebox.adapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.orpheusdroid.moviebox.Constants;
import com.orpheusdroid.moviebox.MovieListActivity;
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
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by vijai on 19-06-2016.
 */
public class HttpRequest extends AsyncTask<String, Void, ArrayList<MovieDataHolder>> {
    private JSONObject jsonPopular, jsonTopRated;
    private ProgressDialog dialog;
    private String mCategory;
    private MovieListActivity.ApiCallback listener;
    private MovieListActivity mContext;

    public HttpRequest(MovieListActivity context, String category, MovieListActivity.ApiCallback listener) {
        mCategory = category;
        this.listener = listener;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show a progressdialog to indicate the user the data is being fetched
        dialog = ProgressDialog.show(mContext, mContext.getResources().getString(R.string.wait_title),
                mContext.getResources().getString(R.string.wait_message), true);
    }

    //Breakup method for the actual Urlconnection to fetch the json data from the api
    private String getJson(String sURL) {
        try {
            URL url = new URL(sURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);

            int code = conn.getResponseCode();
            Log.d("Connection code", "" + code);
            InputStream stream = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            conn.disconnect();
            return builder.toString();
        } catch (UnknownHostException e) {
                /*
                If the code reaches this point, there seem to be some issue with the user's internet
                Let's notify them
                 */
            mContext.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                    new AlertDialog.Builder(mContext)
                            .setTitle(mContext.getResources().getString(R.string.no_internet_title))
                            .setMessage(mContext.getResources().getString(R.string.no_internet_message))
                            .setNeutralButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mContext.finish();
                                }
                            })
                            .show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected ArrayList<MovieDataHolder> doInBackground(String... strings) {
        ArrayList<MovieDataHolder> dataHolder = new ArrayList<>();
        try {

            JSONObject topLevel = new JSONObject();
            if (mCategory.equals(Constants.POPULAR)) {
                if (jsonPopular == null)
                    jsonPopular = new JSONObject(getJson(strings[0]));
                topLevel = jsonPopular;
            }
            if (mCategory.equals(Constants.TOP_RATED)) {
                if (jsonTopRated == null)
                    jsonTopRated = new JSONObject(getJson(strings[0]));
                topLevel = jsonTopRated;
            }
                /*
                There is some creepy caching of the json data above.
                We dont want to fetch data every time a new criteria is selected
                 */
            JSONArray main = topLevel.getJSONArray("results");

            for (int i = 0; i < main.length(); i++) {
                JSONObject movieObject = main.getJSONObject(i);
                String id = movieObject.getString(Constants.MOVIE_ID);
                String TrailerUrl = Constants.API_BASE_URL + id + "/videos?api_key=" + Constants.API_KEY;
                Log.d("Trailer URL", TrailerUrl);
                JSONObject trailerObj = new JSONObject(getJson(TrailerUrl));
                JSONArray trailerLinks = trailerObj.getJSONArray("results");
                String trailerKey = "";
                if (trailerLinks.length() > 0) {
                    trailerKey = trailerLinks.getJSONObject(0).getString("key");
                }

                //Parse the json and create a new custom object to hold the data
                MovieDataHolder movie = new MovieDataHolder(
                        movieObject.getString(Constants.TITLE_TAG),
                        Constants.POSTER_BASE_PATH + movieObject.getString(Constants.POSTER_PATH_TAG),
                        movieObject.getString(Constants.OVERVIEW_TAG),
                        movieObject.getString(Constants.USER_RATING_TAG),
                        movieObject.getString(Constants.RELEASE_DATE_TAG),
                        Constants.BACKDROP_BASE_PATH + movieObject.getString(Constants.BACKDROP_TAG),
                        id,
                        trailerKey
                );
                dataHolder.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataHolder;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDataHolder> datas) {
        //Update the recycler view and dismiss the dialog
        //updateDataset(datas);
        listener.onSuccess(datas);
        dialog.dismiss();
        //showIntro();
    }
}