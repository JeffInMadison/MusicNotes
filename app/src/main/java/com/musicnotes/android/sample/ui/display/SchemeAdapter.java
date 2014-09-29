package com.musicnotes.android.sample.ui.display;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;

import java.util.List;

/**
 * Created by Jeff on 9/26/2014.
 * Copyright JeffInMadison.com 2014
 */
public class SchemeAdapter extends ArrayAdapter<Scheme> {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = SchemeAdapter.class.getSimpleName();
    private SparseBooleanArray mSelectedItemsIds;

    public SchemeAdapter(final Context context, final List<Scheme> objects) {
        super(context, -1, objects);
        mSelectedItemsIds = new SparseBooleanArray();
    }

    public void toggleSelection(int position)
    {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value)
    {
        if(value) {
            mSelectedItemsIds.put(position, value);
        } else {
            mSelectedItemsIds.delete(position);
        }

        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
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

        convertView.setBackgroundColor(mSelectedItemsIds.get(position)? 0x9934B5E4: Color.TRANSPARENT);

        return convertView;
    }
}
