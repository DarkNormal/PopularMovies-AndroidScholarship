package com.marklordan.popularmovies;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.marklordan.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_PARAM = "com.marklordan.popularmovies.DETAILS_MOVIE";
    private Movie mMovie;

    private ImageView mMoviePoster, mBackdrop;
    private TextView mMovieTitle, mMoviePlot;
    private TextView mMovieRating, mMovieReleaseDate;
    private RequestQueue mRequestQueue;


    public static Intent newInstance(Context context, Movie movie) {
        Intent intent  = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_PARAM, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        try{
            mMovie = (Movie) bundle.getSerializable(MOVIE_PARAM);
        }catch (ClassCastException e){
            Log.e("MovieDetailActivity", e.getMessage());
        }
        setContentView(R.layout.activity_movie_detail);

        mRequestQueue = Volley.newRequestQueue(this);
        getTrailers(mMovie.getmId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mMovie.getTitle());
        }catch(NullPointerException e){
            Log.e("MovieDetailActivity", e.getMessage());
        }

        mMoviePoster = (ImageView) findViewById(R.id.movie_detail_poster);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185//" + mMovie.getPosterPath()).into(mMoviePoster);

        mBackdrop = (ImageView) findViewById(R.id.movie_detail_header);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342//" + mMovie.getBackdropPath()).into(mBackdrop);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animateBackdrop();
        }

        mMovieTitle = (TextView) findViewById(R.id.movie_detail_title);
        mMovieTitle.setText(mMovie.getTitle());

        mMoviePlot = (TextView) findViewById(R.id.movie_detail_plot);
        mMoviePlot.setText(mMovie.getPlotSynopsis());

        mMovieRating = (TextView) findViewById(R.id.movie_detail_rating);
        DecimalFormat df = new DecimalFormat("#.##");
        String ratingText = String.format(getString(R.string.detail_rating), df.format(mMovie.getRating()));
        mMovieRating.setText(ratingText);

        mMovieReleaseDate = (TextView) findViewById(R.id.movie_detail_release_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mMovieReleaseDate.setText(String.format(getString(R.string.detail_release_date), sdf.format(mMovie.getReleaseDate())));


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager(),
                MovieDetailActivity.this, mMovie));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getTrailers(int movieId){
        String url = NetworkUtils.buildMovieExtraDetailsUrl(movieId, "videos");
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray resultsArray;
                        try {
                            Gson gson = new Gson();
                            resultsArray = response.getJSONArray("results");
                            ArrayList<Movie.Trailer> trailers = new ArrayList<>();
                            for (int i = 0; i < resultsArray.length(); i++) {
                                Movie.Trailer trailer = gson.fromJson(String.valueOf(resultsArray.getJSONObject(i)), Movie.Trailer.class);
                                trailers.add(trailer);
                            }
                            mMovie.setTrailers(trailers);
                            Log.i("DetailActivity", trailers.size() + " ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", "no response");
            }
        });
        mRequestQueue.add(stringRequest);
    }

    private void animateBackdrop(){
        mBackdrop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGlobalLayout() {
                final View myView = mBackdrop;

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight();

                // get the final radius for the clipping circle
                int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);


                // make the view visible and start the animation
                myView.setVisibility(View.VISIBLE);
                anim.start();
            }
        });
    }
}
