package annin.my.android.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maino96-10022 on 9/6/2017.
 */

public class MovieDbHelper //extends SQLiteOpenHelper
{
//    // The name of the database
//    private static final String DATABASE_NAME = "tasksDb.db";
//
//    // If you change the database schema, you must increment the database version
//    private static final int VERSION = 10;
//
//    /**
//     * Constructs a new instance of {@link MovieDbHelper}.
//     *
//     * @param context of the app
//     */
//    MovieDbHelper(Context context)
//    {
//        super(context, DATABASE_NAME, null, VERSION);
//    }
//
//    /**
//     * Called when the movies database is created for the first time.
//     */
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//        // Create movies table (careful to follow SQL formatting rules)
//        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
//                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY , " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_ID + " TEXT NOT NULL , " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_TITLE + " TEXT NOT NULL, " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW + " TEXT NOT NULL, " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_VOTE + " TEXT NOT NULL, " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_DATE + " TEXT NOT NULL, " +
//                MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH + " TEXT NOT NULL, " +
//
//                " UNIQUE (" + MovieContract.MovieEntry.COLUMN_MOVIES_TITLE + ") ON CONFLICT REPLACE);";
//
//        db.execSQL(CREATE_TABLE);
//    }
//
//    /**
//     * This method discards the old table of data and calls onCreate to recreate a new one.
//     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
//     */
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//        db.execSQL("ALTER TABLE  " + MovieContract.MovieEntry.TABLE_NAME);
//        onCreate(db);
//    }
}


