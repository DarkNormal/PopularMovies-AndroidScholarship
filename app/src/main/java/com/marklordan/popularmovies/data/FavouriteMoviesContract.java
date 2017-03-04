package com.marklordan.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by mark on 04/03/17.
 */

public class FavouriteMoviesContract {

    public static final class FavouriteMoviesEntry implements BaseColumns{
        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
    }
}
