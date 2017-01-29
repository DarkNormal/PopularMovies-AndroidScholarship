package com.marklordan.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mark on 28/01/2017.
 */

public class Movie implements Serializable{

    @SerializedName("id")
    private int mId;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mPlotSynopsis;
    @SerializedName("vote_average")
    private double mRating;
    @SerializedName("release_date")
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
