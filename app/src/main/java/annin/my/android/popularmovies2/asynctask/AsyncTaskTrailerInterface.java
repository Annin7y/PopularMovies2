package annin.my.android.popularmovies2.asynctask;

import java.util.ArrayList;

import annin.my.android.popularmovies2.custom.MovieTrailer;

/**
 * Created by Maino96-10022 on 9/3/2017.
 */

public interface AsyncTaskTrailerInterface {
    void returnTrailerData(ArrayList<MovieTrailer> simpleJsonMovieTrailerData);
}

