package com.example.writefile.SavedFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writefile.R;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ContentViewHolder> {
    private List<FilePojo> fileList;
    public FileAdapter(List<FilePojo> fileList) {
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.list_item,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String filename = fileList.get(position).getFilename();
        String filesize = fileList.get(position).getFilesize();

        holder.fname.setText(filename);
        holder.fsize.setText(filesize);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView fname;
        TextView fsize;
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fileName);
            fsize = itemView.findViewById(R.id.fileSize);
        }
    }
}
