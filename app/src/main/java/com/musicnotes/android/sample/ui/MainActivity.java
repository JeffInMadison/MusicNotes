package com.musicnotes.android.sample.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.ui.add.AddSchemeFragment;
import com.musicnotes.android.sample.ui.display.DisplaySchemeFragment;
import com.musicnotes.android.sample.ui.edit.EditFragment;
import com.musicnotes.android.sample.ui.preview.PreviewFragment;

public class MainActivity extends Activity implements
                                                    DisplaySchemeFragment.DisplaySchemeListener,
                                                    AddSchemeFragment.AddSchemeListener,
                                                    EditFragment.EditSchemeListener,
                                                    PreviewFragment.PreviewListener {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, DisplaySchemeFragment.newInstance(), DisplaySchemeFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onAddSchemeClicked() {
        replaceFragment(AddSchemeFragment.newInstance(), AddSchemeFragment.class.getSimpleName(), true);
    }

    @Override
    public void onItemClicked(final Scheme scheme) {
        replaceFragment(PreviewFragment.newInstance(scheme), PreviewFragment.class.getSimpleName(), true);
    }

    @Override
    public void onEditClicked() {
        replaceFragment(EditFragment.newInstance(), EditFragment.class.getSimpleName(), false);
    }

    @Override
    public void onSchemeAdded() {
        getFragmentManager().popBackStackImmediate();
        DisplaySchemeFragment displaySchemeFragment = (DisplaySchemeFragment) getFragmentManager().findFragmentByTag(DisplaySchemeFragment.class.getSimpleName());
        if (displaySchemeFragment != null) {
            displaySchemeFragment.updateListView();
        }
    }

    @Override
    public void onAddSchemeCancelled() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onSchemeEdited() {
        getFragmentManager().popBackStackImmediate();
        DisplaySchemeFragment displaySchemeFragment = (DisplaySchemeFragment) getFragmentManager().findFragmentByTag(DisplaySchemeFragment.class.getSimpleName());
        if (displaySchemeFragment != null) {
            displaySchemeFragment.updateListView();
        }
    }

    @Override
    public void onPreviewFinished() {
        getFragmentManager().popBackStackImmediate();
    }


    private void replaceFragment(final Fragment fragment, final String stackName, boolean animate) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animate) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.alpha_out, R.anim.alpha_in, R.anim.slide_out);
        }
        fragmentTransaction.replace(R.id.container, fragment, stackName);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onNavigateUp() {
//        getFragmentManager().popBackStack();
//        return super.onNavigateUp();
//    }
}
