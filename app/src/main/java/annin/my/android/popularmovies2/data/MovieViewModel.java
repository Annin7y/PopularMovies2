package annin.my.android.popularmovies2.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

public class MovieViewModel extends AndroidViewModel
{
    private MovieRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;

    public MovieViewModel(Application application)
    {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.loadAllMovies();
    }

    public LiveData<List<Movie>> loadAllMovies() {
        return mAllMovies;
    }

    public LiveData<Boolean> insert(Movie movieEntry)
    {
        mRepository.insert(movieEntry);
        return MovieRepository.isInsertOk;

    }

    public LiveData<Boolean> delete(Movie movieEntry)
    {
        mRepository.delete(movieEntry);
        return MovieRepository.isDeleteOk;
    }

    public void deleteAllMovies()
    {
        mRepository.deleteAllMovies();
    }

    public boolean select(String movieId) {
        return mRepository.select(movieId);
    }

}
