package com.example.android.popularmovies2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/17/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private ArrayList<MovieReview> movieReviewList = new ArrayList<MovieReview>();
    private Context context;
    private MovieReviewAdapter.MovieReviewAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieReviewAdapterOnClickHandler {
        void onClick(MovieReview posterClick);
    }

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieReviewAdapter(MovieReviewAdapter.MovieReviewAdapterOnClickHandler clickHandler, ArrayList<MovieReview> movieReviewList, Context context) {
        this.movieReviewList = movieReviewList;
        this.context = context;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView movieReview;

        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            movieReview = (TextView) view.findViewById(R.id.movie_review);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieReview posterClick = movieReviewList.get(adapterPosition);
            mClickHandler.onClick(posterClick);
        }

        public TextView getMovieReview() {
            return movieReview;
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {

        //Binding data
        final MovieReview movieReviewView = movieReviewList.get(position);


    }

    @Override
    public int getItemCount()
    {
        return movieReviewList.size();
    }

    public void setMovieReviewList(ArrayList<MovieReview> movieReviewList) {
        this.movieReviewList.addAll(movieReviewList);
        notifyDataSetChanged();
    }
}
