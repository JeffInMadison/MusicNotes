package com.musicnotes.android.sample.ui.display;

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
 */
public class DisplaySchemeFragment extends ListFragment implements ActionMode.Callback, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = DisplaySchemeFragment.class.getSimpleName();
    private List<Scheme> mSchemeList;
    private ActionMode mActionMode;
    private DisplaySchemeListener mDisplaySchemeListener;
    private SchemeAdapter mSchemeAdapter;

    public interface DisplaySchemeListener {
        void onAddSchemeClicked();
        void onItemClicked(Scheme scheme);
    }

    public static DisplaySchemeFragment newInstance() {
        DisplaySchemeFragment fragment = new DisplaySchemeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DisplaySchemeFragment() {
        // Required empty public constructor
    }

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
        getListView().setOnItemLongClickListener(this);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DisplaySchemeListener) {
            mDisplaySchemeListener = (DisplaySchemeListener) activity;
        }
    }

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
        int id = item.getItemId();

        if (id == R.id.action_add) {
            if (mDisplaySchemeListener != null) {
                mDisplaySchemeListener.onAddSchemeClicked();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mActionMode == null) {
            if (mDisplaySchemeListener != null) {
                mDisplaySchemeListener.onItemClicked(mSchemeList.get(position));
            }
        } else {
            // TODO handle item selection in actionMode
            mSchemeAdapter.toggleSelection(position);
            boolean hasCheckedItems = mSchemeAdapter.getSelectedCount() > 0;

            if (hasCheckedItems) {

            }
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
