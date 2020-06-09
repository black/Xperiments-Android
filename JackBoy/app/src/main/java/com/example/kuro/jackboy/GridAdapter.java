package com.example.kuro.jackboy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Kuro on 8/3/2016.
 */
public class GridAdapter extends BaseAdapter{
    private Context mContext;

    public Integer[] layouts = {
            R.layout.breathlizertile, R.layout.airpollutiontile,
            R.layout.waterpollutiontile, R.layout.gassensortile,
    };

    public GridAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(layouts[i], null);
        return gridView;
    }
}
