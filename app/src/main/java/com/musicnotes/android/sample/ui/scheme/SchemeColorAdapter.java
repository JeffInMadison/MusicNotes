package com.musicnotes.android.sample.ui.scheme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Jeff on 9/25/2014.
* Copyright JeffInMadison.com 2014
*/
class SchemeColorAdapter extends ArrayAdapter<SchemeColor> {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = SchemeColorAdapter.class.getSimpleName();

    public SchemeColorAdapter(final Context context, final List<SchemeColor> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final SchemeColor schemeColor = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item_scheme_color, parent, false);
        }

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.schemeColorCheckBox);
        checkBox.setChecked(schemeColor.isSelected());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                schemeColor.setSelected(isChecked);
            }
        });
        checkBox.setText(schemeColor.getName());
        checkBox.setTextColor(schemeColor.getColorId());

        return convertView;
    }

    public List<SchemeColor> getSelectedColors() {
        List<SchemeColor> resultList = new ArrayList<SchemeColor>();
        for (int ii = 0; ii < getCount(); ii++) {
            if (getItem(ii).isSelected()) {
                resultList.add(getItem(ii));
            }
        }
        return resultList;
    }
}
