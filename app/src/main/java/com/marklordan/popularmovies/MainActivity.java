package com.marklordan.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marklordan.popularmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String[] moviePosterPaths = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        final MovieAdapter mAdapter = new MovieAdapter(moviePosterPaths, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = NetworkUtils.buildUrl("popular");

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                 new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(MainActivity.this, "got a response", Toast.LENGTH_SHORT).show();
                        JSONArray resultsArray = null;
                        try {
                            resultsArray = response.getJSONArray("results");
                            for (int i = 0; i < resultsArray.length(); i++) {
                                moviePosterPaths[i] = resultsArray.getJSONObject(i).getString("poster_path");
                            }
                            Log.i("MainActivity", moviePosterPaths[10]);
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
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
