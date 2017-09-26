package com.example.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Maino96-10022 on 9/24/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();
    private Context context;
    private Cursor cursor;

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position) {
    }
//    String contactName = cursor.getString(
//            cursor.getColumnIndex(DBOpenHelper.CONTACT_NAME));
//    String contactPhone = cursor.getString(
//            cursor.getColumnIndex(DBOpenHelper.CONTACT_PHONE));
//    TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
//    TextView phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
//        nameTextView.setText(contactName);
//        phoneTextView.setText(contactPhone);





    @Override
    public int getItemCount() {

        if (null == cursor)
            return 0;
        return cursor.getCount();
    }
    }

