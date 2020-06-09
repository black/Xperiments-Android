package com.example.kuro.bloodpressure.DataBase;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kuro.bloodpressure.R;

import java.util.List;

/**
 * Created by Kuro on 4/20/2016.
 */
public class ListDataAdapter extends ArrayAdapter {

    List<DataProvider> mlist;

    public ListDataAdapter(Context context, int resource, List<DataProvider> mPersonList) {
        super(context, resource);
        mlist = mPersonList;
    }

    @Override
    public void add(Object object) {
        super.add(object);

    }

    static class LayoutHandler{
        TextView name, measurement,heart_rate,blood_pressure,timeStamp;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview = convertView;
        LayoutHandler layoutHandler;
        if(mview==null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview = layoutInflater.inflate(R.layout.person_row,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.name = (TextView)mview.findViewById(R.id.person_name);
            layoutHandler.measurement = (TextView)mview.findViewById(R.id.no_of_measurements);
            layoutHandler.heart_rate = (TextView)mview.findViewById(R.id.heatbeat);
            layoutHandler.blood_pressure = (TextView)mview.findViewById(R.id.bpm_upper_lower);
            layoutHandler.timeStamp = (TextView)mview.findViewById(R.id.time_stamp);
            mview.setTag(layoutHandler);
        }else {
            layoutHandler = (LayoutHandler) mview.getTag();
        }
        DataProvider dataProvider = (DataProvider)this.getItem(position);
        layoutHandler.name.setText(dataProvider.getName());
        layoutHandler.measurement.setText(String.valueOf(dataProvider.getNo_of_rows())+" measurements");
        layoutHandler.heart_rate.setText(dataProvider.getHr());
        layoutHandler.blood_pressure.setText(dataProvider.getBpr());
        layoutHandler.timeStamp.setText(dataProvider.getDataStamp());
        return mview;
    }


}