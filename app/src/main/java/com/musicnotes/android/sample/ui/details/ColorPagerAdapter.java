package com.musicnotes.android.sample.ui.details;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;

/**
 * Created by Jeff on 9/26/2014.
 * Copyright JeffInMadison.com 2014
 */
public class ColorPagerAdapter extends PagerAdapter {
    private static final String TAG = ColorPagerAdapter.class.getSimpleName();

    private final List<SchemeColor> mSchemeColorList;

    public ColorPagerAdapter(final List<SchemeColor> schemeColorList) {
        mSchemeColorList = schemeColorList;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View colorView = new View(container.getContext());
        colorView.setBackgroundColor(mSchemeColorList.get(position).getId());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        colorView.setLayoutParams(params);
        container.addView(colorView);
        return colorView;
    }

    @Override
    public int getCount() {
        return mSchemeColorList.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View)object);
    }
}
