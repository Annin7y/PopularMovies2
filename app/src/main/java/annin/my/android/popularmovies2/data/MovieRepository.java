package annin.my.android.popularmovies2.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieRepository
{
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;
    public static MutableLiveData<Boolean> isInsertOk = new MutableLiveData<>();

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

    public void delete(Movie movieEntry)
    {
        new deleteAsyncTask(mMovieDao).execute(movieEntry);
    }

    public boolean select(String id)
    {
        Movie movie = mMovieDao.getSelectedMovie(id);

        if(movie == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public static void setInsertOk(boolean value)
    {
        isInsertOk.setValue(value);
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
    }
        private static class deleteAsyncTask extends AsyncTask<Movie, Void, Long>
        {
            private MovieDao mAsyncTaskDao;

            deleteAsyncTask(MovieDao dao)
            {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Long doInBackground(final Movie... params)
            {
                return mAsyncTaskDao.deleteMovie(params[0]);

            }

        @Override
        protected void onPostExecute(Long id)
    {
          if(id != -1)
          {
              MovieRepository.this.setInsertOk(true);
          }
          else
          {
              MovieRepository.this.setInsertOk(false);
          }

    }

    }}}



