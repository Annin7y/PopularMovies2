package annin.my.android.popularmovies2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import annin.my.android.popularmovies2.pojo.Movie;


@Entity(tableName = "movies")
public class MovieEntry
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Image URL
     */
    private String posterUrl;

    /**
     * Base URL for the poster
     */
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    /**
     * Title of the movie
     */
    private String originalTitle;

    /**
     * Overview of the movie
     */
    private String movieOverview;

    /**
     * Movie rating
     */
    private String voteAverage;

    /**
     * Movie release date
     */
    private String releaseDate;

    /**
     * Movie id
     */
    private String movieId;


    public MovieEntry(int id,String posterUrl, String originalTitle, String movieOverview, String voteAverage, String releaseDate, String movieId)
    {
        this.id = id;
        this.posterUrl = posterUrl;
        this.originalTitle = originalTitle;
        this.movieOverview = movieOverview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setPosterUrl(String poster)
    {
        this.posterUrl = poster;
    }

    public String getPosterUrl()
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


}


