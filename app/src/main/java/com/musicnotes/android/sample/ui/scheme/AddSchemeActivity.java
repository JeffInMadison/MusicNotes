package com.musicnotes.android.sample.ui.scheme;

import android.app.Activity;
import android.os.Bundle;

import com.musicnotes.android.sample.R;

public class AddSchemeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheme);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, AddSchemeFragment.newInstance())
                    .commit();
        }
    }
}
