package com.example.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies2.Movie;
import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by Maino96-10022 on 9/24/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();

    private Context context;
    private Cursor cursor;
    private MovieAdapter.MovieAdapterOnClickHandler mClickHandler;
    public static final int IMAGE_HEIGHT = 185;
    public static final int IMAGE_WIDTH = 50;


    public FavoritesAdapter(MovieAdapter.MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        this.context = context;
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public FavoritesAdapterViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(getAdapterPosition());

            Movie movie = new Movie(posterUrl, originalTitle, movieOverview, voteAvreage, releaseDate, movieId);
            movie.setMovieId(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_ID)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_DATE)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_TITLE)));
            movie.setMovieOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW)));
            movie.setPosterUrl(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH)));
            mClickHandler.onClick(movie);
        }
    }


    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position) {

        cursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data

        int movieIdIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_ID);
        int moviePosterPathIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH);

        final int id = cursor.getInt(movieIdIndex);
        String posterPath = cursor.getString(moviePosterPathIndex);

        holder.itemView.setTag(id);

        Picasso.with(context)
                .load(posterPath)
                .resize(IMAGE_HEIGHT, IMAGE_WIDTH)
                .centerCrop()
                .into(holder.imageView);
        Log.e(TAG, "Failed to load image.");

    }

    @Override
    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
    }


    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (cursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = cursor;
        this.cursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public int getItemCount() {

        if (null == cursor)
            return 0;
        return cursor.getCount();
    }
}

