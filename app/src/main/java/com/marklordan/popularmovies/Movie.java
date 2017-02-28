package com.marklordan.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Mark on 28/01/2017.
 */

class Movie implements Serializable{



    @SerializedName("id")
    private int mId;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mPlotSynopsis;
    @SerializedName("vote_average")
    private double mRating;
    @SerializedName("release_date")
    private Date mReleaseDate;

    private List<String> mYoutubeKeys;

    public Movie(int id, String posterPath, String backdropPath, String title, String plotSynopsis, double rating, Date releaseDate) {
        mId = id;
        mPosterPath = posterPath;
        mBackdropPath = backdropPath;
        mTitle = title;
        mPlotSynopsis = plotSynopsis;
        mRating = rating;
        mReleaseDate = releaseDate;
    }

    public int getmId() {
        return mId;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
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

    public void setYoutubeKeys(List<String> youtubeKeys){
        mYoutubeKeys = youtubeKeys;
    }
}
