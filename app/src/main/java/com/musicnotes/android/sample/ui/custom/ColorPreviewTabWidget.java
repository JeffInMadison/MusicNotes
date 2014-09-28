package com.musicnotes.android.sample.ui.custom;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabWidget;
import android.widget.ViewSwitcher;

import com.musicnotes.android.sample.R;

/**
 * Created by Jeff on 9/27/2014.
 * Copyright JeffInMadison.com 2014
 */
public class ColorPreviewTabWidget extends TabWidget implements View.OnTouchListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = ColorPreviewTabWidget.class.getSimpleName();

    private OnTabChangedListener mOnTabChangedListener;
    private int mSelectedTab;

    public static interface OnTabChangedListener {
        public void onTabChanged(int tabIndex);
    }

    public void setOnTabChangedListener(OnTabChangedListener listener) {
        mOnTabChangedListener = listener;
    }

    public ColorPreviewTabWidget(final Context context) {
        super(context);
        init();
    }
    public ColorPreviewTabWidget(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ColorPreviewTabWidget(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setStripEnabled(false);
        setDividerDrawable(null);
    }

    public void addTab(int color) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.color_preview_tab_view, this, false);
        view.findViewById(R.id.colorViewSmall).setBackgroundColor(color);
        view.findViewById(R.id.colorViewLarge).setBackgroundColor(color);

        addView(view);
        view.setOnClickListener(new TabClickListener(getTabCount() - 1));
        view.setOnTouchListener(this);
    }

    public void setSelectedTab(int selectedTab) {
        for (int ii = 0; ii < getTabCount(); ii++) {
            View tabView = getChildTabViewAt(ii);
            if (ii == selectedTab) {
                ((ViewSwitcher)tabView).setDisplayedChild(1);
                mSelectedTab = ii;
                mOnTabChangedListener.onTabChanged(mSelectedTab);
            } else {
                ((ViewSwitcher)tabView).setDisplayedChild(0);
            }
        }
    }

    @Override
    public boolean onTouch(final View view, final MotionEvent event) {
        for (int ii = 0; ii < getTabCount(); ii++) {
            View tabView = getChildTabViewAt(ii);
            if (doesViewContainsPoints(tabView, event.getRawX(), event.getRawY())) {
                ((ViewSwitcher)tabView).setDisplayedChild(1);
                if (mSelectedTab != ii) {
                    mSelectedTab = ii;
                    mOnTabChangedListener.onTabChanged(mSelectedTab);
                }
            } else {
                ((ViewSwitcher)tabView).setDisplayedChild(0);
            }
        }
        return true;
    }

    private boolean doesViewContainsPoints(final View view, final float rx, final float ry) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Rect rect = new Rect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
        return rect.contains((int)rx, (int)ry);
    }

    private class TabClickListener implements OnClickListener {
        private final int mIndex;

        public TabClickListener(int index) {
            mIndex = index;
        }

        @Override
        public void onClick(View view) {
            if (mOnTabChangedListener != null && mIndex != mSelectedTab) {
                mSelectedTab = mIndex;
                setCurrentTab(mIndex);
                mOnTabChangedListener.onTabChanged(mIndex);
            }
        }
    }
}
