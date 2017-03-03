package com.marklordan.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mark on 01/03/17.
 */

public class TrailerFragment extends Fragment implements TrailerAdapter.TrailerClickListener{

    public static final String ARG_TRAILER = "ARG_TRAILER";

    private List<Movie.Trailer> trailers;
    private RecyclerView mRecyclerview;

    public static TrailerFragment newInstance(int page, List<Movie.Trailer> trailers) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRAILER, (Serializable) trailers);
        TrailerFragment fragment = new TrailerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trailers = (List<Movie.Trailer>) getArguments().getSerializable(ARG_TRAILER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extra_movie_details, container, false);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.details_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerview.getContext(),
                layoutManager.getOrientation());
        mRecyclerview.addItemDecoration(dividerItemDecoration);
        mRecyclerview.setLayoutManager(layoutManager);

        if(trailers != null) {
            TrailerAdapter mAdapter = new TrailerAdapter(trailers, getContext(), this);
            mRecyclerview.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onItemClick(int itemClicked) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailers.get(itemClicked).getYoutubeKey())));
    }
}
