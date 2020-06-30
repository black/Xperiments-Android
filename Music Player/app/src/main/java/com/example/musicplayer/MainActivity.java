package com.example.musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.gauravk.audiovisualizer.visualizer.BlobVisualizer;
import com.gauravk.audiovisualizer.visualizer.WaveVisualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private MusicAdapter musicAdapter;
    private List<SongData> songsList;
    private ListView listView;
    private ProgressBar loader;

    //------Media Player ----------
    private static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------Music Player---------------
        mediaPlayer = new MediaPlayer();
        loader = findViewById(R.id.loading);

        //Permission Check
        int PERMISSIONS_ALL = 1;
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
        };
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_ALL);
        }

        songsList = new ArrayList<>();
        musicAdapter = new MusicAdapter(this, songsList);
        listView = findViewById(R.id.songList);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playPause(position,view);
            }
        });
        getAllSongs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllSongs();
    }

    private void getAllSongs() {
       ReadAsync task = new ReadAsync(getApplicationContext(), new Results() {
           @Override
           public void processFinish(List<SongData> output) {
               songsList.clear();
               songsList.addAll(output);
               musicAdapter.notifyDataSetChanged();
           }
       });
        task.setProgressBar(loader);
        task.execute();
    }

    /*---- Player code -------------*/
    public void playPause(int pos,View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        } else{
            playMusicFile(songsList.get(pos).getUri(), songsList.get(pos).getTitle());
        }
        updateItemInList(pos);
    }



    private void playMusicFile(String filePath,String title) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        updateMusicSeekbar();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateItemInList(int pos){
        musicAdapter.setSelectedPosition(pos);
        musicAdapter.notifyDataSetChanged();
    }


   /* private void stopPlayer() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }*/

    private void updateMusicSeekbar() {
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateMusicSeekbar();
                    musicAdapter.updateSeekbar(mediaPlayer.getCurrentPosition());
                    musicAdapter.notifyDataSetChanged();
                    //seekbarPayer.setProgress(mediaPlayer.getCurrentPosition());
//                    playerTime.setText(millisecondsToTime(mediaPlayer.getCurrentPosition()));
                }
            };
            handler.postDelayed(runnable, 1000);
        } else handler.removeCallbacks(runnable);
    }

    private String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }
        return minutes + ":" + secs;
    }

    public void stopPlayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    /*Check Permission*/
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

