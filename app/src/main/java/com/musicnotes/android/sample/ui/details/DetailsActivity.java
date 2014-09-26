package com.musicnotes.android.sample.ui.details;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;

public class DetailsActivity extends Activity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    public static final String ARG_SCHEME = "ARG_SCHEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Scheme scheme = null;
        if (getIntent().getExtras() != null ) {
            scheme = getIntent().getExtras().getParcelable(ARG_SCHEME);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(scheme))
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            return rootView;
        }
    }
}