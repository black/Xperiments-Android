package com.example.writefile;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.writefile.MainActivity.FILE_NAME;

public class ReadAsync extends AsyncTask<String,Integer,String> {
    private ProgressBar bar;
    private Context context;
    private Results results = null;
    public ReadAsync (Context context,Results results) {
        this.context = context;
        this.results = results;
    }
    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected String doInBackground(String... strings) {
        FileInputStream fis = null;
        try {
            fis =  this.context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text=br.readLine())!=null){
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /*
         FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text=br.readLine())!=null){
                sb.append(text).append("\n");
            }
            tv.setText(sb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        */
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
        bar.setVisibility(View.GONE);
        results.processFinish(s);
    }

    //    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//        mProgressDialog.dismiss();
//
//        mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        setListAdapter(new RowIconAdapter(getApplicationContext(), R.layout.listrow, R.id.row_label, movies));
//    }

    @Override
    protected void onProgressUpdate(Integer... val) {
        super.onProgressUpdate(val);
        if (this.bar != null) {
            bar.setProgress(val[0]);
        }
    }
}
