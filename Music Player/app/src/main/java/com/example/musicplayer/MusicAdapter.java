package com.example.musicplayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MusicAdapter extends BaseAdapter{
    private Context context;
    private List<SongData> itemList;
    private int selected;
    private int val;
    private AnimationDrawable animdraw;

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
    public void updateSeekbar(int val) {
        val = val;
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
        TextView subtitle = v.findViewById(R.id.songDuration);
        SeekBar seekbar = v.findViewById(R.id.seekbar_player);
        ImageView cover = v.findViewById(R.id.cover);

        /* Set values to Views */
        title.setText(itemList.get(position).getTitle());
        subtitle.setText(itemList.get(position).getArtist());

        Glide.with(context)
                .load(itemList.get(position).getImg())
                .centerCrop()
                .into(cover);

        if(position == selected) {
            v.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            title.setTextColor(context.getResources().getColor(R.color.white));
            subtitle.setTextColor(context.getResources().getColor(R.color.white));
            seekbar.setVisibility(View.VISIBLE);
            seekbar.setProgress(val);
            cover.setBackgroundResource(R.drawable.song);
            animdraw = (AnimationDrawable)cover.getBackground();
            animdraw.start();
        }else{
            v.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            title.setTextColor(context.getResources().getColor(R.color.black));
            subtitle.setTextColor(context.getResources().getColor(R.color.black));
            seekbar.setVisibility(View.GONE);
            cover.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }

        return v;
    }

}
