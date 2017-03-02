package com.marklordan.popularmovies;

/**
 * Created by mark on 01/03/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Mark on 23/01/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private Context mContext;
    private List<Movie.Trailer> mTrailers;
    private final TrailerClickListener mTrailerClickListener;

    public TrailerAdapter(List<Movie.Trailer> trailers, Context context, TrailerClickListener trailerClickListener){
        this.mContext = context;
        mTrailers = trailers;
        mTrailerClickListener = trailerClickListener;
    }
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.trailer_list_item, parent, shouldAttachToParentImmediately);
        return new TrailerViewHolder(view);
    }

    public interface TrailerClickListener{
        void onItemClick(int itemClicked);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTrailerTitle;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTrailerTitle = (TextView) itemView.findViewById(R.id.trailer_title);
            itemView.setOnClickListener(this);
        }
        public void bind(){
            if(mTrailers.size()> 0){
                mTrailerTitle.setText(mTrailers.get(getAdapterPosition()).getTrailerName());
            }

        }
        @Override
        public void onClick(View v) {
            mTrailerClickListener.onItemClick(getAdapterPosition());
        }
    }
}
