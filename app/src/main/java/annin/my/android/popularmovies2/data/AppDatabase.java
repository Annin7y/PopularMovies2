package annin.my.android.popularmovies2.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import annin.my.android.popularmovies2.pojo.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase
{
    public abstract MovieDao movieDao();
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movieFavorites";

    private static AppDatabase sInstance;

    public static AppDatabase getDatabase(Context context)
    {
        if (sInstance == null)
        {
            synchronized (LOCK)
            {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                      // COMPLETED (2) call allowMainThreadQueries before building the instance
                     // Queries should be done in a separate thread to avoid locking the UI
                        // We will allow this ONLY TEMPORALLY to see that our DB is working
                        //.allowMainThreadQueries()

                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }
}
