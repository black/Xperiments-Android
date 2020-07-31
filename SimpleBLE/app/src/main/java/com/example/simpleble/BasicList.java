package com.example.simpleble;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BasicList extends BaseAdapter {
    private ArrayList<?> items;
    private int R_layout_IdView;
    private Activity act;

    public BasicList( Activity act, int r_layout_IdView,ArrayList<?> items) {
        this.items = items;
        this.R_layout_IdView = r_layout_IdView;
        this.act = act;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        TextView tv = view.findViewById(R.id.txtText);
        tv.setText(items.get(position).toString());

        //onItem(items.get(position), view, position);
        return view;
    }

}