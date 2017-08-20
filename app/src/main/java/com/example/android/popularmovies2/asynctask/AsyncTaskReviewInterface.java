package com.example.android.popularmovies2.asynctask;

import com.example.android.popularmovies2.MovieReview;

import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/20/2017.
 */

public interface AsyncTaskReviewInterface {
    void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData);
}

