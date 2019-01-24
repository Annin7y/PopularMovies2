package annin.my.android.popularmovies2.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import annin.my.android.popularmovies2.pojo.MovieReview;
import annin.my.android.popularmovies2.utils.JSONUtils;
import annin.my.android.popularmovies2.utils.NetworkUtils;

/**
 * Created by Maino96-10022 on 8/19/2017.
 */

public class MovieReviewAsyncTask extends AsyncTask<String, Void, ArrayList<MovieReview>>
{
    private static final String TAG = MovieReviewAsyncTask.class.getSimpleName();
    private AsyncTaskReviewInterface listener;

    public MovieReviewAsyncTask(AsyncTaskReviewInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<MovieReview> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }
        String movieId = params[0];
        URL reviewRequestUrl = NetworkUtils.buildUrlReview(movieId);

        try
        {
            String jsonMovieReviewResponse = NetworkUtils
                    .makeHttpReviewRequest(reviewRequestUrl);

            return JSONUtils.extractFeatureFromReviewJson(jsonMovieReviewResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MovieReview> mMovieReviewList)
    {
        super.onPostExecute(mMovieReviewList);
        if (mMovieReviewList != null)
        {
            listener.returnReviewData(mMovieReviewList);
        }
    }
}
