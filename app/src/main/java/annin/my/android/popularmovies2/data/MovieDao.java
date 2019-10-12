package annin.my.android.popularmovies2.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

@Dao
    public interface MovieDao
    {
        @Query("SELECT * FROM movies ORDER BY original_title")
        LiveData<List<Movie>> loadAllMovies();

        @Query("SELECT * FROM movies WHERE movieId =:id")
        LiveData<Movie> getSelectedMovie(String id);
        //Method used when testing running the database on the main thread
        //Movie getSelectedMovie(String id);

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        long insertMovie(Movie movieEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovie(Movie movieEntry);

        @Delete
        int deleteMovie(Movie movieEntry);

        @Query(" DELETE FROM movies")
         void deleteAllMovies();

    }





