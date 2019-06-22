package annin.my.android.popularmovies2.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieRepository
{


    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

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

    public void insert(Movie movie)
    {
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void>
    {
        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params)
        {
            mAsyncTaskDao.insert(params[0]);
            return null;

        }
    }

}
