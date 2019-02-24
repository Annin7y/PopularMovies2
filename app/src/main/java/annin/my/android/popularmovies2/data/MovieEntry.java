package annin.my.android.popularmovies2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

    //Annotate the class with Entity. Use "movies" for the table name
    @Entity(tableName = "movies")
    public class MovieEntry
    {
        // COMPLETED (3) Annotate the id as PrimaryKey. Set autoGenerate to true.
        @PrimaryKey(autoGenerate = true)
        /**
         * Unique ID number for the movies table(only for use in the database table). Type: INTEGER
         */
        private int id;

        /**
         * Movie title. Type: TEXT
         */
        private String originalTitle;

        /**
         * Movie synopsis. Type: TEXT
         */
        private String overview;

        /**
         * Movie rating . Type: TEXT
         */
        private String voteAverage;

        /**
         * Movie release date. Type: TEXT
         */
        private String releaseDate;

        /**
         * Movie image path/url. Type: URL
         */
        private String posterPath;

        // COMPLETED (4) Use the Ignore annotation so Room knows that it has to use the other constructor instead
        @Ignore
        public MovieEntry(String originalTitle, String overview, String voteAverage, String releaseDate, String posterPath)
        {
            this.originalTitle = originalTitle;
            this.overview = overview;
            this.voteAverage = voteAverage;
            this.releaseDate = releaseDate;
            this.posterPath = posterPath;
        }

        public MovieEntry(int id, String originalTitle, String overview, String voteAverage, String releaseDate, String posterPath )
        {
            this.id = id;
            this.originalTitle = originalTitle;
            this.overview = overview;
            this.voteAverage = voteAverage;
            this.releaseDate = releaseDate;
            this.posterPath = posterPath;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getOriginalTitle()
        {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle)
        {
            this.originalTitle = originalTitle;
        }

        public String getOverview()
        {
            return overview;
        }

        public void setOverview(String overview)
        {
            this.overview = overview;
        }

        public String getVoteAverage()
        {
            return voteAverage;
        }

        public void setVoteAverage(String voteAverage)
        {
            this.voteAverage = voteAverage;
        }

        public String getReleaseDate()
        {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate)
        {
            this.releaseDate = releaseDate;
        }

        public String getPosterPath()
        {
            return posterPath;
        }

        public void setPosterPath(String posterPath)
        {
            this.posterPath = posterPath;
        }
    }




