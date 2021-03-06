package annin.my.android.popularmovies2.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

//import static annin.my.android.popularmovies2.data.MovieRepository.isFavorite;

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

//    public LiveData<Boolean> insert(Movie movieEntry)
////    {
////        mRepository.insert(movieEntry);
////        return MovieRepository.isInsertOk;
////
////    }
////
////    public LiveData<Boolean> delete(Movie movieEntry)
////    {
////        mRepository.delete(movieEntry);
////        return MovieRepository.isDeleteOk;
////   }

    public void insert(Movie movieEntry) {
        mRepository.insert(movieEntry);

    }

    public void delete(Movie movieEntry) {
        mRepository.delete(movieEntry);
    }


    public void deleteAllMovies()
    {
        mRepository.deleteAllMovies();
    }

    public void select(String movieId) {
        mRepository.select(movieId);
    }


    public LiveData<Boolean> isFavorite()
    {
        return mRepository.isFavorite();
    }

//    public LiveData<Movie> select(String movieId)
//    {
//        return mRepository.select(movieId);
//    }

    //Method used when testing running the database on the main thread
//    public boolean select(String movieId)
//    {
//        return mRepository.select(movieId);
//    }
}
