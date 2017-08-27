package com.example.android.popularmovies2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.asynctask.AsyncTaskReviewInterface;
import com.example.android.popularmovies2.asynctask.MovieReviewAsyncTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements AsyncTaskReviewInterface {

    // implements AsyncTaskReviewInterface {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();

    private Context context;

    private RecyclerView mRecyclerViewReview;

    public Movie movie;

    private MovieReview review;

    private MovieReviewAdapter movieReviewAdapter;

    public String movieId;

    RecyclerView.LayoutManager mReviewLayoutManager;

    ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerview_detail);
        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, context);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);

        RecyclerView.LayoutManager mReviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewReview.setLayoutManager(mReviewLayoutManager);

        MovieReviewAsyncTask myReviewTask = new MovieReviewAsyncTask(this);

        myReviewTask.execute("id");

        movieId = movie.getMovieId();
        poster = (ImageView) findViewById(R.id.imageView);


        if (getIntent() != null && getIntent().getExtras() != null) {
            movie = getIntent().getExtras().getParcelable("Movie");
            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .into(poster);

           // movie.getMovieId();
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

    public void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData) {
        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, DetailActivity.this);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);

        TextView movieReview = (TextView) findViewById(R.id.movie_review);
        movieReview.setText(review.getMovieReview());
        TextView reviewAuthor = (TextView) findViewById(R.id.author_review);
        reviewAuthor.setText(review.getReviewAuthor());
    }
}





