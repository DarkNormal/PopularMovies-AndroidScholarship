package com.marklordan.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.marklordan.popularmovies.data.FavouriteMoviesContract;
import com.marklordan.popularmovies.data.FavouriteMoviesDbHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

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

        Cursor cursor = getContext().getContentResolver().query(FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI,
                null,
                FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " = " + mMovie.getmId(),
                null,
                null,
                null);

        if (cursor != null) {
            while(cursor.moveToNext()){
                mLikeButton.setLiked(true);
            }
        }
        cursor.close();


        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ContentValues cv = buildMovieContentValues();
                addMovieToFavouriteDb(cv);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeMovieFromFavouriteDb();
            }
        });

        return view;
    }
    private ContentValues buildMovieContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID, mMovie.getmId());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_TITLE, mMovie.getTitle());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_PLOT, mMovie.getPlotSynopsis());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_RATING, mMovie.getRating());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_RELEASE_DATE, mMovie.getReleaseDate().getTime());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_POSTER, mMovie.getPosterPath());
        cv.put(FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_BACKDROP, mMovie.getBackdropPath());
        return cv;
    }

    private void addMovieToFavouriteDb(ContentValues cv){
        getContext().getContentResolver().insert(FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI, cv);
    }

    private void removeMovieFromFavouriteDb(){
        String id = Integer.toString(mMovie.getmId());
        Uri uri = FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        getContext().getContentResolver().delete(uri, null,null);

    }


}
