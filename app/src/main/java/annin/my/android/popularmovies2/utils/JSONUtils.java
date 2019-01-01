package annin.my.android.popularmovies2.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import annin.my.android.popularmovies2.model.Movie;
import annin.my.android.popularmovies2.model.MovieReview;
import annin.my.android.popularmovies2.model.MovieTrailer;

import static annin.my.android.popularmovies2.utils.NetworkUtils.KEY_TRAILER_KEY;

public class JSONUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = JSONUtils.class.getSimpleName();

    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_REVIEW_AUTHOR = "author";
    private static final String KEY_REVIEW_CONTENT = "content";
    private static final String KEY_TRAILER_SITE = "site";
    private static final String MOVIE_ID = "id";

    public JSONUtils()
    {
    }

    public static ArrayList<Movie> extractFeatureFromJson(String movieJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON))
        {
            return null;
        }

        ArrayList<Movie> movies = new ArrayList<>();
        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or movies).
            JSONArray movieArray = baseJsonResponse.getJSONArray("results");

            // For each movie in the movieArray, create an {@link Movie} object
            for (int i = 0; i < movieArray.length(); i++)
            {
                // Get a single movie description at position i within the list of movies
                JSONObject currentMovie = movieArray.getJSONObject(i);

                // Extract values for the following keys
                String posterName = currentMovie.getString(KEY_POSTER_PATH);
                String movieName = currentMovie.getString(KEY_ORIGINAL_TITLE);
                String overviewName = currentMovie.getString(KEY_OVERVIEW);
                String voteAverage = currentMovie.getString(KEY_VOTE_AVERAGE);
                String releaseDate = currentMovie.getString(KEY_RELEASE_DATE);
                String movieId = currentMovie.getString(MOVIE_ID);

                Movie movie = new Movie(posterName, movieName, overviewName, voteAverage, releaseDate, movieId);
                movies.add(movie);
            }
        }
        catch (JSONException e)
        {
            /** If an error is thrown when executing any of the above statements in the "try" block,
             catch the exception here, so the app doesn't crash. Print a log message
             with the message from the exception.
             */
            Log.e("QueryUtils", "Problem parsing movies JSON results", e);
        }

        // Return a list of movies
        return movies;
    }

    public static ArrayList<MovieReview> extractFeatureFromReviewJson(String movieReviewJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieReviewJSON))
        {
            return null;
        }

        ArrayList<MovieReview> moviesReviewList = new ArrayList<>();
        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieReviewJSON);

            JSONArray movieReviewArray = baseJsonResponse.getJSONArray("results");

            // For each movie review in the movieReviewArray, create an {@link MovieReview} object
            for (int i = 0; i < movieReviewArray.length(); i++)
            {
                // Get a single movie description at position i within the list of movies
                JSONObject currentMovieReview = movieReviewArray.getJSONObject(i);

                // Extract values for the following keys
                String authorName = currentMovieReview.getString(KEY_REVIEW_AUTHOR);
                String reviewName = currentMovieReview.getString(KEY_REVIEW_CONTENT);

                MovieReview review = new MovieReview(authorName, reviewName);
                moviesReviewList.add(review);
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing movies JSON review results", e);
        }

        // Return a list of movie reviews
        return moviesReviewList;
    }

    public static ArrayList<MovieTrailer> extractFeatureFromTrailerJson(String movieTrailerJSON)
    {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieTrailerJSON))
        {
            return null;
        }

        ArrayList<MovieTrailer> moviesTrailerList = new ArrayList<>();
        try
        {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieTrailerJSON);

            JSONArray movieTrailerArray = baseJsonResponse.getJSONArray("results");

            // For each movie review in the movieReviewArray, create an {@link MovieReview} object
            for (int i = 0; i < movieTrailerArray.length(); i++)
            {
                // Get a single movie description at position i within the list of movies
                JSONObject currentMovieTrailer = movieTrailerArray.getJSONObject(i);

                // Extract values for the following keys
                String trailerSite = currentMovieTrailer.getString(KEY_TRAILER_SITE);
                String trailerKey = currentMovieTrailer.getString(KEY_TRAILER_KEY);

                MovieTrailer trailer = new MovieTrailer(trailerSite, trailerKey);
                moviesTrailerList.add(trailer);
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing movies JSON review results", e);
        }

        // Return a list of movie trailers
        return moviesTrailerList;
    }
}





