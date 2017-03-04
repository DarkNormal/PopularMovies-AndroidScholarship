package com.marklordan.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mark on 04/03/17.
 */

public class FavouriteMoviesContract {


    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.marklordan.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "favouriteMovies";

    public static final class FavouriteMoviesEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_RATING = "movieRating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_PLOT = "moviePlot";
        public static final String COLUMN_MOVIE_POSTER = "moviePosterURL";
        public static final String COLUMN_MOVIE_BACKDROP = "movieBackdrop";
    }
}
