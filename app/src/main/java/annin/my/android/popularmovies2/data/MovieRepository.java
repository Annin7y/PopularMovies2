package annin.my.android.popularmovies2.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieRepository
{
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

}
