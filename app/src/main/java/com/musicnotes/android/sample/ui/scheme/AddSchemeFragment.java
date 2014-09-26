package com.musicnotes.android.sample.ui.scheme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.SchemeColor;
import com.musicnotes.android.sample.util.StringUtils;

import java.util.List;

public class AddSchemeFragment extends Fragment implements TextWatcher, View.OnClickListener {
    private static final String TAG = AddSchemeFragment.class.getSimpleName();
    private ListView mColorListView;
    private List<SchemeColor> mSchemeColors;
    private SchemeColorAdapter mSchemeColorAdapter;
    private EditText mSchemeNameEditText;
    private Button mCancelButton;
    private Button mAddButton;

    public static AddSchemeFragment newInstance() {
        AddSchemeFragment fragment = new AddSchemeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public AddSchemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        DbHelper dbHelper = new DbHelper(getActivity());
        mSchemeColors = dbHelper.getSchemeColors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_scheme, container, false);

        mSchemeNameEditText = (EditText) rootView.findViewById(R.id.schemeNameEditText);
        mSchemeNameEditText.addTextChangedListener(this);
        mCancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(this);
        mAddButton = (Button) rootView.findViewById(R.id.addButton);
        mAddButton.setOnClickListener(this);

        mColorListView = (ListView) rootView.findViewById(R.id.colorsListView);
        mSchemeColorAdapter = new SchemeColorAdapter(getActivity(), mSchemeColors);
        mColorListView.setAdapter(mSchemeColorAdapter);
        return rootView;
    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable text) {
        if (StringUtils.isNullOrEmpty(text)) {
            mAddButton.setEnabled(false);
        } else {
            mAddButton.setEnabled(true);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
                break;
            case R.id.addButton:
                break;
        }
    }
}
