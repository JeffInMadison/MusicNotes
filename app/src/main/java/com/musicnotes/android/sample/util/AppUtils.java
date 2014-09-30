package com.musicnotes.android.sample.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Jeff on 9/29/2014.
 * Copyright JeffInMadison.com 2014
 */
public class AppUtils {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = AppUtils.class.getSimpleName();

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null && activity.getCurrentFocus() != null) {

            // clear focus first (this resolves some weird issues on 2.2, 2.3 devices)
            View focusView = activity.getCurrentFocus();
            focusView.clearFocus();

            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }

}
