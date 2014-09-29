package com.musicnotes.android.sample.ui.preview;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.model.SchemeColor;
import com.musicnotes.android.sample.ui.custom.ColorPreviewTabWidget;

import java.util.List;

public class PreviewFragment extends Fragment implements ColorPreviewTabWidget.OnTabChangedListener, ViewPager.OnPageChangeListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = PreviewFragment.class.getSimpleName();
    private static final String ARG_SCHEME = "ARG_SCHEME";
    private Scheme mScheme;

    private ListAdapter mColorListAdapter;
    private ViewPager mColorPreviewViewPager;
    private ColorPagerAdapter mPagerAdapter;
    private ColorPreviewTabWidget mTabWidget;

    public static PreviewFragment newInstance(Scheme scheme) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEME, scheme);
        fragment.setArguments(args);
        return fragment;
    }
    public PreviewFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mScheme = getArguments().getParcelable(ARG_SCHEME);
            List<SchemeColor> colorList = mScheme.getColorList();
            mPagerAdapter = new ColorPagerAdapter(colorList);
            mColorListAdapter = new ColorListAdapter(getActivity(), colorList);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        TextView schemeNameTextView = (TextView) rootView.findViewById(R.id.schemeNameTextView);
        schemeNameTextView.setText(mScheme.getName());

        mColorPreviewViewPager = (ViewPager) rootView.findViewById(R.id.colorPreviewViewPager);
        mColorPreviewViewPager.setAdapter(mPagerAdapter);
        mColorPreviewViewPager.setOnPageChangeListener(this);

        mTabWidget = (ColorPreviewTabWidget) rootView.findViewById(R.id.colorPreviewTabWidget);
        mTabWidget.setOnTabChangedListener(this);
        for (int ii = 0; ii < mColorListAdapter.getCount(); ii++) {
            SchemeColor schemeColor = ((SchemeColor) mColorListAdapter.getItem(ii));
            mTabWidget.addTab(schemeColor.getColorId());
        }
        mTabWidget.setSelectedTab(0);

        return rootView;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle(R.string.preview_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                ActionBar actionBar = getActivity().getActionBar();
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabChanged(final int tabIndex) {
        mColorPreviewViewPager.setCurrentItem(tabIndex);
    }

    @Override
    public void onPageScrolled(final int i, final float v, final int i2) { }

    @Override
    public void onPageSelected(final int position) {
        mTabWidget.setSelectedTab(position);
    }

    @Override
    public void onPageScrollStateChanged(final int i) { }
}
