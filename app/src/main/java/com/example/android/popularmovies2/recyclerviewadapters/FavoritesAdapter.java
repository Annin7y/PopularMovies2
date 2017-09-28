package com.example.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.Movie;
import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.data.MovieContract;

/**
 * Created by Maino96-10022 on 9/24/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();

    private Context context;
    private Cursor cursor;
    private FavoritesAdapter.FavoritesAdapterOnClickHandler mClickHandler;
    public static final int IMAGE_HEIGHT= 185;
    public static final int IMAGE_WIDTH= 50;

    public interface FavoritesAdapterOnClickHandler {
        void onClick(Movie imageClick);
    }


    public FavoritesAdapter(FavoritesAdapterOnClickHandler clickHandler,Context context, Cursor cursor) {
        mClickHandler = clickHandler;
        this.context = context;
        this.cursor = cursor;
    }


    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView releaseDate;


       public FavoritesAdapterViewHolder(View view) {
            super(view);
           imageView = (ImageView) view.findViewById(R.id.imageView);
           releaseDate = (TextView) view.findViewById(R.id.release_date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();

            Movie posterClick = moviesList.get(adapterPosition);
                mClickHandler.onClick(posterClick);
            }


        }

    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position) {

        // Determine the values of the wanted data
        final int idIndex = cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry._ID));

        int movieIdIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_ID);
        int movieTitleIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_TITLE);
        int movieOverviewIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW);
        int movieVoteIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_VOTE);
        int movieDateIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_DATE);

        cursor.moveToPosition(position); // get to the right location in the cursor

        final int id = cursor.getInt(movieIdIndex);
        String title = cursor.getString(movieTitleIndex);
        String overview = cursor.getString(movieOverviewIndex);
        String vote = cursor.getString(movieVoteIndex);
        String date = cursor.getString(movieDateIndex);

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

