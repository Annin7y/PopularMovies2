package annin.my.android.popularmovies2.asynctask;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import annin.my.android.popularmovies2.custom.Movie;
import annin.my.android.popularmovies2.utils.NetworkUtils;

/**
 * Created by Maino96-10022 on 8/19/2017.
 */

public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private static final String TAG = MovieAsyncTask.class.getSimpleName();
    private AsyncTaskInterface listener;

    public MovieAsyncTask(AsyncTaskInterface listener) {
        this.listener = listener;
    }

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

            return NetworkUtils.extractFeatureFromJson(jsonMovieResponse);

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



