package annin.my.android.popularmovies2.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.asynctask.AsyncTaskInterface;
import annin.my.android.popularmovies2.asynctask.MovieAsyncTask;
import annin.my.android.popularmovies2.custom.Movie;
import annin.my.android.popularmovies2.data.MovieContract;
import annin.my.android.popularmovies2.decoration.DividerItemDecoration;
import annin.my.android.popularmovies2.decoration.VerticalSpacingDecoration;
import annin.my.android.popularmovies2.recyclerviewadapters.FavoritesAdapter;
import annin.my.android.popularmovies2.recyclerviewadapters.MovieAdapter;
import annin.my.android.popularmovies2.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, AsyncTaskInterface, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int FAVORITES_LOADER_ID = 0;

    private ArrayList<Movie> moviesArrayList = new ArrayList<>();

    private Context context;

    private RecyclerView mRecyclerView;

    private MovieAdapter movieAdapter;

    private FavoritesAdapter favoritesAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    private TextView mErrorMessageDisplay;

    private TextView mConnectionMessage;

    private Button retryButton;

    private ProgressBar mLoadingIndicator;

    private static final String KEY_MOVIES_LIST = "movies_list";

    private static final String KEY_SORT_ORDER = "sort_order";

    private String selectedSortOrder = "most_popular";

    private static final String SORT_BY_FAVORITES = "movie_favorites";

    private int mPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        mRecyclerView = findViewById(R.id.recyclerview_main);
        movieAdapter = new MovieAdapter(this, moviesArrayList, context);
        mRecyclerView.setAdapter(movieAdapter);

        favoritesAdapter = new FavoritesAdapter(this, context);

        //specifying how the images will be displayed
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, calculateNoOfColumns(context));
        mRecyclerView.setLayoutManager(mLayoutManager);

        mConnectionMessage = findViewById(R.id.no_connection);
        mErrorMessageDisplay = findViewById(R.id.movie_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        retryButton = findViewById(R.id.button);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof MovieAdapter.MovieAdapterViewHolder) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                //Construct the URI for the item to delete
                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
                // Retrieve the id of the task to delete
                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                // TODO (2) Delete a single row of data using a ContentResolver
                int rowsDeleted = getContentResolver().delete(uri, null, null);
                Log.v("CatalogActivity", rowsDeleted + " rows deleted from the movie database");
                // TODO (3) Restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         *  Starting the asyncTask so that movies load upon launching the app. most popular are loaded first.
         */
        if (savedInstanceState == null) {
            if (isNetworkStatusAvailable(this)) {
                MovieAsyncTask myTask = new MovieAsyncTask(this);
                myTask.execute(NetworkUtils.SORT_BY_POPULAR);
            } else {
                Snackbar
                        .make(view, "No Internet connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new MyClickListener())
                        .show();
                    }
        } else {
            selectedSortOrder = savedInstanceState.getString(KEY_SORT_ORDER, "most_popular");

            if (selectedSortOrder == SORT_BY_FAVORITES) {
                getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
                mRecyclerView.setAdapter(favoritesAdapter);
            } else {
                moviesArrayList = savedInstanceState.getParcelableArrayList(KEY_MOVIES_LIST);
                movieAdapter.setMovieList(moviesArrayList);
            }
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            Log.v(TAG, "SORT ORDER= ." + selectedSortOrder);
            Log.i("list", moviesArrayList.size() + "");
        }

        //specifying the space between images
        mRecyclerView.addItemDecoration(new VerticalSpacingDecoration(64));

        //the vertical divider
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.item_decorator)));
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
    public class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Run the AsyncTask in response to the click
            MovieAsyncTask myTask = new MovieAsyncTask(MainActivity.this);
            myTask.execute(selectedSortOrder);
        }

    }
            @Override
    public void returnData(ArrayList<Movie> simpleJsonMovieData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (null != simpleJsonMovieData) {
            movieAdapter = new MovieAdapter(this, simpleJsonMovieData, MainActivity.this);
            moviesArrayList = simpleJsonMovieData;
            mRecyclerView.setAdapter(movieAdapter);
            movieAdapter.setMovieList(moviesArrayList);
        } else {
            //  showErrorMessage();
            Snackbar
                    .make(view, "No Internet connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new MyClickListener())
                    .show();
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mFavoritesData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mFavoritesData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoritesData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry.COLUMN_MOVIES_ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mFavoritesData = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favoritesAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoritesAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieAsyncTask myTask = new MovieAsyncTask(this);
        switch (item.getItemId()) {
            case R.id.most_popular:
                myTask.execute(NetworkUtils.SORT_BY_POPULAR);
                selectedSortOrder = NetworkUtils.SORT_BY_POPULAR;
                return true;

            case R.id.top_rated:
                myTask.execute(NetworkUtils.SORT_BY_RATING);
                selectedSortOrder = NetworkUtils.SORT_BY_RATING;
                return true;

            case R.id.movie_favorites:
                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
                favoritesAdapter = new FavoritesAdapter(this, MainActivity.this);
                mRecyclerView.setAdapter(favoritesAdapter);
                selectedSortOrder = SORT_BY_FAVORITES;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
    // Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();


//Display if there is no internet connection
//    public void showErrorMessage() {
//            Toast.makeText(getApplicationContext(), "No internet connection",
//                    Toast.LENGTH_SHORT).show();
//            mRecyclerView.setVisibility(View.INVISIBLE);
//            mConnectionMessage.setVisibility(View.VISIBLE);
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//
//    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_SORT_ORDER, selectedSortOrder);
        outState.putParcelableArrayList(KEY_MOVIES_LIST, moviesArrayList);
        super.onSaveInstanceState(outState);
    }
}