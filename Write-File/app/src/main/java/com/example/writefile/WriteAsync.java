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
    private List<Signal> signalList;
    private ProgressBar bar;
    private Context context;
    private String FILE_NAME ="";
    private Results results = null;
    public WriteAsync (Context context,Results results,List<Signal> signalList,String FILE_NAME) {
        this.context = context;
        this.results = results;
        this.signalList = signalList;
        this.FILE_NAME = FILE_NAME;
    }

    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected String doInBackground(String... strings) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            for(int i = 0; i< signalList.size(); i++){
                Signal s = signalList.get(i);
                fos.write(s.toString().getBytes());
            }
            Log.d("WRITESTATUS","Success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("WRITESTATUS","failed");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("WRITESTATUS","failed");
        }finally {
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
