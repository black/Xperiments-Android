package com.example.writefile.SavedFiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.writefile.R;
import com.example.writefile.ReadAsync;
import com.example.writefile.Results;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesActivity extends AppCompatActivity {
    private List<FilePojo> fileList = new ArrayList<>();
    private FileAdapter fileAdapter;
    private ProgressBar fileloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        fileloader = findViewById(R.id.fileloader);
        RecyclerView listView = findViewById(R.id.fileList);
        fileAdapter = new FileAdapter(fileList);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(fileAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllFiles();
    }

    private void getAllFiles(){
        FileAsync fileAsync = new FileAsync(getApplicationContext(), new FileResult() {
            @Override
            public void processFinish(List<FilePojo> output) {
                Log.d("GGGGG",output.toString());
                fileList.clear();
                fileList.addAll(output);
                fileAdapter.notifyDataSetChanged();
            }
        },fileloader);
        fileAsync.execute();
    }

    public void shareFile(String FILE_NAME) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("*/*");
        String auth = getApplicationContext().getPackageName()+".FileProvider";
        Uri uri = FileProvider.getUriForFile(this,auth,new File(getFilesDir(),FILE_NAME));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(sharingIntent, "Share SENSOR Data"));
    }
}