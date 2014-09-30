package com.musicnotes.android.sample.ui.add;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.SchemeColor;
import com.musicnotes.android.sample.util.AppUtils;
import com.musicnotes.android.sample.util.StringUtils;

import java.util.List;

public class AddSchemeFragment extends Fragment implements View.OnClickListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = AddSchemeFragment.class.getSimpleName();

    private List<SchemeColor> mSchemeColors;
    private SchemeColorAdapter mSchemeColorAdapter;
    private EditText mSchemeNameEditText;

    private AddSchemeListener mAddSchemeListener;
    private View mDoneActionView;

    public interface AddSchemeListener {
        void onSchemeAdded();
        void onAddSchemeCancelled();
    }

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
        setRetainInstance(true);
        mSchemeColors = DbHelper.getInstance().getSchemeColors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_scheme, container, false);

        mSchemeNameEditText = (EditText) rootView.findViewById(R.id.schemeNameEditText);

        ListView colorListView = (ListView) rootView.findViewById(R.id.colorsListView);
        mSchemeColorAdapter = new SchemeColorAdapter(getActivity(), mSchemeColors);
        colorListView.setAdapter(mSchemeColorAdapter);
        colorListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        View customActionBar = inflater.inflate(R.layout.actionbar_custom_cancel_add, container, false);
        View cancelActionView = customActionBar.findViewById(R.id.action_cancel);
        cancelActionView.setOnClickListener(this);
        mDoneActionView = customActionBar.findViewById(R.id.action_done);
        mDoneActionView.setOnClickListener(this);

        ActionBar actionBar = getActivity().getActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(customActionBar);
        actionBar.setTitle(R.string.add_scheme_title);
        actionBar.setDisplayHomeAsUpEnabled(false);
        return rootView;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddSchemeListener) {
            mAddSchemeListener = (AddSchemeListener) activity;
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (mAddSchemeListener != null) {
                    mAddSchemeListener.onSchemeAdded();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.action_cancel:
                if (mAddSchemeListener != null) {
                    mAddSchemeListener.onAddSchemeCancelled();
                }
                break;
            case R.id.action_done:
                String schemeName = mSchemeNameEditText.getText().toString();
                List<SchemeColor> selectedColors = mSchemeColorAdapter.getSelectedColors();

                if (StringUtils.isNullOrEmpty(schemeName)) {
                    showDialog("No scheme name", "You must enter a scheme name first.");
                } else if (DbHelper.getInstance().schemeNameExists(schemeName)) {
                    showDialog("Scheme name in use", "There already is a scheme with that name. Please choose a new onesdfg.");
                } else if (selectedColors.size() < 2) {
                    showDialog("Not enough colors selected", "You need to choose at least 2 colors for a scheme.");
                } else if (mAddSchemeListener != null) {
                    AppUtils.hideSoftKeyboard(getActivity());
                    DbHelper.getInstance().addScheme(schemeName, selectedColors);
                    mAddSchemeListener.onSchemeAdded();
                }
                break;
        }
    }

    private void showDialog(final String title, final String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
