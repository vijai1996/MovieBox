package com.orpheusdroid.moviebox;

import com.orpheusdroid.moviebox.adapter.MovieDataHolder;

import java.util.ArrayList;

/**
 * Created by vijai on 19-06-2016.
 */
public interface UiCallbacks {
    void onMoviesReceived(ArrayList<MovieDataHolder> datas);
}
