package com.musicnotes.android.sample.ui.preview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;

/**
 * Created by Jeff on 9/26/2014.
 * Copyright JeffInMadison.com 2014
 */
public class ColorListAdapter extends ArrayAdapter<SchemeColor> {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = ColorListAdapter.class.getSimpleName();

    public ColorListAdapter(final Activity activity, final List<SchemeColor> colorList) {
        // intResource is -1 because we will return our own.
        super(activity, -1, colorList);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        SchemeColor schemeColor = getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.listview_item_color_preview, parent, false);
        View colorView = view.findViewById(R.id.colorView);
        colorView.setBackgroundColor(schemeColor.getColorId());
        return view;
    }
}
