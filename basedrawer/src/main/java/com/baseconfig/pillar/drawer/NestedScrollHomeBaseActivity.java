package com.baseconfig.pillar.drawer;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class NestedScrollHomeBaseActivity extends BaseDrawerActivity {
    private NestedScrollView mNestedScrollView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mHeaderImageView;
    private Toolbar mTitleToolbar;

    public Toolbar getTitleToolbar() {
        return mTitleToolbar;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflateContent(R.layout.pillar_layout_base_nestedscroll_collapsible);
        innerSetupViews(view);
        setupView(savedInstanceState, view);
    }

    public abstract void setupView(Bundle savedInstanceState, View baseLayout);

    public NestedScrollView getNestedScrollView() {
        return mNestedScrollView;
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
        mNestedScrollView = (NestedScrollView) baseLayout.findViewById(R.id.lbnc_scrollview);
    }

    public View inflateNestedContent(@LayoutRes int layoutResourceId) {
        View inflatedView = LayoutInflater.from(NestedScrollHomeBaseActivity.this).inflate(layoutResourceId, mNestedScrollView, false);
        NestedScrollView.LayoutParams params = new NestedScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mNestedScrollView.addView(inflatedView, 0, params);
        return inflatedView;
    }
}
