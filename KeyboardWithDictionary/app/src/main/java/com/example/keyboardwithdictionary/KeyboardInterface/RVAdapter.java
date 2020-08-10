package com.example.keyboardwithdictionary.KeyboardInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keyboardwithdictionary.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ContentViewHolder> {

    private OnRVItemClickListener onRVItemClickListener;
    private List<Keys> objectList;
    private Context context;
    public RVAdapter(List<Keys> objectList,Context context) {
        this.objectList = objectList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.key,parent,false);
        int height = parent.getMeasuredHeight() / 5;
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return new ContentViewHolder(view,onRVItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {

        String title = objectList.get(position).getKey();
        /* Set values to Views */
        if(objectList.get(position).getKey()=="del" || objectList.get(position).getKey()=="tts" || objectList.get(position).getKey()=="space"||objectList.get(position).getKey()=="done"){
           //convertView.setBackgroundResource(R.drawable.key_btn_action);
            holder.view.setBackgroundResource(R.drawable.key_btn_action);
            holder.titleView.setVisibility(View.GONE);
            holder.imageView.setImageResource(context.getResources().getIdentifier("key_"+objectList.get(position).getKey(),"drawable",context.getPackageName()));
        }else{
           // convertView.setBackgroundResource(R.drawable.key_btn_normal);
            holder.imageView.setVisibility(View.GONE);
            holder.titleView.setText(title);

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
        public ContentViewHolder(@NonNull View itemView,final OnRVItemClickListener listener) {
            super(itemView);
            view = itemView;
            titleView = itemView.findViewById(R.id.keyvalue);
            imageView = itemView.findViewById(R.id.keyimg);
            itemView.setBackgroundResource(R.drawable.key_btn_normal);
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