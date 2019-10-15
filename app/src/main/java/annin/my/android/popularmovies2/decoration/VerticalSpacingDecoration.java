package annin.my.android.popularmovies2.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Maino96-10022 on 8/16/2017.
 */

public class VerticalSpacingDecoration extends RecyclerView.ItemDecoration
{
    private int spacing;

    public VerticalSpacingDecoration(int spacing)
    {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        outRect.bottom = spacing;
    }
}
