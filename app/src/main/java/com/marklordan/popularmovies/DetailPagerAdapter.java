package com.marklordan.popularmovies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mark on 01/03/17.
 */

public class DetailPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Details", "Trailers", "Reviews"};
    private Context context;
    private Movie mMovie;

    public DetailPagerAdapter(FragmentManager fm, Context context, Movie movie) {
        super(fm);
        this.context = context;
        this.mMovie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return MovieDetailFragment.newInstance(position + 1, mMovie);
        }
        else if(position == 1){
            return TrailerFragment.newInstance(position + 1, mMovie.getTrailers());
        }
        else{
            return ReviewFragment.newInstance(position + 1, mMovie.getReviews());
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
