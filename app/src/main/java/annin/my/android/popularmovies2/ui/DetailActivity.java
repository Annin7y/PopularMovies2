package annin.my.android.popularmovies2.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.asynctask.AsyncTaskReviewInterface;
import annin.my.android.popularmovies2.asynctask.AsyncTaskTrailerInterface;
import annin.my.android.popularmovies2.asynctask.MovieReviewAsyncTask;
import annin.my.android.popularmovies2.asynctask.MovieTrailerAsyncTask;
import annin.my.android.popularmovies2.data.MovieContract;
import annin.my.android.popularmovies2.recyclerviewadapters.MovieReviewAdapter;
import annin.my.android.popularmovies2.recyclerviewadapters.MovieTrailerAdapter;
import annin.my.android.popularmovies2.utils.NetworkUtils;

import static annin.my.android.popularmovies2.R.id.imageView;
import static annin.my.android.popularmovies2.R.id.imageViewYoutube;

public class DetailActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler, AsyncTaskReviewInterface,
        AsyncTaskTrailerInterface {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private ArrayList<MovieReview> simpleJsonMovieReviewData = new ArrayList<>();

    private ArrayList<MovieTrailer> simpleJsonMovieTrailerData = new ArrayList<>();

    private Context context;

    private RecyclerView mRecyclerViewReview;

    private RecyclerView mRecyclerViewTrailer;

    Movie movie;

    private String youtubeKey;

    private String youtubeImage;

    MovieTrailer firstTrailer;

    private MovieReviewAdapter movieReviewAdapter;

    private MovieTrailerAdapter movieTrailerAdapter;

    public String movieId;

    RecyclerView.LayoutManager mReviewLayoutManager;

    RecyclerView.LayoutManager mTrailerLayoutManager;

    private ShareActionProvider mShareActionProvider;

    private static final String BASE_YOUTUBE_URL_SHARE = "http://www.youtube.com/watch?v=";

    ImageView poster;

    ImageView youtube_thumbnail;

    TextView movieReview;

    TextView reviewAuthor;

    Button favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = getApplicationContext();
        favoritesButton = (Button) findViewById(R.id.favorites_button);
        mRecyclerViewReview = (RecyclerView) findViewById(R.id.recyclerview_review);
        mRecyclerViewTrailer = (RecyclerView) findViewById(R.id.recyclerview_trailer);
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

        movieReview = (TextView) findViewById(R.id.movie_review);
        reviewAuthor = (TextView) findViewById(R.id.author_review);

        //add to favorites
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_ID, movie.getMovieId());
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_TITLE, movie.getOriginalTitle());
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW, movie.getMovieOverview());
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_VOTE, movie.getVoteAverage());
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_DATE, movie.getReleaseDate());
                values.put(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH, movie.getPosterUrl());
                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

                if (uri != null) {
                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                }
                finish();
            }

        });


        if (getIntent() != null && getIntent().getExtras() != null) {
            movie = getIntent().getExtras().getParcelable("Movie");
            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .into(poster);

            movieId = movie.getMovieId();

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
    }

    public void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData) {
        movieReviewAdapter = new MovieReviewAdapter(simpleJsonMovieReviewData, DetailActivity.this);
        mRecyclerViewReview.setAdapter(movieReviewAdapter);
        if (simpleJsonMovieReviewData.size() == 0) {
            Toast.makeText(DetailActivity.this, R.string.review_unavailable, Toast.LENGTH_SHORT).show();
        }
    }

    public void returnTrailerData(ArrayList<MovieTrailer> simpleJsonMovieTrailerData) {
        movieTrailerAdapter = new MovieTrailerAdapter(this, simpleJsonMovieTrailerData, DetailActivity.this);
        mRecyclerViewTrailer.setAdapter(movieTrailerAdapter);
        if (simpleJsonMovieTrailerData.size() > 0) {
            firstTrailer = simpleJsonMovieTrailerData.get(0);
            youtubeKey = firstTrailer.getTrailerKey();
        }  else {
            Toast.makeText(DetailActivity.this, R.string.trailer_unavailable, Toast.LENGTH_SHORT).show();
        }
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        }
    }

    @Override
    public void onClick(MovieTrailer movieTrailer) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(NetworkUtils.buildUrlYouTube(movieTrailer.getTrailerKey()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    public Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, BASE_YOUTUBE_URL_SHARE + youtubeKey);
        return shareIntent;
    }
}
