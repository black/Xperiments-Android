package com.black.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomView extends RecyclerView.Adapter<CustomView.ContentViewHolder> {
    private List<DataObject> objectList;

    public CustomView(List<DataObject> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.grid_item,parent,false);
        return new ContentViewHolder(view);
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
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImage);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
        }
    }
}
