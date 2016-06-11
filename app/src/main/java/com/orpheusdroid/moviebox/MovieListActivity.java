package com.orpheusdroid.moviebox;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.orpheusdroid.moviebox.Adapter.GridLayoutAdapter;
import com.orpheusdroid.moviebox.Adapter.MovieDataHolder;

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
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutAdapter adapter;
    private View recyclerView;
    private JSONObject jsonPopular,jsonTopRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        addSpinner(toolbar);

        Constants.setApiKey(getResources().getString(R.string.themoviedb_api_key));

        recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView((RecyclerView) recyclerView, new ArrayList<MovieDataHolder>());

        new HttpRequest(Constants.POPULAR).execute(Constants.API_BASE_URL + Constants.POPULAR + "/?api_key=" + Constants.API_KEY);
    }

    private void addSpinner(Toolbar toolbar){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_spinner, R.layout.layout_toolbar_spinner_title);
        adapter.setDropDownViewResource(R.layout.layout_toolbar_spinner_list);

        Spinner mNavigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        mNavigationSpinner.setAdapter(adapter);
        toolbar.addView(mNavigationSpinner);

        mNavigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(MovieListActivity.this, "Most Popular", Toast.LENGTH_SHORT).show();
                        new HttpRequest(Constants.POPULAR).execute(Constants.API_BASE_URL + Constants.POPULAR + "/?api_key=" + Constants.API_KEY);
                        break;
                    case 1:
                        Toast.makeText(MovieListActivity.this, "Highest Rated", Toast.LENGTH_SHORT).show();
                        new HttpRequest(Constants.TOP_RATED).execute(Constants.API_BASE_URL + Constants.TOP_RATED + "/?api_key=" + Constants.API_KEY);
                        Log.d("High rated URL", Constants.API_BASE_URL + Constants.TOP_RATED + "/?api_key=" + Constants.API_KEY);
                        break;
                    case 2:
                        Toast.makeText(MovieListActivity.this, getResources().getText(R.string.stage2), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ArrayList<MovieDataHolder> movies) {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new GridLayoutAdapter(this, movies, mTwoPane);
        recyclerView.setAdapter(adapter);
    }

    private void updateDataset(ArrayList<MovieDataHolder> newMovies){
        adapter.swap(newMovies);
    }

    private class HttpRequest extends AsyncTask<String, Void, ArrayList<MovieDataHolder>> {
        private ProgressDialog dialog;
        private String mCategory;

        public HttpRequest(String category){
            mCategory = category;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MovieListActivity.this, getResources().getString(R.string.wait_title),
                    getResources().getString(R.string.wait_message), true);
        }

        private String getJson(String sURL){
            try{
                URL url = new URL(sURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15001);
                conn.setConnectTimeout(15001);
                conn.setDoInput(true);

                int code = conn.getResponseCode();
                Log.d("Connection code", ""+code);
                InputStream stream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }
                conn.disconnect();
                return builder.toString();
            } catch (UnknownHostException e){
                runOnUiThread(new Runnable(){
                    public void run() {
                        dialog.dismiss();
                        new AlertDialog.Builder(MovieListActivity.this)
                                .setTitle(getResources().getString(R.string.no_internet_title))
                                .setMessage(getResources().getString(R.string.no_internet_message))
                                .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MovieListActivity.this.finish();
                                    }
                                })
                                .show();
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected ArrayList<MovieDataHolder> doInBackground(String... strings) {
            ArrayList<MovieDataHolder> dataHolder = new ArrayList<>();
            try {

                JSONObject topLevel = new JSONObject();
                if(mCategory.equals(Constants.POPULAR )){
                    if (jsonPopular == null)
                        jsonPopular = new JSONObject(getJson(strings[0]));
                    topLevel = jsonPopular;
                }
                if(mCategory.equals(Constants.TOP_RATED )){
                    if (jsonTopRated == null)
                        jsonTopRated = new JSONObject(getJson(strings[0]));
                    topLevel = jsonTopRated;
                }
                JSONArray main = topLevel.getJSONArray("results");

                for (int i=0; i<main.length(); i++){
                    JSONObject movieObject = main.getJSONObject(i);
                    String id = movieObject.getString(Constants.MOVIE_ID);
                    String TrailerUrl = Constants.API_BASE_URL+id+"/videos?api_key="+Constants.API_KEY;
                    JSONObject trailerObj = new JSONObject(getJson(TrailerUrl));
                    JSONArray trailerLinks = trailerObj.getJSONArray("results");
                    String trailerKey = "";
                    if (trailerLinks.length()>0){
                        trailerKey = trailerLinks.getJSONObject(0).getString("key");
                    }
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
            updateDataset(datas);
            dialog.dismiss();
        }
    }
}
