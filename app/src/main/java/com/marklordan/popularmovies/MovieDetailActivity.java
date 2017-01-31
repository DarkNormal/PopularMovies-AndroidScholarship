package com.marklordan.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_DETAIL_TITLE = "com.marklordan.popularmovies.DETAILS_TITLE";
    private static final String MOVIE_DETAIL_PLOT = "com.marklordan.popularmovies.DETAILS_PLOT";

    public static Intent newInstance(Context context, Movie movie) {
        Intent intent  = new Intent(context, MovieDetailActivity.class);
        //TODO use serializable to save movie
        intent.putExtra(MOVIE_DETAIL_TITLE, movie.getTitle());
        intent.putExtra(MOVIE_DETAIL_PLOT, movie.getPlotSynopsis());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }
}
