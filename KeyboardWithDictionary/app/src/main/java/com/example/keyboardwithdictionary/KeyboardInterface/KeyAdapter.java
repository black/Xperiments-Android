package com.example.keyboardwithdictionary.KeyboardInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keyboardwithdictionary.R;

import java.util.List;

public class KeyAdapter extends BaseAdapter {
    private Context context;
    private List<Keys> keysList;

    public KeyAdapter(Context context, List<Keys> keysList) {
        this.context = context;
        this.keysList = keysList;
    }

    @Override
    public int getCount() {
        return keysList.size();
    }

    @Override
    public Object getItem(int position) {
        return keysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.key, parent, false);
        }

//        convertView.setMinimumHeight(height);

        /* Get Views from inflated view */


        TextView title = convertView.findViewById(R.id.keyvalue);
        ImageView imgs = convertView.findViewById(R.id.keyimg);

        /* Set values to Views */
        if(keysList.get(position).getKey()=="del" || keysList.get(position).getKey()=="tts" || keysList.get(position).getKey()=="space"||keysList.get(position).getKey()=="done"){
            convertView.setBackgroundResource(R.drawable.key_btn_action);
            imgs.setImageResource(context.getResources().getIdentifier("key_"+keysList.get(position).getKey(),"drawable",context.getPackageName()));
            title.setVisibility(View.GONE);
        }else{
            convertView.setBackgroundResource(R.drawable.key_btn_normal);
            imgs.setVisibility(View.GONE);
            title.setText(keysList.get(position).getKey());
        }
        return convertView;
    }
}