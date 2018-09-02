package annin.my.android.popularmovies2.asynctask;

import java.util.ArrayList;

import annin.my.android.popularmovies2.model.MovieReview;

/**
 * Created by Maino96-10022 on 8/20/2017.
 */

public interface AsyncTaskReviewInterface {
    void returnReviewData(ArrayList<MovieReview> simpleJsonMovieReviewData);
}

