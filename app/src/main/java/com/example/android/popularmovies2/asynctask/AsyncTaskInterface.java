package com.example.android.popularmovies2.asynctask;

import com.example.android.popularmovies2.Movie;
import com.example.android.popularmovies2.MovieReview;

import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/16/2017.
 */

public interface AsyncTaskInterface {
    void returnData(ArrayList<Movie> simpleJsonMovieData);
    void returnReviewData(ArrayList<MovieReview> simpleJsonMovieData);

}
