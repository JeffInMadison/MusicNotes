package com.musicnotes.android.sample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.mobeta.android.dslv.DragSortListView;
import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.ui.edit.EditFragment;

public class TestActivity extends Activity {
    DragSortListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getFragmentManager().beginTransaction().add(R.id.container, EditFragment.newInstance(), EditFragment.class.getSimpleName()).commit();
    }
}
