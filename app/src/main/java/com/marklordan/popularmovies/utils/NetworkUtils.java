package com.marklordan.popularmovies.utils;

import android.net.Uri;

import com.marklordan.popularmovies.BuildConfig;

public class NetworkUtils {
    //TODO INSERT API KEY HERE
    private final static String MOVIEDB_API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static Uri buildBaseUrl(){
        Uri.Builder builder = new Uri.Builder().scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendQueryParameter("api_key", MOVIEDB_API_KEY);
        return builder.build();
    }

    public static String buildUrl(String movieSortQuery){
        Uri uri = buildBaseUrl().buildUpon()
                .appendPath(movieSortQuery)
                .build();
        return uri.toString();
    }

    public static String buildMovieExtraDetailsUrl(int movieId, String trailersOrReviews){
        Uri uri = buildBaseUrl().buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath(trailersOrReviews)
                .build();
        return uri.toString();
    }
}
