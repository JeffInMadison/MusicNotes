package com.musicnotes.android.sample.ui.edit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;
import com.musicnotes.android.sample.model.Scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 9/29/2014.
 * Copyright JeffInMadison.com 2014
 */
public class EditFragment extends ListFragment {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = EditFragment.class.getSimpleName();

    private EditSchemeAdapter mAdapter;
    private List<Scheme> mSchemeList;

    private DragSortListView mDragSortListView;
    private EditSchemeListener mEditSchemeListener;

    public interface EditSchemeListener {
        void onSchemeEdited();
    }

    public static EditFragment newInstance() {
        EditFragment editFragment = new EditFragment();
        Bundle args = new Bundle();
        editFragment.setArguments(args);
        return editFragment;
    }

    /**
     * Called in onCreateView. Override this to provide a custom
     * DragSortController.
     */
    public DragSortController buildController(DragSortListView dragSortListView) {
        DragSortController controller = new DragSortController(dragSortListView);
        controller.setDragHandleId(R.id.drag_handle);
        controller.setClickRemoveId(R.id.click_remove);
        controller.setRemoveEnabled(true);
        controller.setSortEnabled(true);
        controller.setDragInitMode(DragSortController.ON_DOWN);
        controller.setRemoveMode(DragSortController.CLICK_REMOVE);
        return controller;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mDragSortListView = (DragSortListView) inflater.inflate(R.layout.fragment_edit_dslv, container, false);

        DragSortController dragSortController = buildController(mDragSortListView);
        mDragSortListView.setFloatViewManager(dragSortController);
        mDragSortListView.setOnTouchListener(dragSortController);
        mDragSortListView.setDragEnabled(true);

        return mDragSortListView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(false);

        Bundle args = getArguments();
        if (args != null) {
        }

        mDragSortListView = (DragSortListView) getListView();
        mDragSortListView.setDropListener(onDrop);
        mDragSortListView.setRemoveListener(onRemove);

        mSchemeList = new ArrayList<Scheme>(DbHelper.getInstance().getAllSchemes());

        mAdapter = new EditSchemeAdapter(getActivity(), mSchemeList);
        setListAdapter(mAdapter);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof EditSchemeListener) {
            mEditSchemeListener = (EditSchemeListener) activity;
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_done:
                saveListToDB();
                if (mEditSchemeListener != null) {
                    mEditSchemeListener.onSchemeEdited();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveListToDB() {
        DbHelper.getInstance().deleteAllSchemes();
        DbHelper.getInstance().addScheme(mSchemeList);
    }

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        Scheme item = mAdapter.getItem(from);
                        mAdapter.remove(item);
                        mAdapter.insert(item, to);
                    }
                }
            };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    mAdapter.remove(mAdapter.getItem(which));
                }
            };

}
