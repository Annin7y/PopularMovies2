package annin.my.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import annin.my.android.popularmovies2.MovieTrailer;
import annin.my.android.popularmovies2.R;

/**
 * Created by Maino96-10022 on 9/4/2017.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {

    private static final String TAG = MovieTrailerAdapter.class.getSimpleName();

    private ArrayList<MovieTrailer> movieTrailerList = new ArrayList<MovieTrailer>();
    private Context context;
    private MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler mClickHandler;

    public interface MovieTrailerAdapterOnClickHandler {
        void onClick(MovieTrailer movieTrailer);
    }

    /**
     * Creates a MovieTrailerAdapter.
     */
    public MovieTrailerAdapter(MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler clickHandler, ArrayList<MovieTrailer> movieTrailerList, Context context) {
        this.movieTrailerList = movieTrailerList;
        this.context = context;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //  public TextView trailerName;
        public ImageView imageView;

        public MovieTrailerAdapterViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageViewYoutube);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer movieTrailer = movieTrailerList.get(adapterPosition);
            mClickHandler.onClick(movieTrailer);
        }
    }

    @Override
    public MovieTrailerAdapter.MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieTrailerAdapter.MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.MovieTrailerAdapterViewHolder holder, int position) {

        //Binding data
        final MovieTrailer movieTrailerView = movieTrailerList.get(position);

        //  holder.trailerName.setText(movieTrailerView.getTrailerName());
//        String youtubeKey = firstTrailer.getTrailerKey();
//        String youtubeImage = BASE_YOUTUBE_URL_IMAGE + youtubeKey + "/0.jpg";


        Picasso.with(context)
                .load(movieTrailerView.getYoutubeSiteUrl())
                        .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieTrailerList.size();
    }

    public void setMovieTrailerList(ArrayList<MovieTrailer> mMovieTrailerList) {
        this.movieTrailerList = mMovieTrailerList;
        notifyDataSetChanged();
    }
}