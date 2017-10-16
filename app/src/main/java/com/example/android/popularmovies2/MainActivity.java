package com.example.android.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies2.asynctask.AsyncTaskInterface;
import com.example.android.popularmovies2.asynctask.MovieAsyncTask;
import com.example.android.popularmovies2.data.MovieContract;
import com.example.android.popularmovies2.decoration.DividerItemDecoration;
import com.example.android.popularmovies2.decoration.VerticalSpacingDecoration;
import com.example.android.popularmovies2.recyclerviewadapters.FavoritesAdapter;
import com.example.android.popularmovies2.recyclerviewadapters.MovieAdapter;
import com.example.android.popularmovies2.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, AsyncTaskInterface, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int FAVORITES_LOADER_ID = 0;

    private ArrayList<Movie> simpleJsonMovieData = new ArrayList<>();

    private ArrayList<Movie> moviesList;

    private Context context;

    private RecyclerView mRecyclerView;

    private MovieAdapter movieAdapter;

    private FavoritesAdapter favoritesAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    SharedPreferences sharedpreferences;

    private static final String KEY_SORT_ORDER = "sort_order";

    private String selectedSortOrder = "most_popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        movieAdapter = new MovieAdapter(this, simpleJsonMovieData, context);
        mRecyclerView.setAdapter(movieAdapter);

        favoritesAdapter = new FavoritesAdapter(this, context);

        //specifying that the images will be displayed in two columns
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mErrorMessageDisplay = (TextView) findViewById(R.id.movie_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

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

                // TODO (1) Construct the URI for the item to delete
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
        //     getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
        /**
         *  Starting the asyncTask so that movies load upon launching the app. most popular are loaded first.
         */
        if (savedInstanceState == null) {
            MovieAsyncTask myTask = new MovieAsyncTask(this);
            myTask.execute(NetworkUtils.SORT_BY_POPULAR);
        } else {
            selectedSortOrder = savedInstanceState.getString(KEY_SORT_ORDER, "most_popular");
//            MovieAsyncTask myTask = new MovieAsyncTask(this);
//            myTask.execute(selectedSortOrder);
            simpleJsonMovieData = savedInstanceState.getParcelableArrayList(KEY_SORT_ORDER);
            mRecyclerView.setAdapter(movieAdapter);
            Log.v(TAG, "SORT ORDER= ." + selectedSortOrder);
        }

        //   getSupportLoaderManager().initLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
        //specifying the space between images
        mRecyclerView.addItemDecoration(new VerticalSpacingDecoration(64));

        //the vertical divider
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.item_decorator)));
    }

    @Override
    public void returnData(ArrayList<Movie> simpleJsonMovieData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        movieAdapter = new MovieAdapter(this, simpleJsonMovieData, MainActivity.this);
        moviesList = simpleJsonMovieData;
        mRecyclerView.setAdapter(movieAdapter);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
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
        // Update the data that the adapter uses to create ViewHolders
        favoritesAdapter.swapCursor(data);
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
                returnData(simpleJsonMovieData);
                return true;

            case R.id.top_rated:
                myTask.execute(NetworkUtils.SORT_BY_RATING);
                selectedSortOrder = NetworkUtils.SORT_BY_RATING;
                returnData(simpleJsonMovieData);
                return true;

            case R.id.movie_favorites:
                getSupportLoaderManager().restartLoader(FAVORITES_LOADER_ID, null, MainActivity.this);
                favoritesAdapter = new FavoritesAdapter(this, MainActivity.this);
                mRecyclerView.setAdapter(favoritesAdapter);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_SORT_ORDER, selectedSortOrder);
        outState.putParcelableArrayList(KEY_SORT_ORDER, moviesList);
        super.onSaveInstanceState(outState);

    }
}