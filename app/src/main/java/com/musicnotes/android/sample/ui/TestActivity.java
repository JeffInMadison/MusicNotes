package com.musicnotes.android.sample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;
import com.musicnotes.android.sample.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TestActivity extends Activity {
    DragSortListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = (DragSortListView) findViewById(R.id.listview);
        String[] names = new String[]{"a", "b", "c", "d"};
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(names));
        adapter = new ArrayAdapter<String>(this, R.layout.item_test_layout, R.id.textView1, list);
        listView.setAdapter(adapter);
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);

        DragSortController controller = new DragSortController(listView);
        controller.setDragHandleId(R.id.imageView1);
        controller.setClickRemoveId(R.id.removeButton);
        controller.setRemoveEnabled(false);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);
        controller.setRemoveMode(DragSortController.CLICK_REMOVE);

        listView.setFloatViewManager(controller);
        listView.setOnTouchListener(controller);
        listView.setDragEnabled(true);
    }

    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
    {
        @Override
        public void drop(int from, int to)
        {
            if (from != to)
            {
                String item = adapter.getItem(from);
                adapter.remove(item);
                adapter.insert(item, to);
            }
        }
    };

    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener()
    {
        @Override
        public void remove(int which)
        {
            adapter.remove(adapter.getItem(which));
        }
    };
}
