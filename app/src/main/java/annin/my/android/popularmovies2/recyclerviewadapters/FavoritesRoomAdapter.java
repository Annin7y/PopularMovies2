package annin.my.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import annin.my.android.popularmovies2.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FavoritesRoomAdapter extends RecyclerView.Adapter<FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder>
        {
private static final String TAG = FavoritesRoomAdapter.class.getSimpleName();

private Context context;

private FavoritesRoomAdapter.FavoritesRoomAdapterOnClickHandler mClickHandler;
public static final int IMAGE_HEIGHT = 185;
public static final int IMAGE_WIDTH = 50;


public FavoritesRoomAdapter(MovieAdapter.MovieAdapterOnClickHandler clickHandler, Context context)
        {
        mClickHandler = clickHandler;
        this.context = context;
        }

public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    @BindView(R.id.imageView)
    ImageView imageView;

    public FavoritesAdapterViewHolder(View view)
    {
        super(view);
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        cursor.moveToPosition(getAdapterPosition());

        String posterUrl = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH));
        String originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_TITLE));
        String movieOverview = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_OVERVIEW));
        String voteAverage = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_VOTE));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_DATE));
        String movieId = cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_ID));

        Movie movie = new Movie(posterUrl, originalTitle, movieOverview, voteAverage, releaseDate, movieId);

        mClickHandler.onClick(movie);
    }
}

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position)
    {
        // get to the right location in the cursor
        cursor.moveToPosition(position);

        // Determine the values of the wanted data
        int movieIdIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_ID);
        int moviePosterPathIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIES_POSTER_PATH);

        final int id = cursor.getInt(movieIdIndex);
        String posterPath = cursor.getString(moviePosterPathIndex);

        holder.itemView.setTag(id);

        Picasso.with(context)
                .load(posterPath)
                .error(R.drawable.user_placeholder_error)
                .into(holder.imageView);
        // Log.e(TAG, "Failed to load image.");
        Timber.e("Failed to load image.");

    }

    @Override
    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
    }


}


