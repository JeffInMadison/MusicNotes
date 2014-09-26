package com.musicnotes.android.sample.ui.details;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;

public class DetailsFragment extends Fragment {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = DetailsFragment.class.getSimpleName();
    private static final String ARG_SCHEME = "ARG_SCHEME";
    private Scheme mScheme;

    private ColorPagerAdapter mPagerAdapter;

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
            mPagerAdapter = new ColorPagerAdapter(mScheme.getColorList());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        TextView schemeNameTextView = (TextView) rootView.findViewById(R.id.schemeNameTextView);
        schemeNameTextView.setText(mScheme.getName());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.selectedColorViewPager);
        viewPager.setAdapter(mPagerAdapter);
        return rootView;
    }
}
