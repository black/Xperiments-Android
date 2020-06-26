package com.example.writefile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteAsync extends AsyncTask<String,Integer,String> {
    private Signal signal;
    private ProgressBar bar;
    private Context context;
    private String FILE_NAME ="";
    private Results results = null;
    public WriteAsync (Context context,Results results,Signal signal,String FILE_NAME) {
        this.context = context;
        this.results = results;
        this.signal = signal;
        this.FILE_NAME = FILE_NAME;
    }

    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected String doInBackground(String... strings) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE|Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.write(signal.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        bar.setVisibility(View.INVISIBLE);
        results.processFinish("SAVED SUCCESSFULLY");
    }

    @Override
    protected void onProgressUpdate(Integer... val) {
        super.onProgressUpdate(val);
        if (this.bar != null) {
            bar.setProgress(val[0]);
        }
    }
}
