package com.kuro.lockscreenquote;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONParser extends AsyncTask<String, String, String> {

    private View quote;
    private View author;
    static InputStream in = null;
    JSONArray array;
    static JSONObject json;
    static String result = "";

    public JSONParser(View quote, View author) {
        this.quote = quote;
        this.author = author;
    }

    @Override
    protected String doInBackground(String... urls) {
        getJSONFromUrl(urls[0]);
        Log.e("getting url",urls[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            json = array.getJSONObject(0);
            try {
                TextView quoteText = (TextView) quote.findViewById(R.id.quote);
                TextView authText = (TextView) author.findViewById(R.id.author);
                String title =  json.getString("content");
                String result = title.replaceAll("[-+^:,<p>,/]","");
                quoteText.setText(json.getString("title"));
                authText.setText(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

    }

    public String getJSONFromUrl(String url) {
        Log.e("received",url);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL _url = new URL(url);
            urlConnection = (HttpURLConnection) _url.openConnection();
            //urlConnection.setDoOutput(false);
            urlConnection.connect();
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                in = urlConnection.getErrorStream();
            else
                in = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            result = buffer.toString();
            try {
                array = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("results",result);
            return result;
        } catch (MalformedURLException e) {
            Log.e("JSON Parser", "Error due to a malformed URL " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("JSON Parser", "IO error " + e.toString());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                try {
                    if (reader != null) reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}