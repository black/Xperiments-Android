package com.example.keyboardwithdictionary.KeyboardInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keyboardwithdictionary.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ContentViewHolder> {

    private OnRVItemClickListener onRVItemClickListener;
    private List<Keys> objectList;
    public RVAdapter(List<Keys> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.key,parent,false);
        return new ContentViewHolder(view,onRVItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String title = objectList.get(position).getKey();
        holder.titleView.setText(title);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        public ContentViewHolder(@NonNull View itemView,final OnRVItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.keyvalue);
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

    public void setOnRVItemClickListener(OnRVItemClickListener rvItemClickListener){
        onRVItemClickListener = rvItemClickListener;
    }
}