package com.marklordan.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mark on 23/01/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private String[] mPosterPaths;

    public MovieAdapter(String[] moviePosterPaths, Context context){
        this.mContext = context;
        mPosterPaths = moviePosterPaths;

    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_grid_item, parent, shouldAttachToParentImmediately);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mPosterPaths.length;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView mMoviePosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.item_movie_poster);
        }
        public void bind(){
            if(mPosterPaths.length > 0){
                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185//" + mPosterPaths[getAdapterPosition()]).into(mMoviePosterImageView);
            }

        }
    }
}
