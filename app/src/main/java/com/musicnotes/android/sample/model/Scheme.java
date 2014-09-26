package com.musicnotes.android.sample.model;

import java.util.List;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
public class Scheme {

    int mId;
    String mName;
    List<SchemeColor> mColorList;

    public int getId() {
        return mId;
    }

    public void setId(final int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public List<SchemeColor> getColorList() {
        return mColorList;
    }

    public void setColorList(final List<SchemeColor> colorList) {
        mColorList = colorList;
    }

    public String getColorListAsHtmlColoredString() {
        return "";
    }
}
