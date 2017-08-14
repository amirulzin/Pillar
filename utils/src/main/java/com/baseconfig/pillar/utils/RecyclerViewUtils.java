package com.baseconfig.pillar.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewUtils {

    /**
     * This class will add basic (and the correct) pixel margin to each list item in a vertical RecyclerView.
     */
    public static class VerticalItemDecoration extends RecyclerView.ItemDecoration {
        private int pixelMargin;

        public VerticalItemDecoration(int pixelMargin) {
            this.pixelMargin = pixelMargin;
        }

        public VerticalItemDecoration(Context context, int dpMargin) {
            this.pixelMargin = ContextUtils.getPixels(context, dpMargin);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildAdapterPosition(view) == 0) outRect.top = pixelMargin;

            outRect.bottom = pixelMargin;
            outRect.left = pixelMargin;
            outRect.right = pixelMargin;

        }
    }

}
