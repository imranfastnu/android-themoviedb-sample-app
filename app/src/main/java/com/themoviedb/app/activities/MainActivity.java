package com.themoviedb.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.themoviedb.app.R;
import com.themoviedb.app.adapters.MovieAdapter;
import com.themoviedb.app.http.Constants;
import com.themoviedb.app.http.ResponseHandler;
import com.themoviedb.app.http.ServerConnectionManager;
import com.themoviedb.app.model.Movie;
import com.themoviedb.app.utils.IListItemClickListener;
import com.themoviedb.app.views.EndlessRecyclerViewScrollListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IListItemClickListener {

    Context context;

    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    RecyclerView recyclerView;

    TextView txvEmptyList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, FilterActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        txvEmptyList = (TextView) findViewById(R.id.txvEmptyList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(context, this, movieList);
        recyclerView.setAdapter(movieAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Add the scroll listener
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                loadMoreDataFromApi(page, "");

            }
        });


        this.loadMoreDataFromApi(1, "");
    }

    @Override
    public void onListItemClick(View view, int position) {

        Movie obj=movieList.get(position);
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", obj);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                String minYear = data.getStringExtra("minYear");
                String maxYear = data.getStringExtra("maxYear");

                String filter = "";
                if (minYear != null && !minYear.equals(""))
                    filter = Constants.MIN_YEAR_FILTER + minYear;

                if (maxYear != null && !maxYear.equals(""))
                    filter = filter + Constants.MAX_YEAR_FILTER + maxYear;

                movieList.clear();
                movieAdapter.notifyDataSetChanged();

                loadMoreDataFromApi(1, filter);
            }
        }
    }

    // Network request
    public void loadMoreDataFromApi(final int page, String filter) {

        //Show Progress Dialog
        if (page == 1)
            showProgressBar(true);

        new ServerConnectionManager(context).executeApiRequest(Constants.GET_MOVIES + filter + "&page=" + page, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Parse response
                List<Movie> movies = ResponseHandler.parseMovies(response);
                if (page == 1)
                    showProgressBar(false);

                //Set Movies Adapter
                if (movies != null && movies.size() > 0) {
                    int curSize = movieAdapter.getItemCount();
                    movieList.addAll(movies);
                    movieAdapter.notifyItemRangeInserted(curSize, movieList.size() - 1);
                } else {
                    showEmptyList(true);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Dismiss Progress Dialog

                showEmptyList(true);

            }
        });

    }


    private void showProgressBar(boolean value) {
        if (value)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void showEmptyList(boolean show) {
        if (show)
            txvEmptyList.setVisibility(View.VISIBLE);
        else
            txvEmptyList.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
