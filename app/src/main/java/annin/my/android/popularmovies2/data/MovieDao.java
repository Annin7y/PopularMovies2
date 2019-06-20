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
        List<MovieEntry> loadAllMovies();

        @Insert
        void insertMovie(MovieEntry movieEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovie(MovieEntry movieEntry);

        @Delete
        void deleteMovie(MovieEntry movieEntry);
    }





