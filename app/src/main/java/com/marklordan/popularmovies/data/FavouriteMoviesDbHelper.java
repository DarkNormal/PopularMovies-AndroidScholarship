package com.marklordan.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marklordan.popularmovies.data.FavouriteMoviesContract.*;

/**
 * Created by mark on 04/03/17.
 */

public class FavouriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favouriteMovies.db";
    private static final int DATABASE_VERSION = 2;

    public FavouriteMoviesDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVOURITE_MOVIES_TABLE = "CREATE TABLE " + FavouriteMoviesEntry.TABLE_NAME + " (" +
                FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FavouriteMoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_PLOT + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_RATING + " REAL NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " INTEGER NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                FavouriteMoviesEntry.COLUMN_MOVIE_BACKDROP + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesEntry.TABLE_NAME);
        onCreate(db);

    }
}
