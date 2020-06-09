package com.example.nurolink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import devin.com.linkmanager.LinkManager;
import devin.com.linkmanager.bean.Angle;
import devin.com.linkmanager.bean.DataType;
import devin.com.linkmanager.bean.Power;

public class MainActivity extends AppCompatActivity {


    private int delta,theta,lowAlpha,lowBete,lowGamma,highAlpha,highBete,middleGamma,fancyDegree,electric,version;
    private float yaw,bow,across;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Permission Check
       int PERMISSIONS_ALL = 1;
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_ALL);
        }

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE SUPPORTED ON THIS DEVICE", Toast.LENGTH_LONG).show();
        }

        try {
            LinkManager.getInstance().init(getApplication());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startDevice(View view) {
        start();
        view.setBackgroundColor(Color.GREEN);
    }

    private void start(){
        LinkManager.getInstance().setConnectiDeviceFirst(handler);
    }

    private void stop(){
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
                    //设备开始升级，等待设备关闭表示设备升级成功
                    ((TextView)findViewById(R.id.upgrade)).setText("UPGRADED");
                    break;
                case DataType.CODE_CONNECT_SUCCEED:
                    ((TextView)findViewById(R.id.connection)).setText("CONNECTED");
                    break;
                case DataType.CODE_CONNECT_FAIL:
                    ((TextView)findViewById(R.id.connection)).setText("FAILED");
                    //设备连接失败
                    break;
                case DataType.CODE_POOR_SIGNAL:
                    // msg.arg1表示信号值
                    ((TextView)findViewById(R.id.signal)).setText(msg.arg1+"");
                    break;
                case DataType.CODE_ATTENTION:
                    // msg.arg1表示attention
                    updateEEG("FOCUS",0,msg.arg1);
                    break;
                case DataType.CODE_MEDITATION:
                    // msg.arg1表示meditation
                    updateEEG("CALM",1,msg.arg1);
                    break;
                case DataType.CODE_RAW:
                    // msg.arg1表示raw值
                    break;
                case DataType.CODE_EEGPOWER:
                    Power power = (Power)msg.obj;
                    delta = power.delta;
                    theta = power.theta;
                    lowAlpha = power.lowAlpha;
                    highAlpha = power.highAlpha;
                    lowBete = power.lowBeta;
                    highBete = power.highBeta;
                    lowGamma = power.lowGamma;
                    middleGamma = power.middleGamma;
                    fancyDegree = power.fancyDegree; //喜好度 / Degree of preference
                    electric = power.electric; //电量值 / Electricity value
                    version = power.version;  //版本号 / version number
                    ((ProgressBar)findViewById(R.id.power)).setProgress(electric);
                    updateEEG("Delta",6,delta);
                    updateEEG("Theta",7,theta);
                    updateEEG("Low Alpha",8,lowAlpha);
                    updateEEG("High Alpha",9,highAlpha);
                    updateEEG("Low Beta",10,lowBete);
                    updateEEG("High Beta",11,highBete);
                    updateEEG("Low Gamma",12,lowGamma);
                    updateEEG("High Gamma",13,middleGamma);
                    updateEEG("APPRECIATION",2,fancyDegree);
                    break;
                case DataType.CODE_ANGLE:
                    Angle angle = (Angle) msg.obj;
                    yaw = angle.yaw; //偏航角度值 / Yaw angle value
                    bow = angle.bow;  //俯仰角度值 / Pitch angle value
                    across = angle.across;//横滚角度值 / Roll angle value

                    updateEEG("YAW",14,(int)yaw*100);
                    updateEEG("PITCH",15,(int)bow*100);
                    updateEEG("ROLL",16,(int)across*100);
                    break;
            }
        }
    };

    private void updateEEG(String title, int i,int progress){
        ((TextView)findViewById(getResources().getIdentifier("title"+i,"id",getPackageName()))).setText(title);
        ((ProgressBar)findViewById(getResources().getIdentifier("progress"+i,"id",getPackageName()))).setProgress(progress);
        ((TextView)findViewById(getResources().getIdentifier("value"+i,"id",getPackageName()))).setText(String.valueOf(progress));
    }
    @Override
    protected void onPause() {
        super.onPause();
        LinkManager.getInstance().close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LinkManager.getInstance().close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LinkManager.getInstance().close();
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

}
