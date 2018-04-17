package com.themoviedb.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.themoviedb.app.R;
import com.themoviedb.app.http.Constants;
import com.themoviedb.app.model.Movie;

public class MovieDetails extends AppCompatActivity {

    Context context;
    ImageView imageView;
    TextView tvTitle;
    TextView tvRating;
    TextView tvOverview;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);

        movie=getIntent().getParcelableExtra("movie");
        context=this;
        imageView=(ImageView)findViewById(R.id.imageViewHeader);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvRating=(TextView)findViewById(R.id.tvRating);
        tvOverview=(TextView)findViewById(R.id.tvOverview);

        if(movie!=null){
        Picasso.with(context)
                .load(Constants.IMAGE_BASE_PATH_W300+ movie.getPosterPath()) //https://image.tmdb.org/t/p/w300///cGOPbv9wA5gEejkUN892JrveARt.jpg
                .into(imageView);

            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getRating());
            tvOverview.setText(movie.getOverview());

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
