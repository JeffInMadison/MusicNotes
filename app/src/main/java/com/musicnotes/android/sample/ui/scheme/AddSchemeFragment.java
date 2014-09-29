package com.musicnotes.android.sample.ui.scheme;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.SchemeColor;
import com.musicnotes.android.sample.util.StringUtils;

import java.util.List;

public class AddSchemeFragment extends Fragment implements TextWatcher, View.OnClickListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = AddSchemeFragment.class.getSimpleName();

    private ListView mColorListView;
    private List<SchemeColor> mSchemeColors;
    private SchemeColorAdapter mSchemeColorAdapter;
    private EditText mSchemeNameEditText;
    private Button mCancelButton;
    private Button mAddButton;

    private AddSchemeListener mAddSchemeListener;

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
        if (getArguments() != null) {
        }
        mSchemeColors = DbHelper.getInstance().getSchemeColors();
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
        mColorListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
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
        int id = item.getItemId();

        if (id == R.id.action_add) {
            if (mAddSchemeListener != null) {
                mAddSchemeListener.onSchemeAdded();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
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
                if (mAddSchemeListener != null) {
                    mAddSchemeListener.onAddSchemeCancelled();
                }
                break;
            case R.id.addButton:
                String schemeName = mSchemeNameEditText.getText().toString();
                List<SchemeColor> selectedColors = mSchemeColorAdapter.getSelectedColors();
                if (DbHelper.getInstance().schemeNameExists(schemeName)) {
                    String message = String.format("There already is a scheme with that name. Please choose another one.", schemeName);
                    showDialog("Scheme name in use", message);
                } else if (selectedColors.size() < 2) {
                    showDialog("Not enough colors selected", "You need to choose at least 2 colors for a scheme.");
                } else if (mAddSchemeListener != null) {
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
