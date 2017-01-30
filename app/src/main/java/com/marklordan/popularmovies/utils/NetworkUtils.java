package com.marklordan.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mark on 24/01/2017.
 */

public class NetworkUtils {
    final static String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    final static String MOVIEDB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    final static String MOVIEDB_API_KEY = "INSERT_API_KEY_HERE";

    public static String buildUrl(String movieSortQuery){
        Uri.Builder builder = new Uri.Builder().scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieSortQuery)
                .appendQueryParameter("api_key", MOVIEDB_API_KEY);
        Uri uri = builder.build();
        String url = uri.toString();
        return url;
    }
}
