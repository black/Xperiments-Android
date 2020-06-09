package com.black.jsonarraywriter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer, senGyroscope, senProximity;
    private JSONArray jsonArrayBufferOne = new JSONArray();
    private JSONArray jsonArrayBufferTwo = new JSONArray();
    private double session = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permission Check
        int PERMISSIONS_ALL = 1;
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        };
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_ALL);
        }

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senProximity = senSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senProximity, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        session = Math.random();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        JSONObject obj = new JSONObject();
        try {
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                obj.put("accelerometer", sensorEvent.values[0] + "," + sensorEvent.values[1] + "," + sensorEvent.values[2]);
            }else obj.put("accelerometer", "0,0,0");
            if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
                obj.put("gyroscope", sensorEvent.values[0] + "," + sensorEvent.values[1] + "," + sensorEvent.values[2]);
            }else obj.put("gyroscope", "0,0,0");
            if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
                obj.put("proximity", sensorEvent.values[0]);
            }else obj.put("proximity", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArrayBufferOne.put(obj);
        Log.d("JSONOBJECT",obj.toString()+ " : Length" +jsonArrayBufferOne.length());
        if(jsonArrayBufferOne.length()>50){
            Log.d("Message Starting","Trying to write data");
 //           writeToFile(jsonArrayBufferOne.toString(),this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senProximity, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

/*    private void writeToFile(String data, Context context) {
        String destPath = context.getExternalFilesDir(null)+"/JSON_ARRAY";
        Log.d("Message FolderPath",destPath);
        File file = new File(destPath,"sensor_data"+session+".txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
            Log.d("Message Success", "Sucessfully Done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("Message Not Found", "File not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Message Not Found", "File not found");
        }
    }*/

    /*----------- Custom Functions------- */
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
