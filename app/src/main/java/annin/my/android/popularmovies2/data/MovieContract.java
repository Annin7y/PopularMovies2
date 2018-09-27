package annin.my.android.popularmovies2.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Maino96-10022 on 9/6/2017.
 */

public class MovieContract
{

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MovieContract()
    {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "annin.my.android.popularmovies2";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public final static String _ID = BaseColumns._ID;

        public static final String TABLE_NAME = "movies";

        public final static String COLUMN_MOVIES_ID = "id";

        public final static String COLUMN_MOVIES_TITLE = "original_title";

        public final static String COLUMN_MOVIES_OVERVIEW = "overview";

        public final static String COLUMN_MOVIES_VOTE = "vote_average";

        public final static String COLUMN_MOVIES_DATE = "release_date";

        public final static String COLUMN_MOVIES_POSTER_PATH = "posterPath";

    }
}
