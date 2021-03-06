package com.musicnotes.android.sample.ui.display;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.Scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 *
 * DisplaySchemeFragment is the main ListView for displaying Schemes the user added.
 */
public class DisplaySchemeFragment extends ListFragment implements ActionMode.Callback, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = DisplaySchemeFragment.class.getSimpleName();

    private List<Scheme> mSchemeList;
    private ActionMode mActionMode;
    private DisplaySchemeListener mDisplaySchemeListener;
    private SchemeAdapter mSchemeAdapter;

    /**
     * Interface to tell containing Activity when events occur.
     */
    public interface DisplaySchemeListener {
        void onAddSchemeClicked();
        void onItemClicked(Scheme scheme);
        void onEditClicked();
    }

    public static DisplaySchemeFragment newInstance() {
        DisplaySchemeFragment fragment = new DisplaySchemeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DisplaySchemeFragment() { /* Required empty public constructor */ }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSchemeList = new ArrayList<Scheme>();
        mSchemeAdapter = new SchemeAdapter(getActivity(), mSchemeList);
        setListAdapter(mSchemeAdapter);
        setHasOptionsMenu(true);
        updateListView();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(this);
        // Disable long click. You'd normally long click to bring up a contextual edit in the actionbar
        // but we are using the drag sort list view to handle that now...
//        getListView().setOnItemLongClickListener(this);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DisplaySchemeListener) {
            mDisplaySchemeListener = (DisplaySchemeListener) activity;
        }
    }

    /**
     * Reloads Schemes from the database and updates the ListView. Can be used to update list when
     * data was changed externally.
     */
    public void updateListView() {
        mSchemeList.clear();
        mSchemeList.addAll(DbHelper.getInstance().getAllSchemes());
        mSchemeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (mDisplaySchemeListener != null) {
                    mDisplaySchemeListener.onAddSchemeClicked();
                }
                return true;
            case R.id.action_edit:
                if (mDisplaySchemeListener != null) {
                    mDisplaySchemeListener.onEditClicked();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set ActionBar to default view in the event it was changed by another activity
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(false);

        setEmptyText("No Schemes to display. \nUse the '+' to add one.");
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mActionMode == null) {
            if (mDisplaySchemeListener != null) {
                mDisplaySchemeListener.onItemClicked(mSchemeList.get(position));
            }
        } else {
            // handle item selection in actionMode
            mSchemeAdapter.toggleSelection(position);
//            boolean hasCheckedItems = mSchemeAdapter.getSelectedCount() > 0;
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mActionMode != null) {
            return false;
        }
        mActionMode = getActivity().startActionMode(this);
        view.setSelected(true);
        return true;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
        MenuInflater menuInflater = mode.getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
        SparseBooleanArray selected = mSchemeAdapter.getSelectedIds();
        for (int ii = 0; ii < selected.size(); ii++){
            if (selected.valueAt(ii)) {
                Scheme selectedItem = mSchemeAdapter.getItem(selected.keyAt(ii));
                DbHelper.getInstance().deleteSchemeByName(selectedItem.getName());
            }
        }
        mSchemeList.clear();
        mSchemeList.addAll(DbHelper.getInstance().getAllSchemes());
        // close action mode
        mode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(final ActionMode mode) {
        mSchemeAdapter.removeSelection();
        mActionMode = null;
    }
}
