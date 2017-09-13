package com.example.android.popularmovies2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.asynctask.AsyncTaskReviewInterface;
import com.example.android.popularmovies2.asynctask.AsyncTaskTrailerInterface;
import com.example.android.popularmovies2.asynctask.MovieReviewAsyncTask;
import com.example.android.popularmovies2.asynctask.MovieTrailerAsyncTask;
import com.example.android.popularmovies2.recyclerviewadapters.MovieReviewAdapter;
import com.example.android.popularmovies2.recyclerviewadapters.MovieTrailerAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler, AsyncTaskReviewInterface,
        AsyncTaskTrailerInterface {

    // MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();

    private ArrayList<MovieTrailer> simpleJsonMovieTrailerData = new ArrayList<>();

    private Context context;

    private RecyclerView mRecyclerViewReview;

    private RecyclerView mRecyclerViewTrailer;

    Movie movie;

    public MovieReview review;

    private MovieReviewAdapter movieReviewAdapter;

    private MovieTrailerAdapter movieTrailerAdapter;

    public String movieId;

    public static final String VIDEO_ID = "GUzMYvHJLz8";

    RecyclerView.LayoutManager mReviewLayoutManager;

    RecyclerView.LayoutManager mTrailerLayoutManager;

    ImageView poster;

    TextView movieReview;

    TextView reviewAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerview_review);
        mRecyclerViewTrailer = (RecyclerView) findViewById(R.id.recyclerview_trailer);
        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, context);
        movieTrailerAdapter = new MovieTrailerAdapter(this, simpleJsonMovieTrailerData, context);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);
        mRecyclerViewTrailer.setAdapter(movieTrailerAdapter);

        RecyclerView.LayoutManager mReviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReview.setLayoutManager(mReviewLayoutManager);

        RecyclerView.LayoutManager mTrailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTrailer.setLayoutManager(mTrailerLayoutManager);

        poster = (ImageView) findViewById(R.id.imageView);

        movieReview = (TextView) findViewById(R.id.movie_review);
        reviewAuthor = (TextView) findViewById(R.id.author_review);


        if (getIntent() != null && getIntent().getExtras() != null) {
            movie = getIntent().getExtras().getParcelable("Movie");
            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .into(poster);

            movieId = movie.getMovieId();

            MovieReviewAsyncTask myReviewTask = new MovieReviewAsyncTask(this);
            myReviewTask.execute(movieId);

            MovieTrailerAsyncTask myTrailerTask = new MovieTrailerAsyncTask(this);
            myTrailerTask.execute(movieId);

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

    }

    public void returnTrailerData(ArrayList<MovieTrailer> simpleJsonMovieTrailerData) {
        movieTrailerAdapter = new MovieTrailerAdapter(this, simpleJsonMovieTrailerData, DetailActivity.this);
        mRecyclerViewTrailer.setAdapter(movieTrailerAdapter);

    }

    @Override
    public void onClick(MovieTrailer movieTrailer) {

    }
}



