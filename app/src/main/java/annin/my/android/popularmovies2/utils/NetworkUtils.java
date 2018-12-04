package annin.my.android.popularmovies2.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import annin.my.android.popularmovies2.BuildConfig;

import static android.content.ContentValues.TAG;

/**
 * Created by Maino96-10022 on 8/16/2017.
 */

public class NetworkUtils
{
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = "api_key";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static final String BASE_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch";
    public static final String KEY_TRAILER_KEY = "key";
    public static final String SORT_BY_POPULAR = "most_popular";
    public static final String SORT_BY_RATING = "top_rated";

    public NetworkUtils()
    {
    }

    /**
     * @param sortMode
     * @return either most popular or top rated movies
     */
    public static URL buildUrl(String sortMode)
    {
        URL url = null;
        try {
            if (sortMode.equals(SORT_BY_POPULAR))
            {
                Uri builtUri = Uri.parse(BASE_URL_POPULAR).buildUpon()
                        .appendQueryParameter(API_KEY, BuildConfig.OPEN_MOVIES_API_KEY)
                        .build();
                url = new URL(builtUri.toString());
            }
            else if (sortMode.equals(SORT_BY_RATING))
            {
                Uri builtUri = Uri.parse(BASE_URL_TOP_RATED).buildUpon()
                        .appendQueryParameter(API_KEY, BuildConfig.OPEN_MOVIES_API_KEY)
                        .build();
                url = new URL(builtUri.toString());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }


    public static URL buildUrlReview(String movieId)
    {
        URL urlReview = null;
        try
        {
            Uri movieReviewQueryUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(movieId)
                    .appendPath("reviews")
                    .appendQueryParameter(API_KEY, BuildConfig.OPEN_MOVIES_API_KEY)
                    .build();
            urlReview = new URL(movieReviewQueryUri.toString());

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + urlReview);

        return urlReview;
    }

    public static URL buildUrlTrailer(String movieId)
    {
        URL urlTrailer = null;
        try
        {
            Uri movieTrailerQueryUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(movieId)
                    .appendPath("videos")
                    .appendQueryParameter(API_KEY, BuildConfig.OPEN_MOVIES_API_KEY)
                    .build();
            urlTrailer = new URL(movieTrailerQueryUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + urlTrailer);

        return urlTrailer;
    }

    public static Uri buildUrlYouTube(String trailerKey)
    {
        Uri movieTrailerQueryUri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendQueryParameter("v", String.valueOf(trailerKey))
                .build();

        Log.v(TAG, "Built URI " + movieTrailerQueryUri);

        return movieTrailerQueryUri;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";
        Log.i("URL: ", url.toString());
        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving movie JSON results.", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String makeHttpReviewRequest(URL urlReview) throws IOException
    {
        String jsonReviewResponse = "";
        Log.i("URLREVIEW: ", urlReview.toString());
        // If the URL is null, then return early.
        if (urlReview == null)
        {
            return jsonReviewResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) urlReview.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonReviewResponse = readFromStream(inputStream);
            }
            else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving movie review JSON results.", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonReviewResponse;
    }

    public static String makeHttpTrailerRequest(URL urlTrailer) throws IOException
    {
        String jsonTrailerResponse = "";
        Log.i("URLTRAILER: ", urlTrailer.toString());
        // If the URL is null, then return early.
        if (urlTrailer == null)
        {
            return jsonTrailerResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) urlTrailer.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonTrailerResponse = readFromStream(inputStream);
            }
            else
                {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving movie trailer JSON results.", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonTrailerResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
