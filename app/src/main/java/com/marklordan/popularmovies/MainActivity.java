package com.marklordan.popularmovies;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.marklordan.popularmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener, IOrderSelection{

    private static final String MOVIE_LIST_KEY = "com.marklordan.popularmovies.MOVIE_LIST";
    private static final String MOVIE_ORDER_FRAGMENT = "com.marklordan.popularmovies.ORDER_FRAGMENT";


    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ArrayList<Movie> mMovies;
    private RequestQueue mQueue;
    private String mSortOrder;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        mSortOrder = getString(R.string.popular);

        if(savedInstanceState != null){
            mMovies = (ArrayList<Movie>) savedInstanceState.getSerializable(MOVIE_LIST_KEY);
        }
        else{
            mMovies = new ArrayList<>();
            getMoviesFromApi(mSortOrder);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new MovieAdapter(mMovies, this, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getMoviesFromApi(String sortOrder){
        String url = NetworkUtils.buildUrl(sortOrder);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, "got a response", Toast.LENGTH_SHORT).show();
                        JSONArray resultsArray = null;
                        try {
                            Gson gson = new Gson();
                            mMovies.clear();
                            resultsArray = response.getJSONArray("results");
                            for (int i = 0; i < resultsArray.length(); i++) {
                                Movie movie = gson.fromJson(String.valueOf(resultsArray.getJSONObject(i)), Movie.class);
                                mMovies.add(movie);
                            }
                            mAdapter.notifyDataSetChanged();
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
        mQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        String menuSortTitle = String.format(getString(R.string.menu_order_by), getString(R.string.most_popular_order));
        menu.findItem(R.id.menu_sort_by_option).setTitle(menuSortTitle);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_sort_by_option){
            FragmentManager manager = getSupportFragmentManager();
            MovieOrderFragment dialog = MovieOrderFragment.newInstance(mSortOrder);
            dialog.show(manager, MOVIE_ORDER_FRAGMENT);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(MOVIE_LIST_KEY, mMovies);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onItemClick(int itemClicked) {
        Movie selectedMovie = mMovies.get(itemClicked);
        Intent intent = MovieDetailActivity.newInstance(this,selectedMovie);
        startActivity(intent);
    }

    @Override
    public void onSelectedOrderOption(String sortOrder) {
        Toast.makeText(this, sortOrder, Toast.LENGTH_SHORT).show();
        mSortOrder = sortOrder;
        updateMenuSortOrder();
        getMoviesFromApi(mSortOrder);
    }

    private void updateMenuSortOrder(){
        MenuItem sortOrder = mMenu.findItem(R.id.menu_sort_by_option);
        String menuTitleOrder = getString(R.string.most_popular_order);
        if(mSortOrder.equals(getString(R.string.rated))){
            menuTitleOrder = getString(R.string.top_rated_order);
        }
        sortOrder.setTitle(String.format(getString(R.string.menu_order_by), menuTitleOrder));
    }


}

