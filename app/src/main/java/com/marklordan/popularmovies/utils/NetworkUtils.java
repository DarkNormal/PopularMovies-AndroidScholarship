package com.marklordan.popularmovies.utils;

import android.net.Uri;

import com.marklordan.popularmovies.BuildConfig;

/**
 * Created by Mark on 24/01/2017.
 */

public class NetworkUtils {
    //TODO INSERT API KEY HERE
    private final static String MOVIEDB_API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static String buildUrl(String movieSortQuery){
        Uri.Builder builder = new Uri.Builder().scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieSortQuery)
                .appendQueryParameter("api_key", MOVIEDB_API_KEY);
        Uri uri = builder.build();
        return uri.toString();
    }
}
