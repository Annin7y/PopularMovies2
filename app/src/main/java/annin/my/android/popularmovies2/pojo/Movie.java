package annin.my.android.popularmovies2.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * Created by Maino96-10022 on 8/16/2017.
 */

@Entity(tableName = "movies")
public class Movie implements Parcelable
{

    /**
     * Image URL
     */
    @ColumnInfo(name = "poster_url")
    private String posterUrl;

    /**
     * Base URL for the poster
     */
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    /**
     * Title of the movie
     */
    @ColumnInfo(name = "original_title")
    private String originalTitle;

    /**
     * Overview of the movie
     */
    @ColumnInfo(name = "movie_overview")
    private String movieOverview;

    /**
     * Movie rating
     */
    @ColumnInfo(name = "vote_average")
    private String voteAverage;

    /**
     * Movie release date
     */
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    /**
     * Movie id
     */
    @PrimaryKey
    @NonNull
    private String movieId;

    public Movie(String posterUrl, String originalTitle, String movieOverview, String voteAverage, String releaseDate, String movieId)
    {
        this.posterUrl = posterUrl;
        this.originalTitle = originalTitle;
        this.movieOverview = movieOverview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
    }

    public void setPosterUrl(String poster)
    {
        this.posterUrl = poster;
    }

    //Will only store the poster path in the database
    public String getPosterUrl()
    {
        return posterUrl;
    }

    //Will be used on all the Adapters
    public String getFullPosterUrl()
    {
        return BASE_POSTER_URL + posterUrl;
    }


    public void setOriginalTitle(String originalTitle)
    {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle()
    {
        return originalTitle;
    }

    public void setMovieOverview(String movieOverview)
    {
        this.movieOverview = movieOverview;
    }

    public String getMovieOverview()
    {
        return movieOverview;
    }

    public void setVoteAverage(String voteAverage)
    {
        this.voteAverage = voteAverage;
    }

    public String getVoteAverage()
    {
        return voteAverage;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getMovieId()
    {
        return movieId;
    }

    protected Movie(Parcel in)
    {
        posterUrl = in.readString();
        originalTitle = in.readString();
        movieOverview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        movieId = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(posterUrl);
        dest.writeString(originalTitle);
        dest.writeString(movieOverview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(movieId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
}

