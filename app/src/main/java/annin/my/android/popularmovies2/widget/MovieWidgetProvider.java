package annin.my.android.popularmovies2.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import annin.my.android.popularmovies2.R;
import annin.my.android.popularmovies2.pojo.Movie;
import annin.my.android.popularmovies2.ui.DetailActivity;
import timber.log.Timber;

public class MovieWidgetProvider extends AppWidgetProvider
{
    //The following code is based on the code in these links:
    //https://joshuadonlan.gitbooks.io/onramp-android/content/widgets/collection_widgets.html
    //http://www.vogella.com/tutorials/AndroidWidgets/article.html
    //https://medium.com/android-bits/android-widgets-ad3d166458d3

    public static final String ACTION_DATA_UPDATED =
            "annin.my.android.MovieWidgetProvider.ACTION_DATA_UPDATED";

    public static final String EXTRA_ITEM =
            "annin.my.android.MovieWidgetProvider.EXTRA_ITEM";

    public void setPendingIntentTemplate(int viewId, PendingIntent pendingIntentTemplate)
    {
    }

    private Movie movie;
    private String movieString;

    /*
    This method is called once a new widget is created as well as every update interval.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds)
    {
        for (int i = 0; i < appWidgetIds.length; i++)
        {
            int widgetId = appWidgetIds[i];


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String gsonString = sharedPreferences.getString("MovieList_Widget", "");
            movie = gson.fromJson(gsonString, Movie.class);

            //    Build the intent to call the service
            Intent intent = new Intent(context, MovieWidgetProvider.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            //Log.d("onUpdate", "method working");
            Timber.d("method working");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_widget_provider);
            views.setTextViewText(R.id.movie_widget_title, movie.getOriginalTitle());

            Intent detailIntent = new Intent(context, DetailActivity.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.movie_widget_title, pIntent);

            appWidgetManager.updateAppWidget(widgetId, views);
        }

//       Picasso code based on:
//       https://www.codota.com/code/java/classes/android.widget.RemoteViews
//        Picasso picasso = PicassoProvider.get();
//          picasso.load(Data.URLS[new Random().nextInt(Data.URLS.length)]) //
//            .placeholder(R.drawable.placeholder) //
//            .error(R.drawable.error) //
//           .transform(new GrayscaleTransformation(picasso)) //
//            .into(updateViews, R.id.image, appWidgetIds);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Code structure based on the code in this blog:
        //http://android-er.blogspot.com/2010/10/update-widget-in-onreceive-method.html
        super.onReceive(context, intent);

        if (ACTION_DATA_UPDATED.equals(intent.getAction()))
        {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), MovieWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}
