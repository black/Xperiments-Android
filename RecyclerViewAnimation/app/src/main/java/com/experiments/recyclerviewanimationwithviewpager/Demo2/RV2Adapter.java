package com.experiments.recyclerviewanimationwithviewpager.Demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.experiments.recyclerviewanimationwithviewpager.R;

import java.util.List;

public class RV2Adapter extends RecyclerView.Adapter<RV2Adapter.ContentViewHolder> {

    private OnRVTwoItemClickListener onRVItemClickListener;
    private List<String> objectList;
    private Context context;
    public RV2Adapter(List<String> objectList, Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.rv_item_2,parent,false);
        int height = parent.getMeasuredHeight() / 7;
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
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
        View view;
        public ContentViewHolder(@NonNull View itemView,final OnRVTwoItemClickListener listener) {
            super(itemView);
            view = itemView;
            titleView = itemView.findViewById(R.id.rv_tile_2);
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

    public void setOnRVItemClickListener(OnRVTwoItemClickListener rvItemClickListener){
        onRVItemClickListener = rvItemClickListener;
    }
}