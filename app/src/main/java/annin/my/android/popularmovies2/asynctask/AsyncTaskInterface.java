package annin.my.android.popularmovies2.asynctask;

import java.util.ArrayList;

import annin.my.android.popularmovies2.model.Movie;

/**
 * Created by Maino96-10022 on 8/19/2017.
 */

public interface AsyncTaskInterface
{
    void returnData(ArrayList<Movie> simpleJsonMovieData);
}
