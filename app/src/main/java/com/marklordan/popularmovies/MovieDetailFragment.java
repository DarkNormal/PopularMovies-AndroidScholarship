package com.marklordan.popularmovies;

import android.animation.Animator;
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
        
        return view;
    }


}
