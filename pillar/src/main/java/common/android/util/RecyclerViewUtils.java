package common.android.util;

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

    /**
     * <p>This class will try to calculate the target width of a view given a set container width,
     * the min width, and the max width, an optional margin width (distributing space between each
     * item and the container side) and an item count. <p>Get the results via the exposed get
     * methods.
     */
    public static final class MathScaffold {
        private float targetWidth;
        private int maxPossibleItemCount;
        private int containerWidth;
        //private int maxWidth;
        private int minWidth;
        private int marginWidth;
        //private int itemCount;

        /**
         * Only use this helper constructor to for {@link #fitToMinWidth()} calculation.
         */
        public MathScaffold(int containerWidth, int marginWidth, int minWidth) {
            this.containerWidth = containerWidth;
            this.marginWidth = marginWidth;
            this.minWidth = minWidth;
        }

        /**
         * <p>This calculation only requires containerWidth, minWidth, and marginWidth.
         * <p>
         * <p>Use this to {@link #getMaxPossibleItemCount()} with respect to the min width per item
         * and also the container width. The final item width will grow itself to fill extra space
         * (item margin is still respected!).
         * <p>
         * <p>The calculation will try to cram as much item for the given min width instead of
         * expanding each item like an uncontrollable hippo.
         * <p>
         * This looks the best for galleries (especially with the right min width!).
         * <p>
         * <p>Retrieve the final item width via {@link #getTargetWidth()}.
         */
        public MathScaffold fitToMinWidth() {
            int initialItemWidth = minWidth + marginWidth;
            int availableSpace = containerWidth - marginWidth;
            maxPossibleItemCount = availableSpace / initialItemWidth;
            targetWidth = (float) availableSpace / maxPossibleItemCount - marginWidth;
            return this;
        }

        public int getMaxPossibleItemCount() {
            return maxPossibleItemCount;
        }

        public float getTargetWidth() {
            return targetWidth;
        }
    }
}
