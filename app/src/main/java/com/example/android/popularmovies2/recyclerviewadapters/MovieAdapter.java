package com.example.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.Movie;
import com.example.android.popularmovies2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Maino96-10022 on 8/17/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> moviesList = new ArrayList<Movie>();
    private Context context;
    private MovieAdapterOnClickHandler mClickHandler;
    public static final int IMAGE_HEIGHT= 185;
    public static final int IMAGE_WIDTH= 50;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie posterClick);
    }

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, ArrayList<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView releaseDate;

        public MovieAdapterViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            releaseDate = (TextView) view.findViewById(R.id.release_date);
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
            Movie posterClick = moviesList.get(adapterPosition);
            mClickHandler.onClick(posterClick);
        }

        public TextView getReleaseDate() {
            return releaseDate;
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        //Binding data
        final Movie movieView = moviesList.get(position);

        Picasso.with(context)
                .load(movieView.getPosterUrl())
                .resize(IMAGE_HEIGHT, IMAGE_WIDTH)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount()

    {
        return moviesList.size();
    }

    public void setMovieList(ArrayList<Movie> mMovieList) {
        this.moviesList.addAll(mMovieList);
        notifyDataSetChanged();
    }
}
