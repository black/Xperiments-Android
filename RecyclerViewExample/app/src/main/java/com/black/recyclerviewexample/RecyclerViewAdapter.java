package com.black.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContentViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<DataObject> objectList;

    public RecyclerViewAdapter(List<DataObject> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.grid_item,parent,false);
        return new ContentViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String title = objectList.get(position).getTitle();
        String description = objectList.get(position).getDescription();
        String imgURL = objectList.get(position).getImgURL();

        holder.titleView.setText(title);
        holder.descriptionView.setText(title);
       // holder.imageView.setImageResource(imgURL);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleView;
        TextView descriptionView;
        public ContentViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImage);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.OnitemClick(pos);
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        onItemClickListener = clickListener;
    }
}
