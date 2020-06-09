package com.example.musicplayer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MusicAdapter extends BaseAdapter{
    private Context context;
    private List<SongData> itemList;
    private int selected;

    public MusicAdapter(Context context, List<SongData> itemList) {
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

    public void setSelectedPosition(int position) {
        selected = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            v = inflater.inflate(R.layout.layout_song,null);
        }
        /* Get Views from inflated view */
        TextView title = v.findViewById(R.id.songTitle);
        TextView duration = v.findViewById(R.id.songDuration);
        ImageView cover = v.findViewById(R.id.cover);

        /* Set values to Views */
        title.setText(itemList.get(position).getTitle());
        duration.setText(itemList.get(position).getArtist());

        Glide.with(context)
                .load(itemList.get(position).getImg())
                .centerCrop()
                .into(cover);

        if(position == selected) {
            v.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            title.setTextColor(context.getResources().getColor(R.color.white));
            duration.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            v.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            title.setTextColor(context.getResources().getColor(R.color.black));
            duration.setTextColor(context.getResources().getColor(R.color.black));
        }

        return v;
    }

    private String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }
        return minutes + ":" + secs;
    }
}
