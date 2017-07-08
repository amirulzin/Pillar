package com.baseconfig.pillar.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewUtils {

    public static class VerticalItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public VerticalItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildAdapterPosition(view) == 0) outRect.top = space;

            outRect.bottom = space;
            outRect.left = space;
            outRect.right = space;

        }
    }

}
