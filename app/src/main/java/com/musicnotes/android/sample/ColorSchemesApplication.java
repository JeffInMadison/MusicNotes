package com.musicnotes.android.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jeff on 9/28/2014.
 * Copyright JeffInMadison.com 2014
 */
public class ColorSchemesApplication extends Application {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = ColorSchemesApplication.class.getSimpleName();
    private static Context sAppContext;

    public static Context getAppContext() {
        return ColorSchemesApplication.sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ColorSchemesApplication.sAppContext = getApplicationContext();
    }
}
