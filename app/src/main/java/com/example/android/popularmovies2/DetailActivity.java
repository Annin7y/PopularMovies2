package com.example.android.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.asynctask.AsyncTaskInterface;
import com.example.android.popularmovies2.asynctask.MovieAsyncTask;
import com.example.android.popularmovies2.asynctask.MovieReviewAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();

    private Context context;

    private MovieReviewAdapter movieReviewAdapter;

    private RecyclerView mRecyclerView;

    ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

     //   context = getApplicationContext();
      //  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
  //      movieReviewAdapter = new MovieReviewAdapter(this, simpleJsonMovieReviewData, context);
     //   mRecyclerView.setAdapter(movieReviewAdapter);

        poster = (ImageView) findViewById(R.id.imageView);


     //   MovieReviewAsyncTask myReviewTask = new MovieReviewAsyncTask(this);
     //   myReviewTask.execute("author");
     //   returnReviewData(simpleJsonMovieReviewData);


        Movie movie;
        if (getIntent() != null && getIntent().getExtras() != null) {
            movie = getIntent().getExtras().getParcelable("Movie");
            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .into(poster);

            TextView originalTitle = (TextView) findViewById(R.id.original_title);
            originalTitle.setText(movie.getOriginalTitle());

            TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
            movieOverview.setText(movie.getMovieOverview());

            TextView voteAverage = (TextView) findViewById(R.id.vote_average);
            voteAverage.setText(movie.getVoteAverage());

            TextView releaseDate = (TextView) findViewById(R.id.release_date);
            releaseDate.setText(movie.getReleaseDate());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;

            try {
                date = simpleDateFormat.parse(movie.getReleaseDate());
                date.toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String finalDate = newDateFormat.format(date);

            releaseDate.setText(finalDate);
        }
    }

 //   @Override
  //  public void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData) {
   //     movieReviewAdapter = new MovieReviewAdapter(this, simpleJsonMovieReviewData, DetailActivity.this);
    //    mRecyclerView.setAdapter(movieReviewAdapter);


  //  }

   // @Override
 //   public void onClick(MovieReview review) {
   //     MovieAsyncTask myTask = new MovieAsyncTask(this);
    //    returnReviewData(simpleJsonMovieReviewData);


 //   }
}



