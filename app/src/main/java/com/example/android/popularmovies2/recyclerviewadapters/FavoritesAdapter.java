package com.example.android.popularmovies2.recyclerviewadapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies2.R;

/**
 * Created by Maino96-10022 on 9/24/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();
    private Context context;
    private Cursor cursor;

    public FavoritesAdapter(Context context,Cursor cursor ) {

        this.context = context;
        this.cursor = cursor;
    }



    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder {


        public FavoritesAdapterViewHolder(View view) {
            super(view);
        }
    }

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
    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
    }


    @Override
    public int getItemCount() {

        if (null == cursor)
            return 0;
        return cursor.getCount();
    }
    }

