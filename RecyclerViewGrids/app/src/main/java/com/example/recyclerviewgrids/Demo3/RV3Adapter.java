package com.example.recyclerviewgrids.Demo3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewgrids.R;

import java.util.List;

public class RV3Adapter extends RecyclerView.Adapter<RV3Adapter.ContentViewHolder> {

    private OnRVThreeItemClickListener onRVItemClickListener;
    private List<String> objectList;
    private Context context;
    public RV3Adapter(List<String> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public RV3Adapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.rv_item_3,parent,false);
        int height = parent.getMeasuredHeight() / 7;
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return new RV3Adapter.ContentViewHolder(view, onRVItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String title = objectList.get(position);
        if(objectList.get(position)=="del" || objectList.get(position)=="tts" || objectList.get(position)=="space"||objectList.get(position)=="done"){
            holder.titleView.setVisibility(View.GONE);
            holder.imageView.setImageResource(context.getResources().getIdentifier("key_"+objectList.get(position),"drawable",context.getPackageName()));
        }else{
            holder.titleView.setText(title);
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        ImageView imageView;
        View view;
        public ContentViewHolder(@NonNull View itemView,final OnRVThreeItemClickListener listener) {
            super(itemView);
            view = itemView;
            titleView = itemView.findViewById(R.id.rv_tile_3);
            imageView = itemView.findViewById(R.id.rv_img_3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onClickListener(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setOnRVItemClickListener(OnRVThreeItemClickListener rvItemClickListener){
        onRVItemClickListener = rvItemClickListener;
    }
}
