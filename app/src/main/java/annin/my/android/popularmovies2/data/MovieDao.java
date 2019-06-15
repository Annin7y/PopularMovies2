package annin.my.android.popularmovies2.data;

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
        @Query("SELECT * FROM movies ORDER BY originalTitle")
        List<Movie> loadAllMovies();

        @Insert
        void insertMovie(Movie movieEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovie(Movie movieEntry);

        @Delete
        void deleteMovie(Movie movieEntry);
    }





