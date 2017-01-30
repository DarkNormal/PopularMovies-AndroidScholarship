package com.marklordan.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mark on 23/01/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovies;
    private final MovieItemClickListener mClickListener;

    public MovieAdapter(ArrayList<Movie> movies, Context context, MovieItemClickListener clickListener){
        this.mContext = context;
        mMovies = movies;
        mClickListener = clickListener;

    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_grid_item, parent, shouldAttachToParentImmediately);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    public interface MovieItemClickListener{
        void onItemClick(int itemClicked);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mMoviePosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.item_movie_poster);
            itemView.setOnClickListener(this);
        }
        public void bind(){
            if(mMovies.size()> 0){
                Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185//" + mMovies.get(getAdapterPosition()).getPosterPath()).into(mMoviePosterImageView);
            }

        }
        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }
}
