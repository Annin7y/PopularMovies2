package annin.my.android.popularmovies2.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.pojo.Movie;

public class MovieWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
private ArrayList<Movie> mMovieNameList;

private Context mContext;

public MovieWidgetViewFactory(Context context)
        {
        mContext = context;
        }

@Override
public void onCreate()
        {
        }

@Override
public void onDataSetChanged()
        {
        //code structure based on the code in this link:
        //https://stackoverflow.com/questions/37927113/how-to-store-and-retrieve-an-object-from-gson-in-android
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        String gsonString = sharedPreferences.getString("Movie_name", "");
        }

@Override
public int getCount()
        {
        return mMovieNameList.size();
        }

@Override
public RemoteViews getViewAt(int position)
        {
        Movie movieName = mMovieNameList.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_list_item);

        itemView.setTextViewText(R.id.movie_review_widget_name, movieName.getOriginalTitle());

        Intent intent = new Intent();
        intent.putExtra(MovieWidgetProvider.EXTRA_ITEM, movieName);
        itemView.setOnClickFillInIntent(R.id.movie_widget_list, intent);

        return itemView;
        }

@Override
public int getViewTypeCount()
        {
        return 1;
        }

@Override
public long getItemId(int position)
        {
        return position;
        }

@Override
public boolean hasStableIds()
        {
        return true;
        }

@Override
public RemoteViews getLoadingView()
        {
        return null;
        }

@Override
public void onDestroy()
        {
        }
}
