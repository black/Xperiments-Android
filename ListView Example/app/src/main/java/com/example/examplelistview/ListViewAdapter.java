package com.example.examplelistview;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ItemDataModel> itemList;

    public ListViewAdapter(Context context, List<ItemDataModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            v = inflater.inflate(R.layout.item,null);
        }
        /* Get Views from inflated view */
        TextView title = v.findViewById(R.id.title);
        TextView description = v.findViewById(R.id.description);
        ProgressBar progressBar = v.findViewById(R.id.progressBar);

        /* Set values to Views */
        title.setText(itemList.get(position).getTitle());
        description.setText(itemList.get(position).getDescription());
        progressBar.setProgress(itemList.get(position).getProgress());

        return v;
    }
}
