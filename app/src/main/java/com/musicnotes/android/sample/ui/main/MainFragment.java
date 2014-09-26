package com.musicnotes.android.sample.ui.main;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.ui.details.DetailsActivity;

import java.util.List;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
public class MainFragment extends ListFragment implements ActionMode.Callback, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = MainFragment.class.getSimpleName();
    private List<Scheme> mSchemeList;
    private ActionMode mActionMode;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSchemeList = new DbHelper(getActivity()).getAllSchemes();
        SchemeAdapter listAdapter = new SchemeAdapter(getActivity(), mSchemeList);
        setListAdapter(listAdapter);
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mActionMode == null) {
            Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
            detailsIntent.putExtra(DetailsActivity.ARG_SCHEME, mSchemeList.get(position));
            startActivity(detailsIntent);
        } else {
            // TODO handle item selection in actionMode
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
        return false;
    }

    @Override
    public void onDestroyActionMode(final ActionMode mode) {
        mActionMode = null;
    }

}
