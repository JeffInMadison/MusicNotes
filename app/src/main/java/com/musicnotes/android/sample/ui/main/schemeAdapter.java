package com.musicnotes.android.sample.ui.main;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;

/**
 * Created by Jeff on 9/26/2014.
 * Copyright JeffInMadison.com 2014
 */
public class SchemeAdapter extends ArrayAdapter<Scheme> {
    private static final String TAG = SchemeAdapter.class.getSimpleName();

    public SchemeAdapter(final Context context, final List<Scheme> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Scheme scheme = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item_scheme, parent, false);
        }

        TextView schemeNameTextView = (TextView) convertView.findViewById(R.id.schemeNameTextView);
        schemeNameTextView.setText(scheme.getName());

        TextView schemeColorsTextView = (TextView) convertView.findViewById(R.id.schemeColorsTextView);
        schemeColorsTextView.setText(Html.fromHtml(scheme.getColorListAsHtmlColoredString()));

        return convertView;

    }
}
