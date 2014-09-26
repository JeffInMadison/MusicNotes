package com.musicnotes.android.sample.ui.scheme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;
import java.util.zip.Inflater;

/**
* Created by Jeff on 9/25/2014.
* Copyright JeffInMadison.com 2014
*/
class SchemeColorAdapter extends ArrayAdapter<SchemeColor> {
    private static final String TAG = SchemeColorAdapter.class.getSimpleName();

    public SchemeColorAdapter(final Context context, final List<SchemeColor> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        SchemeColor schemeColor = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item_scheme_color, parent, false);
        }

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.schemeColorCheckBox);
        checkBox.setText(schemeColor.getName());
        checkBox.setTextColor(Integer.parseInt(schemeColor.getId()));

        return convertView;
    }
}
