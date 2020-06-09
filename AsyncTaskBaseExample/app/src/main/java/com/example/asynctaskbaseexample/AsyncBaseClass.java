package com.example.asynctaskbaseexample;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBaseClass extends AsyncTask<String,Void,Void> {

    private JSONArray jsonArray;
    public AsyncResponse delegate = null;

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonDataAsString="";
            String line = "";
            while (line!=null){
                line = bufferedReader.readLine();
                jsonDataAsString+=line;
            }
            jsonArray = new JSONArray(jsonDataAsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.processPending(true);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.processFinish(this.jsonArray);
        delegate.processPending(false);
    }
}
