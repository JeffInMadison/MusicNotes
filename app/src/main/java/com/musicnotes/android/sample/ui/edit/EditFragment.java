package com.musicnotes.android.sample.ui.edit;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.db.DbHelper;

import java.util.ArrayList;

/**
 * Created by Jeff on 9/29/2014.
 * Copyright JeffInMadison.com 2014
 */
public class EditFragment extends ListFragment {
    ArrayAdapter<String> mAdapter;

    private String[] array;
    private ArrayList<String> mList;

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        String item = mAdapter.getItem(from);
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

    private DragSortListView mDslv;
    private DragSortController mController;
    private EditSchemeListener mEditSchemeListener;

    public interface EditSchemeListener {
        void onSchemeEdited();
    }

    public int dragStartMode = DragSortController.ON_DOWN;
    public boolean removeEnabled = false;
    public int removeMode = DragSortController.FLING_REMOVE;
    public boolean sortEnabled = true;
    public boolean dragEnabled = true;

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
    public DragSortController buildController(DragSortListView dslv) {
        // defaults are
        //   dragStartMode = onDown
        //   removeMode = flingRight
        DragSortController controller = new DragSortController(dslv);
        controller.setDragHandleId(R.id.drag_handle);
        controller.setClickRemoveId(R.id.click_remove);
        controller.setRemoveEnabled(removeEnabled);
        controller.setSortEnabled(sortEnabled);
        controller.setDragInitMode(dragStartMode);
        controller.setRemoveMode(removeMode);
        return controller;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mDslv = (DragSortListView) inflater.inflate(R.layout.dslv_fragment_main, container, false);

        mController = buildController(mDslv);
        mDslv.setFloatViewManager(mController);
        mDslv.setOnTouchListener(mController);
        mDslv.setDragEnabled(dragEnabled);

        return mDslv;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDslv = (DragSortListView) getListView();

        mDslv.setDropListener(onDrop);
        mDslv.setRemoveListener(onRemove);

        Bundle args = getArguments();
        if (args != null) {
        }

        mList = new ArrayList<String>(DbHelper.getInstance().getSchemeNames());

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_dslv, R.id.text, mList);
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
                if (mEditSchemeListener != null) {
                    mEditSchemeListener.onSchemeEdited();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
