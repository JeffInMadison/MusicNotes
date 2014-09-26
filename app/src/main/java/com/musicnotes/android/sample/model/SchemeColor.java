package com.musicnotes.android.sample.model;

import android.graphics.Color;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
public class SchemeColor {
    private static final String TAG = SchemeColor.class.getSimpleName();

    private String mName;
    private String mId;
    private Color mColor;

    public String getName() {
        return mName;
    }
    public void setName(final String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }
    public void setId(final String id) {
        mId = id;
    }

    public Color getColor() {
        return mColor;
    }
    public void setColor(final Color color) {
        mColor = color;
    }

    public SchemeColor(final String name, final String id) {
        mName = name;
        mId = id;
    }
}
