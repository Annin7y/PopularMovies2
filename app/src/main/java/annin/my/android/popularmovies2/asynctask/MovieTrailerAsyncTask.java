package annin.my.android.popularmovies2.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import annin.my.android.popularmovies2.model.MovieTrailer;
import annin.my.android.popularmovies2.utils.JSONUtils;
import annin.my.android.popularmovies2.utils.NetworkUtils;

/**
 * Created by Maino96-10022 on 9/3/2017.
 */

public class MovieTrailerAsyncTask extends AsyncTask<String, Void, ArrayList<MovieTrailer>>
{
    private static final String TAG = MovieTrailerAsyncTask.class.getSimpleName();
    private AsyncTaskTrailerInterface listener;

    public MovieTrailerAsyncTask(AsyncTaskTrailerInterface listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<MovieTrailer> doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }

        String movieId = params[0];
        URL trailerRequestUrl = NetworkUtils.buildUrlTrailer(movieId);

        try
        {
            String jsonMovieTrailerResponse = NetworkUtils
                    .makeHttpTrailerRequest(trailerRequestUrl);

            return JSONUtils.extractFeatureFromTrailerJson(jsonMovieTrailerResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MovieTrailer> mMovieTrailerList)
    {
        super.onPostExecute(mMovieTrailerList);
        if (mMovieTrailerList != null)
        {
            listener.returnTrailerData(mMovieTrailerList);
        }
    }
}
