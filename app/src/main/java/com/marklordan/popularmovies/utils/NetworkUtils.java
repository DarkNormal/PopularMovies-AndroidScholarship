package com.marklordan.popularmovies.utils;

import android.net.Uri;

/**
 * Created by Mark on 24/01/2017.
 */

public class NetworkUtils {
    //TODO INSERT API KEY HERE
    private final static String MOVIEDB_API_KEY = "INSERT_API_KEY_HERE";

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
