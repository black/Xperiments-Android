package com.experiments.recyclerviewmultiview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter {

    List<String> dummylist;

    public RVAdapter(List<String> dummylist) {
        this.dummylist = dummylist;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==1){
            view = layoutInflater.inflate(R.layout.chat_left,parent,false);
            return new LeftChat(view);
        }else{
            view = layoutInflater.inflate(R.layout.chat_right,parent,false);
            return new RightChat(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position%2==0){
            LeftChat viewLeft = (LeftChat) holder;
            viewLeft.msgView.setText(dummylist.get(position));
            viewLeft.timeView.setText(position+"");
        }else{
            RightChat viewRight = (RightChat) holder;
            viewRight.msgView.setText(dummylist.get(position));
            viewRight.timeView.setText(position+"");
        }
    }

    @Override
    public int getItemCount() {
        return dummylist.size();
    }

    class LeftChat extends RecyclerView.ViewHolder{
        TextView msgView,timeView;
        public LeftChat(@NonNull View itemView) {
            super(itemView);
            msgView = itemView.findViewById(R.id.tv_1);
            timeView = itemView.findViewById(R.id.tm_1);
        }
    }

    class RightChat extends RecyclerView.ViewHolder{
        TextView msgView,timeView;
        public RightChat(@NonNull View itemView) {
            super(itemView);
            msgView = itemView.findViewById(R.id.tv_2);
            timeView = itemView.findViewById(R.id.tm_2);
        }
    }
}