package com.example.android.popularmovies2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.asynctask.AsyncTaskReviewInterface;
import com.example.android.popularmovies2.asynctask.MovieReviewAsyncTask;

import java.util.ArrayList;

public class MovieReviewActivity extends AppCompatActivity implements AsyncTaskReviewInterface {

        private static final String TAG = MovieReviewActivity.class.getSimpleName();

        private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();

        private Context context;

        private MovieReview review;

        public Movie movie;

        private MovieReviewAdapter movieReviewAdapter;

        private RecyclerView mRecyclerViewReview;
        public String movieId;

        ImageView poster;

        @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_review);

            poster = (ImageView) findViewById(R.id.imageView);


            context = getApplicationContext();
            mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerview_movie_review);
            movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, context);
            mRecyclerViewReview.setAdapter(movieReviewAdapter);

            movie.getMovieId();

            MovieReviewAsyncTask myReviewTask = new MovieReviewAsyncTask(this);
            myReviewTask.execute("reviews");


            if (getIntent() != null && getIntent().getExtras() != null) {
                review = getIntent().getExtras().getParcelable("MovieReview");

                TextView movieReview = (TextView) findViewById(R.id.movie_review);
                movieReview.setText(review.getMovieReview());
                TextView reviewAuthor = (TextView) findViewById(R.id.author_review);
                reviewAuthor.setText(review.getReviewAuthor());

            }
        }
        public void returnReviewData (ArrayList < MovieReview > simpleJsonMovieReviewData) {
        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, MovieReviewActivity.this);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);
    }

    }

