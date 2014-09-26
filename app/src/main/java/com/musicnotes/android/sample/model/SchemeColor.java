package com.musicnotes.android.sample.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */

public class SchemeColor implements Parcelable, Serializable {
    private static final String TAG = SchemeColor.class.getSimpleName();

    private String mName;
    private int mId;

    public String getName() {
        return mName;
    }
    public void setName(final String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }
    public void setId(final int id) {
        mId = id;
    }

    public SchemeColor(final String name, final int id) {
        mName = name;
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mId);
    }

    private SchemeColor(Parcel in) {
        this.mName = in.readString();
        this.mId = in.readInt();
    }

    public static final Parcelable.Creator<SchemeColor> CREATOR = new Parcelable.Creator<SchemeColor>() {
        public SchemeColor createFromParcel(Parcel source) {
            return new SchemeColor(source);
        }

        public SchemeColor[] newArray(int size) {
            return new SchemeColor[size];
        }
    };
}
