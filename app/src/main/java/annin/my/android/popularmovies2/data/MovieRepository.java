package annin.my.android.popularmovies2.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieRepository
{
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;
    private boolean mutableLiveData;

    MovieRepository(Application application)
    {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.loadAllMovies();
    }

    LiveData<List<Movie>> loadAllMovies()
    {
        return mAllMovies;
    }

    public void insert(Movie movieEntry)
    {
        new insertAsyncTask(mMovieDao).execute(movieEntry);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Long>
    {
        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Movie... params)
        {
           return mAsyncTaskDao.insertMovie(params[0]);

        }

        @Override
        protected void onPostExecute(Long id)
    {

    }

    }


}
