package com.example.asynctaskfileioexample;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBaseClass extends AsyncTask<String,Void,Void> {

    private JSONArray jsonArray;
    public AsyncResponse delegate = null;
    private final WeakReference<Context> ctx;

    AsyncBaseClass(Context context) {
        this.ctx = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            AssetManager manager = ctx.get().getAssets();
            InputStream file = manager.open(params[0]);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            String jsonString = new String(data);
            jsonArray = new JSONArray(jsonString);
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
