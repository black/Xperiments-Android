package com.example.writefile.SavedFiles;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAsync extends AsyncTask<List<FilePojo>,Integer,List<FilePojo>> {
    private ProgressBar bar;
    private Context context;
    private FileResult results = null;

    public FileAsync(Context context, FileResult results,ProgressBar bar) {
        this.bar = bar;
        this.context = context;
        this.results = results;
    }

    @Override
    protected List<FilePojo> doInBackground(List<FilePojo>... list) {
        List<FilePojo> mlist = new ArrayList<>();
        File dir = context.getFilesDir();
        File[] subFiles = dir.listFiles();

        if (subFiles != null)
        {
            for (File file : subFiles)
            {
                mlist.add(new FilePojo(file.getName(),file.length()/(1024 * 1024)+" MB"));
            }
        }
        return mlist;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<FilePojo> list) {
        super.onPostExecute(list);
        bar.setVisibility(View.INVISIBLE);
        results.processFinish(list);
    }

    @Override
    protected void onProgressUpdate(Integer... val) {
        super.onProgressUpdate(val);
        if (this.bar != null) {
            bar.setProgress(val[0]);
        }
    }
}
