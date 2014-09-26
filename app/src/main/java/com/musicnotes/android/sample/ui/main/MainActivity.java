package com.musicnotes.android.sample.ui.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.ui.scheme.AddSchemeActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ((Button)rootView.findViewById(R.id.testButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    startActivity(new Intent(getActivity(), AddSchemeActivity.class));
                }
            });
            return rootView;
        }
    }
}
