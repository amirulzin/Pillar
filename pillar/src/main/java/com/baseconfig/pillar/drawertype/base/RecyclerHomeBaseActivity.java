package com.baseconfig.pillar.drawertype.base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.baseconfig.pillar.R;


public abstract class RecyclerHomeBaseActivity extends BaseDrawerActivity {
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mHeaderImageView;
    private Toolbar mTitleToolbar;

    public Toolbar getTitleToolbar() {
        return mTitleToolbar;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflateContent(R.layout.pillar_layout_base_recycler_collapsible);
        innerSetupViews(view);
        setupView(savedInstanceState, view);
    }

    public abstract void setupView(Bundle savedInstanceState, View baseLayout);

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return mCollapsingToolbarLayout;
    }

    public ImageView getHeaderImageView() {
        return mHeaderImageView;
    }

    private void innerSetupViews(View baseLayout) {
        mTitleToolbar = (Toolbar) baseLayout.findViewById(R.id.lbnc_toolbar);
        mHeaderImageView = (ImageView) baseLayout.findViewById(R.id.lbnc_headerimage);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) baseLayout.findViewById(R.id.lbnc_collapsingtoolbar);
        mRecyclerView = (RecyclerView) baseLayout.findViewById(R.id.lbnc_recyclerview);

    }

    public void applySpecialOverlay() {
        final Drawable drawable;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            drawable = getResources().getDrawable(R.drawable.pillar_horizontal_shadow_top);
        } else
            drawable = getResources().getDrawable(R.drawable.pillar_horizontal_shadow_top, getTheme());

        getCollapsingToolbarLayout().setForeground(drawable);

    }

}
