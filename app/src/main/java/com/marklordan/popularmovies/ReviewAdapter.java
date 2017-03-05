package com.marklordan.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mark on 02/03/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    private List<Movie.Review> mReviews;


    public ReviewAdapter(List<Movie.Review> reviews, Context context){
        this.mContext = context;
        mReviews = reviews;
    }
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.review_list_item, parent, shouldAttachToParentImmediately);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        private TextView mReviewContent, mReviewReviewer;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mReviewContent = (TextView) itemView.findViewById(R.id.review_content);
            mReviewReviewer = (TextView) itemView.findViewById(R.id.reviewer_username);
        }
        public void bind(){
            mReviewContent.setText(mReviews.get(getAdapterPosition()).getContent());
            mReviewReviewer.setText(
                    String.format(mContext.getResources().getString(R.string.review_author_title),
                            mReviews.get(getAdapterPosition()).getAuthor()));
        }
    }
}
