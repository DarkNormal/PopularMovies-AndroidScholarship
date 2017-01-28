package com.marklordan.popularmovies;

import java.util.Date;

/**
 * Created by Mark on 28/01/2017.
 */

public class Movie {

    private String mPosterPath;
    private String mTitle;
    private String mPlotSynopsis;
    private double mRating;
    private Date mReleaseDate;

    public Movie(String posterPath, String title, String plotSynopsis, double rating, Date releaseDate) {
        mPosterPath = posterPath;
        mTitle = title;
        mPlotSynopsis = plotSynopsis;
        mRating = rating;
        mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public double getRating() {
        return mRating;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }
}
