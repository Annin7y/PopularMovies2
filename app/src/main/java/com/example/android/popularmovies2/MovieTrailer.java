package com.example.android.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maino96-10022 on 9/3/2017.
 */

public class MovieTrailer implements Parcelable {

    /**
     * Movie trailer name
     */
    private String trailerName;

    /**
     * Movie trailer YouTube key
     */
    private String trailerKey;

    public MovieTrailer(String trailerName, String trailerKey) {
        this.trailerName = trailerName;
        this.trailerKey = trailerKey;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    protected MovieTrailer(Parcel in) {

        trailerName = in.readString();
        trailerKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(trailerName);
        dest.writeString(trailerKey);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}
