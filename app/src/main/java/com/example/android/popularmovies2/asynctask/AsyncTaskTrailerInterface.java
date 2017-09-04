package com.example.android.popularmovies2.asynctask;

import com.example.android.popularmovies2.MovieTrailer;

import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 9/3/2017.
 */

public interface AsyncTaskTrailerInterface {

    void returnTrailerData(ArrayList<MovieTrailer> simpleJsonMovieTrailerData);
}

