package com.example.android.popularmovies2.asynctask;

import android.os.AsyncTask;

import com.example.android.popularmovies2.MovieReview;
import com.example.android.popularmovies2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/19/2017.
 */

public class MovieReviewAsyncTask extends AsyncTask<String, Void, ArrayList<MovieReview>> {

    private static final String TAG = MovieAsyncTask.class.getSimpleName();
    private AsyncTaskInterface listener;

    public MovieReviewAsyncTask(AsyncTaskInterface listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<MovieReview> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String reviewQuery = params[0];
        URL movieRequestUrl = NetworkUtils.buildUrl(reviewQuery);

        try {
            String jsonMovieResponse = NetworkUtils
                    .makeHttpRequest(movieRequestUrl);

            ArrayList simpleJsonMovieReviewData = NetworkUtils
                    .extractFeatureFromJson(jsonMovieResponse);

            return simpleJsonMovieData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MovieReview> mMovieList) {
        super.onPostExecute(mMovieList);
        if (mMovieList != null) {
            listener.returnReviewData(mMovieList);
        }
    }
}
