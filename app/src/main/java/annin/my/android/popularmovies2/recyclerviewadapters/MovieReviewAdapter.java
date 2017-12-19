package annin.my.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.custom.MovieReview;

/**
 * Created by Maino96-10022 on 8/17/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private static final String TAG = MovieReviewAdapter.class.getSimpleName();

    private ArrayList<MovieReview> movieReviewList = new ArrayList<MovieReview>();
    private Context context;



    /**
     * Creates a MovieAdapter.
     */
    public MovieReviewAdapter(ArrayList<MovieReview> movieReviewList, Context context) {
        this.movieReviewList = movieReviewList;
        this.context = context;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder  {

        public TextView movieReview;
        public TextView reviewAuthor;


        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            movieReview = (TextView) view.findViewById(R.id.movie_review);
            reviewAuthor  = (TextView) view.findViewById(R.id.author_review);
        }
   }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {

        //Binding data
       final MovieReview movieReviewView = movieReviewList.get(position);

        holder.movieReview.setText(movieReviewView.getMovieReview());
        holder.reviewAuthor.setText(movieReviewView.getReviewAuthor());

    }

    @Override
    public int getItemCount() {
        return movieReviewList.size();
    }

    public void setMovieReviewList(ArrayList<MovieReview> mMovieReviewList) {
        this.movieReviewList.addAll(mMovieReviewList);
        notifyDataSetChanged();
    }
}
