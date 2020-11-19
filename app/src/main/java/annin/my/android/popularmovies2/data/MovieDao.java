package annin.my.android.popularmovies2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import annin.my.android.popularmovies2.pojo.Movie;

@Dao
    public interface MovieDao
    {
        @Query("SELECT * FROM movies ORDER BY original_title")
        LiveData<List<Movie>> loadAllMovies();

        @Query("SELECT * FROM movies WHERE movieId =:id")
                //Method used when setting isFavorite in the DetailActivity
        //LiveData<Movie> getSelectedMovie(String id);
        //Method used when testing running the database on the main thread &
        //setting isFavorite in the Repository
         Movie getSelectedMovie(String id);

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        long insertMovie(Movie movieEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateMovie(Movie movieEntry);

        @Delete
        int deleteMovie(Movie movieEntry);

        @Query(" DELETE FROM movies")
         void deleteAllMovies();

    }





