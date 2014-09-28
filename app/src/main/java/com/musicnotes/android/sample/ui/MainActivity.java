package com.musicnotes.android.sample.ui;

import android.app.Activity;
import android.os.Bundle;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.ui.display.DisplaySchemeFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, DisplaySchemeFragment.newInstance())
                    .commit();
        }
    }

//    @Override
//    public boolean onNavigateUp() {
//        getFragmentManager().popBackStack();
//        return super.onNavigateUp();
//    }
}
