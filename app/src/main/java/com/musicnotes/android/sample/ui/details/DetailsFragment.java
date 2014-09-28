package com.musicnotes.android.sample.ui.details;

import android.app.Fragment;
import android.os.Bundle;
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

public class DetailsFragment extends Fragment implements ColorPreviewTabWidget.OnTabChangedListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = DetailsFragment.class.getSimpleName();
    private static final String ARG_SCHEME = "ARG_SCHEME";
    private Scheme mScheme;

    private ColorPagerAdapter mPagerAdapter;
    private ListAdapter mColorListAdapter;
    private View mColorPreviewView;
    private ColorPreviewTabWidget mTabWidget;

    public static DetailsFragment newInstance(Scheme scheme) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEME, scheme);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScheme = getArguments().getParcelable(ARG_SCHEME);
            List<SchemeColor> colorList = mScheme.getColorList();
            mPagerAdapter = new ColorPagerAdapter(colorList);
            mColorListAdapter = new ColorListAdapter(getActivity(), colorList);
        }
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        TextView schemeNameTextView = (TextView) rootView.findViewById(R.id.schemeNameTextView);
        schemeNameTextView.setText(mScheme.getName());

        mColorPreviewView = rootView.findViewById(R.id.colorPreviewView);

        mTabWidget = (ColorPreviewTabWidget) rootView.findViewById(R.id.colorPreviewTabWidget);
        mTabWidget.setOnTabChangedListener(this);
        for (int ii = 0; ii < mColorListAdapter.getCount(); ii++) {
            SchemeColor schemeColor = ((SchemeColor) mColorListAdapter.getItem(ii));
            mTabWidget.addTab(schemeColor.getId());
        }
        mTabWidget.setSelectedTab(0);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                getActivity().getActionBar().setHomeButtonEnabled(false);
                return true;
        }
        return false;
    }



    @Override
    public void onTabChanged(final int tabIndex) {
        SchemeColor schemeColor = (SchemeColor) mColorListAdapter.getItem(tabIndex);
        mColorPreviewView.setBackgroundColor(schemeColor.getId());
    }
}
