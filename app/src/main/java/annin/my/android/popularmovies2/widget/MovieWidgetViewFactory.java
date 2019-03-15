package annin.my.android.popularmovies2.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.pojo.MovieReview;

public class MovieWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory
{
private ArrayList<MovieReview> mMovieReviewList;
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

        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieReview>>() {}.getType();
        String gsonString = sharedPreferences.getString("MovieReviewList_Widget", "");
        mMovieReviewList = gson.fromJson(gsonString, type);
        }

@Override
public int getCount()
        {
        return mMovieReviewList.size();
        }

@Override
public RemoteViews getViewAt(int position)
        {
        MovieReview movieReview = mMovieReviewList.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_list_item);

        itemView.setTextViewText(R.id.movie_review_widget_name, movieReview.getMovieReview());

        Intent intent = new Intent();
        intent.putExtra(MovieWidgetProvider.EXTRA_ITEM, movieReview);
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
