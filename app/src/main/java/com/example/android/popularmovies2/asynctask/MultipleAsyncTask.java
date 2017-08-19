package com.example.android.popularmovies2.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.android.popularmovies2.Movie;
import com.example.android.popularmovies2.MovieReview;
import com.example.android.popularmovies2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/18/2017.
 */

public class MultipleAsyncTask extends Activity
{
    private AsyncTaskInterface listener;

    public MultipleAsyncTask(AsyncTaskInterface listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        runMultipleAsyncTask(); // Start Async Task
    }
    private void runMultipleAsyncTask() // Run Multiple Async Task
    {
        FirstMovieAsyncTask asyncTask = new FirstMovieAsyncTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else // Below Api Level 13
        {
            asyncTask.execute();
        }
        SecondMovieAsyncTask asyncTask2 = new SecondMovieAsyncTask();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

        {
            asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
        {
            asyncTask2.execute();
        }
    }
    private class FirstMovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String sortMode = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortMode);

            try {
                String jsonMovieResponse = NetworkUtils
                        .makeHttpRequest(movieRequestUrl);

                ArrayList simpleJsonMovieData = NetworkUtils
                        .extractFeatureFromJson(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> mMovieList) {
            super.onPostExecute(mMovieList);
            if (mMovieList != null) {
                listener.returnData(mMovieList);
            }
        }

    }
    private class SecondMovieAsyncTask extends AsyncTask<String, Void, ArrayList<MovieReview>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MovieReview> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String sortMode = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortMode);

            try {
                String jsonMovieResponse = NetworkUtils
                        .makeHttpRequest(movieRequestUrl);

                ArrayList simpleJsonMovieReviewData = NetworkUtils
                        .extractFeatureFromJson(jsonMovieResponse);

                return simpleJsonMovieReviewData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieReview> mMovieReviewList) {
            super.onPostExecute(mMovieReviewList);
            if (mMovieReviewList != null) {
                listener.returnReviewData(mMovieReviewList);
            }
        }

    }
}
