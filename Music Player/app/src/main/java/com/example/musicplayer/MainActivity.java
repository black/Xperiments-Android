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
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MusicAdapter musicAdapter;
    private List<SongData> songsList;
    private ListView listView;
    private ProgressBar loader;

    //------Media Player ----------
    private static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView playButton, prevButton, nextButton;
    private SeekBar seekbarPayer;
    private TextView playerTime, songTitle;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------Music Player---------------
        mediaPlayer = new MediaPlayer();
        playButton = findViewById(R.id.playpause_player);
        prevButton = findViewById(R.id.prev_player);
        nextButton = findViewById(R.id.next_player);
        seekbarPayer = findViewById(R.id.seekbar_player);
        playerTime = findViewById(R.id.playertime);
        songTitle = findViewById(R.id.songname);

        loader = findViewById(R.id.loading);

        songTitle.setSelected(true);

        playButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

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
                pos = position;
                playMusicFile(songsList.get(position).getUri(), songsList.get(position).getTitle());
                updateItemInList();
            }
        });
        getAllSongs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllSongs();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playpause_player:
                play();
                break;
            case R.id.next_player:
                if (pos < songsList.size() - 1) {
                    pos++;
                } else {
                    pos = 0;
                }
                playMusicFile(songsList.get(pos).getUri(), songsList.get(pos).getTitle());
                break;
            case R.id.prev_player:
                if (pos > 0) {
                    pos--;
                } else {
                    pos = songsList.size() - 1;
                }
                playMusicFile(songsList.get(pos).getUri(), songsList.get(pos).getTitle());
                break;
        }
        updateItemInList();
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

    private void playMusicFile(String filePath,final String title) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        playButton.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                        seekbarPayer.setMax(mp.getDuration());
                        songTitle.setText(title);
                        updateMusicSeekbar();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playButton.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                        stopPlayer();
                    }
                });
                seekbarPayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(progress);
                            seekBar.setProgress(progress);
                            playerTime.setText(millisecondsToTime(mediaPlayer.getCurrentPosition()));
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        updateMusicSeekbar();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateItemInList(){
        listView.setSelection(pos);
        listView.setItemChecked(pos,true);
        musicAdapter.setSelectedPosition(pos);
        musicAdapter.notifyDataSetChanged();
        int h1 = listView.getHeight();
        listView.setSelectionFromTop(pos, h1/2);
    }

    private void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playButton.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);

        } else {
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        }
    }

    private void stopPlayer() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }

    private void updateMusicSeekbar() {
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateMusicSeekbar();
                    seekbarPayer.setProgress(mediaPlayer.getCurrentPosition());
                    playerTime.setText(millisecondsToTime(mediaPlayer.getCurrentPosition()));
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

