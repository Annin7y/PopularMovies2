package annin.my.android.popularmovies2.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.asynctask.AsyncTaskReviewInterface;
import annin.my.android.popularmovies2.asynctask.AsyncTaskTrailerInterface;
import annin.my.android.popularmovies2.asynctask.MovieReviewAsyncTask;
import annin.my.android.popularmovies2.asynctask.MovieTrailerAsyncTask;
import annin.my.android.popularmovies2.data.AppDatabase;
import annin.my.android.popularmovies2.data.MovieViewModel;
import annin.my.android.popularmovies2.pojo.Movie;
import annin.my.android.popularmovies2.pojo.MovieReview;
import annin.my.android.popularmovies2.pojo.MovieTrailer;
import annin.my.android.popularmovies2.recyclerviewadapters.MovieReviewAdapter;
import annin.my.android.popularmovies2.recyclerviewadapters.MovieTrailerAdapter;
import annin.my.android.popularmovies2.utils.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static annin.my.android.popularmovies2.R.id.imageView;
import static annin.my.android.popularmovies2.R.id.imageViewYoutube;

public class DetailActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler, AsyncTaskReviewInterface,
        AsyncTaskTrailerInterface //LoaderManager.LoaderCallbacks<Cursor>
{
    //Tag for the log messages
    private static final String TAG = DetailActivity.class.getSimpleName();

    private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();
    private ArrayList<MovieTrailer> simpleJsonMovieTrailerData = new ArrayList<>();
    private Context context;
    @BindView(R.id.recyclerview_review)
    RecyclerView mRecyclerViewReview;
    @BindView(R.id.recyclerview_trailer)
    RecyclerView mRecyclerViewTrailer;
    private String youtubeKey;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter movieTrailerAdapter;
    public String movieId;
    private ShareActionProvider mShareActionProvider;
    private static final String BASE_YOUTUBE_URL_SHARE = "http://www.youtube.com/watch?v=";
    Movie movie;
    MovieTrailer firstTrailer;
    ImageView poster;
    ImageView youtube_thumbnail;
    @BindView(R.id.favorites_button)
    Button favoritesButton;
    @BindView(R.id.empty_view_review)
    TextView emptyReview;
    @BindView(R.id.empty_view_trailer)
    TextView emptyTrailer;
    public static final String Name = "nameKey";
    public static final String ACTION_DATA_UPDATED =
            "annin.my.android.MovieWidgetProvider.ACTION_DATA_UPDATED";

    // Create AppDatabase member variable for the Database
    // Member variable for the Database
   private AppDatabase mDb;

   //ViewModel variable
    private MovieViewModel mMovieViewModel;

    // Keep track of whether the selected movie is Favorite or not
    private boolean isFavorite;

    /**
     * Identifier for the favorites data loader
     */
    private static final int FAVORITES_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();
        ButterKnife.bind(this);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, context);
        movieTrailerAdapter = new MovieTrailerAdapter(this, simpleJsonMovieTrailerData, context);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);
        mRecyclerViewTrailer.setAdapter(movieTrailerAdapter);

        RecyclerView.LayoutManager mReviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReview.setLayoutManager(mReviewLayoutManager);

        RecyclerView.LayoutManager mTrailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrailer.setLayoutManager(mTrailerLayoutManager);

        poster = (ImageView) findViewById(imageView);

        youtube_thumbnail = (ImageView) findViewById(imageViewYoutube);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // Set a click listener for the Favorite button
        favoritesButton.setOnClickListener(view -> {
//        favoritesButton.setOnClickListener(new View.OnClickListener()
//        {
//                                               @Override
//                                               public void onClick(View view)
//                                               {

                                                   if (isFavorite) {
                                                       mMovieViewModel.delete(movie);

                                                       // If the movie is already a favorite, we remove it from the DB
//                    mMovieViewModel.delete(movie).observe(DetailActivity.this, new Observer<Boolean>() {
//                        @Override
//                        public void onChanged(@Nullable Boolean isDeleteOk) {
//                            if (isDeleteOk != null && isDeleteOk) {
//                                // If everything was OK,
//                                // we change the button text and set isFavorite to false
//                                Toast.makeText(DetailActivity.this, getString(R.string.movie_removed_from_favorites), Toast.LENGTH_SHORT).show();
//                                favoritesButton.setText(R.string.favorites_button_text_add);
//                                isFavorite = false;
//                            }
//                        }
//                    });

                                                   } else {
                                                       // If the movie is not favorite, we add it to the DB
                                                       mMovieViewModel.insert(movie);
                                                   }

                                           });

//                    mMovieViewModel.insert(movie).observe(DetailActivity.this, new Observer<Boolean>() {
//                        @Override
//                        public void onChanged(@Nullable Boolean isInsertOk) {
//                            if (isInsertOk != null && isInsertOk) {
//                                // If everything was OK,
//                                // we change the button text and set isFavorite to true
//                                Toast.makeText(DetailActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
//                                favoritesButton.setText((R.string.favorites_button_text_remove));
//                                isFavorite = true;
//                            }
//                        }
//                    });
//                }
//            }
//        });

        if (getIntent() != null && getIntent().getExtras() != null) {
            movie = getIntent().getExtras().getParcelable("Movie");
            Picasso.with(this)
                    .load(movie.getFullPosterUrl())
                    .into(poster);

            // Extract the movie ID from the selected movie
            String movieId = Objects.requireNonNull(movie).getMovieId();

            //The code below was used when running the Room Database on the main thread
            // Query the DB for the selected movie.
            // This can return true (if the movie is already favorite),
            // or false (if the movie is not favorite)
            // isFavorite = mMovieViewModel.select(movieId);

            // If the movie is favorite, we show the "Remove from Favorites" text.
            // Otherwise, we show "Add to Favorites".
//            if (isFavorite) {
//                favoritesButton.setText(getString(R.string.remove_from_favorites));
//            } else {
//                favoritesButton.setText(getString(R.string.favorites_button_text_add));
//            }
            mMovieViewModel.select(movieId);
            //The code below is to set the button in the Detail Activity to "Remove from Favorites"
            //when we click on a movie in the Favorites list
            // mMovieViewModel.isFavorite().observe(this, new Observer<Boolean>() {
            //   @Override
            //  public void onChanged(Boolean value) {
            //Implementing a lambda expression
            mMovieViewModel.isFavorite().observe(this, value ->
            {
                isFavorite = value;
                if (isFavorite) {
                    favoritesButton.setText(getString(R.string.favorites_button_text_remove));
                } else {
                    favoritesButton.setText(getString(R.string.favorites_button_text_add));
                }
            });
        }


//                mMovieViewModel.select(movieId).observe(DetailActivity.this, new Observer<Movie>()
//                {
//                    @Override
//                    public void onChanged(@Nullable Movie movie)
//                    {
//                        if (movie != null)
//                        {
//                            isFavorite = true;
//                            favoritesButton.setText(getString(R.string.favorites_button_text_remove));
//                        }
//                    }
//                });
//
//            } else
//                {
//                    favoritesButton.setText(getString(R.string.favorites_button_text_add));
//                    isFavorite = false;
//                           }


            //Log.i("movieId: ", movie.getMovieId());
            //  Timber.i( "movieId:" +  movie.getMovieId());

            //Store MovieInfo in SharedPreferences
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

            Gson gson = new Gson();
            String json = gson.toJson(movie);
            prefsEditor.putString("MovieList_Widget", json);
            prefsEditor.apply();

            Intent intent = new Intent(ACTION_DATA_UPDATED)
                    .setPackage(context.getPackageName());
            context.sendBroadcast(intent);

            MovieReviewAsyncTask myReviewTask = new MovieReviewAsyncTask(this);
            myReviewTask.execute(movieId);

            MovieTrailerAsyncTask myTrailerTask = new MovieTrailerAsyncTask(this);
            myTrailerTask.execute(movieId);

            TextView originalTitle = (TextView) findViewById(R.id.original_title);
            originalTitle.setText(movie.getOriginalTitle());

            TextView movieOverview = (TextView) findViewById(R.id.movie_overview);
            movieOverview.setText(movie.getMovieOverview());

            TextView voteAverage = (TextView) findViewById(R.id.vote_average);
            voteAverage.setText(movie.getVoteAverage());

            TextView releaseDate = (TextView) findViewById(R.id.release_date);
            releaseDate.setText(movie.getReleaseDate());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;

            try {
                date = simpleDateFormat.parse(movie.getReleaseDate());
                date.toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String finalDate = newDateFormat.format(date);

            releaseDate.setText(finalDate);
        }

        //Content Provider code commented out
//                ContentValues values = new ContentValues();
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_ID, movie.getMovieId());
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_TITLE, movie.getOriginalTitle());
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW, movie.getMovieOverview());
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_VOTE, movie.getVoteAverage());
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_DATE, movie.getReleaseDate());
//                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH, movie.getPosterUrl());
//                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

//                if (uri != null)
//                {
//                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(DetailActivity.this, R.string.favorites_added, Toast.LENGTH_SHORT).show();
//                    favoritesButton.setVisibility(View.GONE);
//                }


        // Kick off the loader
       // getLoaderManager().initLoader(FAVORITES_LOADER, null, this);


    public void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData)
    {
        if (simpleJsonMovieReviewData.size() > 0)
        {
            movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, DetailActivity.this);
            mRecyclerViewReview.setAdapter(movieReviewAdapter);
        }
        else
        {
            //Toast message commented out; replaced with the text message below
           // Toast.makeText(DetailActivity.this, R.string.review_unavailable_toast, Toast.LENGTH_SHORT).show();
           //Code below(and the trailer code) based on the highest rated answer in this stackoverflow post:
           //https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
            emptyReview.setVisibility(View.VISIBLE);
        }
    }

    public void returnTrailerData(ArrayList<MovieTrailer> simpleJsonMovieTrailerData)
    {
        movieTrailerAdapter = new MovieTrailerAdapter(this, simpleJsonMovieTrailerData, DetailActivity.this);
        mRecyclerViewTrailer.setAdapter(movieTrailerAdapter);
        if (simpleJsonMovieTrailerData.size() > 0)
        {
            firstTrailer = simpleJsonMovieTrailerData.get(0);
            youtubeKey = firstTrailer.getTrailerKey();
        }
        else
            {
           // Toast.makeText(DetailActivity.this, R.string.trailer_unavailable_toast, Toast.LENGTH_SHORT).show();
                emptyTrailer.setVisibility(View.VISIBLE);
        }
        if (mShareActionProvider != null)
        {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    @Override
    public void onClick(MovieTrailer movieTrailer)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(NetworkUtils.buildUrlYouTube(movieTrailer.getTrailerKey()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    public Intent createShareIntent()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, BASE_YOUTUBE_URL_SHARE + youtubeKey);
        return shareIntent;
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle)
//    {
//        String[] projection = {MovieContract.MovieEntry._ID, MovieContract.MovieEntry.COLUMN_MOVIES_ID,};
//        String[] selectionArgs = new String[]{movieId};
//
//        switch (loaderId)
//        {
//            case FAVORITES_LOADER:
//                return new CursorLoader(this,   // Parent activity context
//                        MovieContract.MovieEntry.CONTENT_URI,   // Provider content URI to query
//                        projection,             // Columns to include in the resulting Cursor
//                        MovieContract.MovieEntry.COLUMN_MOVIES_ID + "=?",
//                        selectionArgs,
//                        null);                  // Default sort order
//
//            default:
//                throw new RuntimeException("Loader Not Implemented: " + loaderId);
//        }
//    }
//
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
//    {
//        if ((cursor != null) && (cursor.getCount() > 0))
//        {
//            //"Add to Favorites" button is disabled in the Detail Activity when the user clicks on a movie stored in Favorites
//            favoritesButton.setEnabled(false);
//        }
//    }
//
//    public void onLoaderReset(Loader<Cursor> cursorLoader)
//    {
//    }
}
