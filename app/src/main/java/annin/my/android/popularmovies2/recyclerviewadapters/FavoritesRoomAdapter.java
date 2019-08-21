package annin.my.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.pojo.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FavoritesRoomAdapter extends RecyclerView.Adapter<FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder>
        {
private static final String TAG = FavoritesRoomAdapter.class.getSimpleName();

private Context context;
public List<Movie> roomMoviesList;
private MovieAdapter.MovieAdapterOnClickHandler mClickHandler;
public static final int IMAGE_HEIGHT = 185;
public static final int IMAGE_WIDTH = 50;


public FavoritesRoomAdapter(MovieAdapter.MovieAdapterOnClickHandler clickHandler, Context context)
        {
        mClickHandler = clickHandler;
        this.context = context;
        }

public class FavoritesRoomAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    @BindView(R.id.imageView)
    ImageView imageView;

    public FavoritesRoomAdapterViewHolder(View view)
    {
        super(view);
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int adapterPosition = getAdapterPosition();
        Movie posterClick = roomMoviesList.get(adapterPosition);
        mClickHandler.onClick(posterClick);
    }
}

    @Override
    public void onBindViewHolder(FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder holder, int position)
    {
        //Binding data
        final Movie movieView = roomMoviesList.get(position);

        Picasso.with(context)
                .load(movieView.getFullPosterUrl())
                .error(R.drawable.user_placeholder_error)
                .into(holder.imageView);
        // Log.e(TAG, "Failed to load image.");
        Timber.e("Failed to load image.");

    }

    public void setMovies(List<Movie> movies)
    {
        roomMoviesList = movies;
        notifyDataSetChanged();
    }

    public Movie getMovieAt(int position)
    {
        return roomMoviesList.get(position);
    }


    @Override
    public FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder(view);
    }

            public int getItemCount()
            {
                if(roomMoviesList != null)
                return roomMoviesList.size();
                else return 0;
            }
}


