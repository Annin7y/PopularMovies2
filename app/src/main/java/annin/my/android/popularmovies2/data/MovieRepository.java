package annin.my.android.popularmovies2.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieRepository
{
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;
   // public static MutableLiveData<Boolean> isInsertOk = new MutableLiveData<>();
   // public static MutableLiveData<Boolean> isDeleteOk = new MutableLiveData<>();
    public static MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();


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

    //Insert and delete methods commented out: combined into a single isFavorite variable
    //will be checked in the Repository instead of the DetailActivity
    public void insert(Movie movieEntry)
    {
        new insertAsyncTask(mMovieDao).execute(movieEntry);
    }

    public void delete(Movie movieEntry)
    {
        new deleteAsyncTask(mMovieDao).execute(movieEntry);
    }

    public void deleteAllMovies()
    {
        new deleteAllMoviesAsyncTask(mMovieDao).execute();
    }


    //Method used when testing running the database on the main thread
//    public boolean select(String id)
//    {
//        Movie movie = mMovieDao.getSelectedMovie(id);
//
//        return movie != null;
//    }

    public LiveData<Movie> select(String id)
    {
        return mMovieDao.getSelectedMovie(id);
    }

    public void setFavorite(boolean value) {
        isFavorite.setValue(value);
    }

    public MutableLiveData<Boolean> isFavorite() {
        return isFavorite;
    }


//    public static void setInsertOk(boolean value)
//    {
//        isInsertOk.setValue(value);
//    }

    private class insertAsyncTask extends AsyncTask<Movie, Void, Long>
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
            if(id != -1)
            {
              //  MovieRepository.this.setInsertOk(true);
                // since the query was OK, we set isFavorite to true
                    setFavorite(true);
                }

            else
            {
              //  MovieRepository.this.setInsertOk(false);
                setFavorite(false);
            }

        }

    }

//    public static void setDeleteOk(boolean value)
//    {
//        isDeleteOk.setValue(value);
//    }

        private class deleteAsyncTask extends AsyncTask<Movie, Void, Integer>
        {
            private MovieDao mAsyncTaskDao;

            deleteAsyncTask(MovieDao dao)
            {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Integer doInBackground(final Movie... params)
            {
                return mAsyncTaskDao.deleteMovie(params[0]);

            }

            @Override
            protected void onPostExecute(Integer  rowsDeleted)
            {
                if(rowsDeleted > 0)
                {
                    setFavorite(false);
                  //  MovieRepository.this.setDeleteOk(true);
                }
            else
                {
                   // MovieRepository.this.setDeleteOk(false);
                    setFavorite(true);
                }
            }
            }
    public static class deleteAllMoviesAsyncTask extends AsyncTask<Movie, Void, Void>
    {
        private final MovieDao mAsyncTaskDao;

        deleteAllMoviesAsyncTask(MovieDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... voids)
        {
            mAsyncTaskDao.deleteAllMovies();
            return null;
        }
    }
    }



