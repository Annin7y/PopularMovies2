package annin.my.android.popularmovies2.data;

/**
 * Created by Maino96-10022 on 9/6/2017.
 */

public class MovieContentProvider //extends ContentProvider
{
//    //Tag for the log messages
//    public static final String LOG_TAG = MovieContentProvider.class.getSimpleName();
//
//    /**
//     * URI matcher code for the content URI for the movies table
//     */
//    public static final int MOVIES = 100;
//
//    /**
//     * URI matcher code for the content URI for one movie in the movie table
//     */
//    public static final int MOVIE_WITH_ID = 101;
//
//    /**
//     * UriMatcher object to match a content URI to a corresponding code.
//     * The input passed into the constructor represents the code to return for the root URI.
//     * It's common to use NO_MATCH as the input for this case.
//     */
//    private static final UriMatcher sUriMatcher = buildUriMatcher();
//
//    // Static initializer. This is run the first time anything is called from this class.
//    public static UriMatcher buildUriMatcher()
//    {
//        // The calls to addURI() go here, for all of the content URI patterns that the provider
//        // should recognize. All paths added to the UriMatcher have a corresponding code to return
//        // when a match is found.
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
//        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);
//
//        return uriMatcher;
//    }
//
//    /**
//     * Database helper object
//     */
//    private MovieDbHelper mMovieDbHelper;
//
//    @Override
//    public boolean onCreate()
//    {
//        // Complete onCreate() and initialize a MovieDbhelper on startup
//        // [Hint] Declare the DbHelper as a global variable
//        Context context = getContext();
//        mMovieDbHelper = new MovieDbHelper(context);
//        return true;
//    }
//
//    // Implement insert to handle requests to insert a single new row of data
//    @Override
//    public Uri insert(@NonNull Uri uri, ContentValues values)
//    {
//        // Get access to the movie database (to write new data to)
//        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
//
//        // Write URI matching code to identify the match for the movies directory
//        int match = sUriMatcher.match(uri);
//        Uri returnUri; // URI to be returned
//
//        switch (match)
//        {
//            case MOVIES:
//                // Insert new values into the database
//                // Inserting values into movies table
//                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
//                if (id > 0)
//                {
//                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
//                }
//                else
//                    {
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            // Set the value for the returnedUri and write the default case for unknown URI's
//            // Default case throws an UnsupportedOperationException
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        // Notify the resolver if the uri has been changed, and return the newly inserted URI
//        getContext().getContentResolver().notifyChange(uri, null);
//
//        // Return constructed uri (this points to the newly inserted row of data)
//        return returnUri;
//    }
//
//    // Implement query to handle requests for data by URI
//    @Override
//    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder)
//    {
//
//        // Get access to underlying database (read-only for query)
//        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();
//
//        // Write URI match code and set a variable to return a Cursor
//        int match = sUriMatcher.match(uri);
//        Cursor retCursor;
//
//        // Query for the movies directory and write a default case
//        switch (match)
//        {
//            // Query for the movies directory
//            case MOVIES:
//                retCursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                break;
//            // Default exception
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        // Set a notification URI on the Cursor and return that Cursor
//        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
//
//        // Return the desired Cursor
//        return retCursor;
//    }
//
//    /**
//     * Update inventory in the database with the given content values. Apply the changes to the rows
//     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
//     * Return the number of rows that were successfully updated.
//     */
//    @Override
//    public int update(@NonNull Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs)
//    {
//        //Keep track of if an update occurs
//        int rowsUpdated;
//        final int match = sUriMatcher.match(uri);
//        switch (match) {
//            case MOVIE_WITH_ID:
//                String id = uri.getPathSegments().get(1);
//                rowsUpdated = mMovieDbHelper.getWritableDatabase().update(MovieContract.MovieEntry.TABLE_NAME, values, "_id=?", new String[]{id});
//                break;
//            default:
//                throw new UnsupportedOperationException("Not yet implemented");
//        }
//        if (rowsUpdated != 0)
//        {
//            //set notifications if a row was updated
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//
//        // return number of rows updated
//        return rowsUpdated;
//    }
//
//    // Implement delete to delete a single row of data
//    @Override
//    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
//    {
//
//        // Get access to the database and write URI matching code to recognize a single item
//        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
//
//        int match = sUriMatcher.match(uri);
//        // Keep track of the number of deleted rows
//        int rowsDeleted; // starts as 0
//
//        //if (null == selection) selection = "1";
//        // Write the code to delete a single row of data
//        // [Hint] Use selections to delete an item by its row ID
//        switch (match)
//        {
//            // Handle the single item case, recognized by the ID included in the URI path
//            case MOVIE_WITH_ID:
//                // Get the movie ID from the URI path
//                String id = uri.getPathSegments().get(1);
//                // Use selections/selectionArgs to filter for this ID
//                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, "id=?", new String[]{id});
//
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        // Notify the resolver of a change and return the number of items deleted
//        if (rowsDeleted != 0)
//        {
//            // A movie was deleted, set notification
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
//
//        // Return the number of rows deleted
//        return rowsDeleted;
//    }
//
//    @Override
//    public String getType(@NonNull Uri uri)
//    {
//        final int match = sUriMatcher.match(uri);
//        switch (match) {
//            case MOVIES:
//                return MovieContract.MovieEntry.CONTENT_LIST_TYPE;
//            case MOVIE_WITH_ID:
//                return "vnd.android.cursor.item" + "/" + MovieContract.CONTENT_AUTHORITY + "/" + MovieContract.PATH_MOVIES;
//            default:
//                throw new UnsupportedOperationException("Not yet implemented");
//        }
//    }
}