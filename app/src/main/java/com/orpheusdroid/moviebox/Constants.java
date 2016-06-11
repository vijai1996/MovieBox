package com.orpheusdroid.moviebox;

/**
 * Created by vijai on 10-06-2016.
 */
public class Constants {
    /*
    Nothing much for comment here. Just a bunch of constant strings for fetching data from api
     */
    public static String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static String POSTER_BASE_PATH = "https://image.tmdb.org/t/p/w500/";
    public static String BACKDROP_BASE_PATH = "https://image.tmdb.org/t/p/w780/";
    public static String POPULAR = "popular";
    public static String TOP_RATED = "top_rated";
    public static String API_KEY;
    public static String TITLE_TAG = "original_title";
    public static String POSTER_PATH_TAG = "poster_path";
    public static String OVERVIEW_TAG = "overview";
    public static String USER_RATING_TAG = "vote_average";
    public static String RELEASE_DATE_TAG = "release_date";
    public static String BACKDROP_TAG = "backdrop_path";
    public static String MOVIE_ID = "id";

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
