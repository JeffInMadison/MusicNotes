package com.musicnotes.android.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.musicnotes.android.sample.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
public class Scheme implements Parcelable {

    int mId;
    String mName;
    ArrayList<SchemeColor> mColorList;

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

    public void setColorList(final ArrayList<SchemeColor> colorList) {
        mColorList = colorList;
    }

    public Scheme() { }


    /**
     * Returns an html string where each color is wrapped in a <font color= tag for inserting into
     * a TextView via Html.fromHtml
     * @return html String of colors.
     */
    public String getColorListAsHtmlColoredString() {
        List<String> colorStrings = new ArrayList<String>();
        for (SchemeColor schemeColor : mColorList) {
            String colorString = String.format("<font color=%s>%s</font>", schemeColor.getColorHex(), schemeColor.getName());
            colorStrings.add(colorString);
        }
        return StringUtils.getCommaSeparatedString(colorStrings, true);
    }

// Parcelable code

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeSerializable(this.mColorList);
    }

    private Scheme(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        //noinspection unchecked
        this.mColorList = (ArrayList<SchemeColor>) in.readSerializable();
    }

    public static final Parcelable.Creator<Scheme> CREATOR = new Parcelable.Creator<Scheme>() {
        public Scheme createFromParcel(Parcel source) {
            return new Scheme(source);
        }

        public Scheme[] newArray(int size) {
            return new Scheme[size];
        }
    };
}
