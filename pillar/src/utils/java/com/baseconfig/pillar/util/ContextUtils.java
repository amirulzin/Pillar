package com.baseconfig.pillar.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

public class ContextUtils {

    public static int getPixels(final Context context, float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics()));
    }

    /**
     * For measuring views before rendering finishes. The passed predraw listener is wrapped via an
     * internal predraw listener (auto added and removed).
     */
    public static View setOneShotPreDrawListener(View view, final ViewTreeObserver.OnPreDrawListener onPreDrawListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                viewTreeObserver.removeOnPreDrawListener(this);
                return onPreDrawListener.onPreDraw();
            }
        });

        return view;
    }

    public static int getFrameworkColorAttribute(final Context context, final int colorAttribute) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(colorAttribute, value, true);
        return value.data;
    }


}
