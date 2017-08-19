package com.example.android.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maino96-10022 on 8/17/2017.
 */

public class MovieReview implements Parcelable {

    /**
     * Movie review
     */
    private String movieReview;

    /**
     * Author name
     */
    private String reviewAuthor;

    public MovieReview(String movieReview, String reviewAuthor)
    {
        this.movieReview = movieReview;
        this.reviewAuthor = reviewAuthor;
    }


    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieOverview) {
        this.movieReview = movieReview;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }


    protected MovieReview(Parcel in) {

        movieReview = in.readString();
        reviewAuthor = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(movieReview);
        dest.writeString(reviewAuthor);
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieReview> CREATOR = new Parcelable.Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

}
