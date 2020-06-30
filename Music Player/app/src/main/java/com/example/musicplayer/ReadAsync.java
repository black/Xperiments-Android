package com.example.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ReadAsync extends AsyncTask<List<SongData>,Integer,List<SongData>> {
    private ProgressBar bar;
    private Context context;
    private Results results = null;
    public ReadAsync(Context context, Results results) {
        this.context = context;
        this.results = results;
    }
    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected List<SongData> doInBackground(List<SongData>... lists) {
        List<SongData> songsList = new ArrayList<>();
        Uri songURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor songCursor = context.getContentResolver().query(songURI, null, selection, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songUri = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songCover = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
            do {
                String title = songCursor.getString(songTitle);
                String artist = songCursor.getString(songArtist);
                String uri = songCursor.getString(songUri);
                String img = songCursor.getString(songCover);
                songsList.add(new SongData(title, artist, uri, img));
            } while (songCursor.moveToNext());
            songCursor.close();
        }
       return songsList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<SongData> list) {
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
