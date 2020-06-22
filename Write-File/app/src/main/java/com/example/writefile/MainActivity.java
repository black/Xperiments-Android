package com.example.writefile;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import devin.com.linkmanager.LinkManager;
import devin.com.linkmanager.bean.Angle;
import devin.com.linkmanager.bean.DataType;
import devin.com.linkmanager.bean.Power;

public class MainActivity extends AppCompatActivity{
    public static final String TAG ="SENSOR";
    public static final String FILE_NAME = "link.csv";
    private String data;
    private TextView tv,ctv;
    private List<Signal> signalList = new ArrayList<>();
    private LineGraphSeries<DataPoint> mSeries = new LineGraphSeries<>();
    private double graph2LastXValue = 5d;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.loadedData);
        ctv= findViewById(R.id.connectStatus);
        progressBar = findViewById(R.id.readProgress);
        /*Permissions*/
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(permissionsListener)
                .check();

        /*EEG Sensor-----------------------*/
        try {
            LinkManager.getInstance().init(getApplication());
        }catch (Exception e){
            e.printStackTrace();
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.addSeries(mSeries);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
       // mSeries.appendData(new DataPoint(graph2LastXValue+=1d, data), true, 40);
    }

    MultiplePermissionsListener permissionsListener = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

        }
    };

    public void saveFile(View view){
        WriteAsync task = new WriteAsync(getApplicationContext(), new Results() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(MainActivity.this,output,Toast.LENGTH_LONG).show();
            }
        },signalList);
        task.setProgressBar(progressBar);
        task.execute();
    }

    public void loadFile(View view){
        ReadAsync task = new ReadAsync(getApplicationContext(), new Results() {
            @Override
            public void processFinish(String output) {
                tv.setText(output);
            }
        });
        task.setProgressBar(progressBar);
        task.execute();
    }

    private void appendToFile(String str) {
        File file = getFileStreamPath("data.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream writer = null;
        try {
            writer = openFileOutput(file.getName(), MODE_APPEND | MODE_WORLD_READABLE);
            writer.write(str.getBytes());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*NUROLINK-----------------*/
    public void startSensor(View view){
        startLink();
    }

    public void stopSensor(View view){
        stopLink();
    }
    private void startLink(){
        LinkManager.getInstance().setConnectiDeviceFirst(handler);
    }

    private void stopLink(){
        LinkManager.getInstance().close();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DataType.CODE_UP_SEND_START:
                    break;
                case DataType.CODE_UP_SEND_ING:
                    break;
                case DataType.CODE_UP_SEND_END:
                    //升级包发送完毕
                    break;
                case DataType.CODE_UP_SUCCEED:
                    Log.d("TESTING","0");
                    break;
                case DataType.CODE_CONNECT_SUCCEED:
                    ctv.setText("connected");
                    break;
                case DataType.CODE_CONNECT_FAIL:
                    ctv.setText("dis_connected");
                    break;
                case DataType.CODE_POOR_SIGNAL:
                    //eeg_signalstrength = 200-msg.arg1+55;
                    break;
                case DataType.CODE_ATTENTION:
                    long t1 = System.currentTimeMillis();
                    signalList.add(new Signal(t1, Signal.Type.ATTENTION,msg.arg1));
                    Log.d(TAG,"Attention:"+  msg.arg1);
                    break;
                case DataType.CODE_MEDITATION:
                    long t2 = System.currentTimeMillis();
                    signalList.add(new Signal(t2, Signal.Type.MEDITATION,msg.arg1));
                    Log.d(TAG,"Meditation:"+  msg.arg1);
                    break;
                case DataType.CODE_RAW:
                    long t3 = System.currentTimeMillis();
                    signalList.add(new Signal(t3, Signal.Type.RAW,msg.arg1));
                    Log.d(TAG,"Raw:"+  msg.arg1);
                    break;
                case DataType.CODE_EEGPOWER:
                    Power power = (Power)msg.obj;
                    long t4 = System.currentTimeMillis();
                    signalList.add(new Signal(t4, Signal.Type.LOALPHA,power.lowAlpha));
                    signalList.add(new Signal(t4, Signal.Type.HIALPHA,power.highAlpha));
                    signalList.add(new Signal(t4, Signal.Type.LOBETA,power.lowBeta));
                    signalList.add(new Signal(t4, Signal.Type.HIBETA,power.highBeta));
                    signalList.add(new Signal(t4, Signal.Type.LOGAMMA,power.lowGamma));
                    signalList.add(new Signal(t4, Signal.Type.MIDGAMMA,power.middleGamma));
                    signalList.add(new Signal(t4, Signal.Type.THETA,power.theta));
                    signalList.add(new Signal(t4, Signal.Type.DELTA,power.delta));
                    break;
                case DataType.CODE_ANGLE:
                    Angle angle = (Angle) msg.obj;
                    break;
            }
            invalidateOptionsMenu();
        }
    };

    /*---------------*/
    private void perform(){
        ArrayList<Signal> signalArray = new ArrayList<>();
        for (Signal signal : signalList) {
            if (signal.type == Signal.Type.RAW) {
                signalArray.add(signal);
            }
        }

        if(signalArray.size()<512){
            return;
        }

//        FFT transform = new FFT(512);
//        double x[] = new double[512];
//        double y[] = new double[512];
//        for(int i=0; i < 512; ++i) {
//            x[i] = signalArray.get(i).RAW;
//        }
//
//        transform.fft(x, y);
//        double scale = 512 * 512; // no_of_samples * frequency of data
//        for(int i=0; i < signalArray.size()/8; ++i){
//            mSeries.appendData(new DataPoint(graph2LastXValue+=1d, data), true, 40);
//        }
//        signalArray.clear();
//        Signals[] signalArray;
//        signalArray = new Signals[n];
//        int j=0;
//        for(Signals signal : signalsList){
//            signalArray
//        }
    }
}