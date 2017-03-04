package com.marklordan.popularmovies;

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
 * Created by mark on 02/03/17.
 */

public class ReviewFragment extends Fragment {

    public static final String ARG_REVIEW = "ARG_REVIEW";

    private List<Movie.Review> reviews;
    private RecyclerView mRecyclerview;

    public static ReviewFragment newInstance(int page, List<Movie.Review> reviews) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_REVIEW, (Serializable) reviews);
        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviews = (List<Movie.Review>) getArguments().getSerializable(ARG_REVIEW);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extra_movie_details, container, false);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.details_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerview.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerview.addItemDecoration(dividerItemDecoration);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        if(reviews != null) {
            ReviewAdapter mAdapter = new ReviewAdapter(reviews, getContext());
            mRecyclerview.setAdapter(mAdapter);

        }
        return view;
    }
}
