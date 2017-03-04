package com.marklordan.popularmovies;

import android.animation.Animator;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.marklordan.popularmovies.data.FavouriteMoviesContract;
import com.marklordan.popularmovies.data.FavouriteMoviesDbHelper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by mark on 03/03/17.
 */

public class MovieDetailFragment extends Fragment {

    public static final String ARG_MOVIE = "ARG_MOVIE";
    private Movie mMovie;

    private ImageView mMoviePoster;
    private TextView mMovieTitle, mMoviePlot;
    private TextView mMovieRating, mMovieReleaseDate;
    private LikeButton mLikeButton;
    private SQLiteDatabase mDb;


    public static MovieDetailFragment newInstance(int page, Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = (Movie) getArguments().getSerializable(ARG_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mMoviePoster = (ImageView) view.findViewById(R.id.movie_detail_poster);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + mMovie.getPosterPath()).into(mMoviePoster);



        mMovieTitle = (TextView) view.findViewById(R.id.movie_detail_title);
        mMovieTitle.setText(mMovie.getTitle());

        mMoviePlot = (TextView) view.findViewById(R.id.movie_detail_plot);
        mMoviePlot.setText(mMovie.getPlotSynopsis());

        mMovieRating = (TextView) view.findViewById(R.id.movie_detail_rating);
        DecimalFormat df = new DecimalFormat("#.##");
        String ratingText = String.format(getString(R.string.detail_rating), df.format(mMovie.getRating()));
        mMovieRating.setText(ratingText);

        mMovieReleaseDate = (TextView) view.findViewById(R.id.movie_detail_release_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mMovieReleaseDate.setText(String.format(getString(R.string.detail_release_date), sdf.format(mMovie.getReleaseDate())));

        FavouriteMoviesDbHelper dbHelper = new FavouriteMoviesDbHelper(getContext());

        mDb = dbHelper.getWritableDatabase();

        mLikeButton = (LikeButton) view.findViewById(R.id.favourite_button);

        Cursor cursor = mDb.query(
                FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                null,
                FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " = " + mMovie.getmId(),
                null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            mLikeButton.setLiked(true);
        }


        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ContentValues cv = buildMovieContentValues();
                long addedId = addMovieToFavouriteDb(cv);
                System.out.println(addedId);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                boolean deleted = removeMovieFromFavouriteDb();
                System.out.println(deleted);
            }
        });






        
        return view;
    }
    private ContentValues buildMovieContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID, mMovie.getmId());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_TITLE, mMovie.getTitle());
        return cv;
    }

    private long addMovieToFavouriteDb(ContentValues cv){
        return mDb.insert(FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME, null, cv);
    }

    private boolean removeMovieFromFavouriteDb(){
        return mDb.delete(FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " = " + mMovie.getmId(), null) > 0;

    }


}
