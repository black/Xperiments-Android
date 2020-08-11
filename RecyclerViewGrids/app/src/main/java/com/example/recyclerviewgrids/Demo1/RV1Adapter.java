package com.example.recyclerviewgrids.Demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewgrids.R;

import java.util.List;

public class RV1Adapter extends RecyclerView.Adapter<RV1Adapter.ContentViewHolder> {

    private OnRVOneItemClickListener onRVItemClickListener;
    private List<String> objectList;
    private Context context;
    public RV1Adapter(List<String> objectList,Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.rv_item_1,parent,false);
        return new ContentViewHolder(view, onRVItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String title = objectList.get(position);
        holder.titleView.setText(title);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        public ContentViewHolder(@NonNull View itemView,final OnRVOneItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.rv_tile_1);
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

    public void setOnRVItemClickListener(OnRVOneItemClickListener rvItemClickListener){
        onRVItemClickListener = rvItemClickListener;
    }
}