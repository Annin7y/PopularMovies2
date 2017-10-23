package annin.my.android.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maino96-10022 on 9/3/2017.
 */

public class MovieTrailer implements Parcelable {

//    /**
//     * Movie trailer name
//     */
//    private String trailerName;

    private String BASE_YOUTUBE_URL_IMAGE = "http://img.youtube.com/vi/";

    private String youtubeSiteUrl;
    /**
     * Movie trailer YouTube key
     */
    private String trailerKey;

    public MovieTrailer(String youtubeSiteUrl, String trailerKey) {
        this.youtubeSiteUrl = youtubeSiteUrl;
        this.trailerKey = trailerKey;
    }

    public void setYoutubeSiteUrl(String youtubeSiteUrl) {
        this.youtubeSiteUrl = youtubeSiteUrl;
    }

    public String getYoutubeSiteUrl() {
        return BASE_YOUTUBE_URL_IMAGE + trailerKey + "/0.jpg";
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    protected MovieTrailer(Parcel in) {

        youtubeSiteUrl = in.readString();
        trailerKey = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(youtubeSiteUrl);
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
