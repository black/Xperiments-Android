package com.example.graphviewexample;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer,senGyroscope,senProximity;

    private LineGraphSeries<DataPoint> lineACCSeriesX,lineACCSeriesY,lineACCSeriesZ;
    private LineGraphSeries<DataPoint> lineGYROSeriesX,lineGYROSeriesY,lineGYROSeriesZ;
    private LineGraphSeries<DataPoint> linePROXYSeries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView lineACCGraph = findViewById(R.id.sensorACCLine);
        lineACCSeriesX = new LineGraphSeries<>();
        lineACCSeriesY = new LineGraphSeries<>();
        lineACCSeriesZ = new LineGraphSeries<>();
        lineACCSeriesX.setColor(Color.RED);
        lineACCSeriesY.setColor(Color.GREEN);
        lineACCSeriesZ.setColor(Color.BLUE);
        lineACCGraph.addSeries(lineACCSeriesX);
        lineACCGraph.addSeries(lineACCSeriesY);
        lineACCGraph.addSeries(lineACCSeriesZ);
        lineACCGraph.getViewport().setXAxisBoundsManual(true);
        lineACCGraph.getViewport().setMinX(0);
        lineACCGraph.getViewport().setMaxX(40);

        GraphView lineGYROGraph = findViewById(R.id.sensorGYROLine);
        lineGYROSeriesX = new LineGraphSeries<>();
        lineGYROSeriesY = new LineGraphSeries<>();
        lineGYROSeriesZ = new LineGraphSeries<>();
        lineGYROSeriesX.setColor(Color.RED);
        lineGYROSeriesY.setColor(Color.GREEN);
        lineGYROSeriesZ.setColor(Color.BLUE);
        lineGYROGraph.addSeries(lineGYROSeriesX);
        lineGYROGraph.addSeries(lineGYROSeriesY);
        lineGYROGraph.addSeries(lineGYROSeriesZ);
        lineGYROGraph.getViewport().setXAxisBoundsManual(true);
        lineGYROGraph.getViewport().setMinX(0);
        lineGYROGraph.getViewport().setMaxX(40);

        GraphView linePROXYGraph = findViewById(R.id.sensorPROXYLine);
        linePROXYSeries = new LineGraphSeries<>();
        linePROXYSeries.setColor(Color.MAGENTA);
        linePROXYGraph.addSeries(linePROXYSeries);
        linePROXYGraph.getViewport().setXAxisBoundsManual(true);
        linePROXYGraph.getViewport().setMinX(0);
        linePROXYGraph.getViewport().setMaxX(40);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senProximity = senSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        senSensorManager.registerListener(this, senProximity , SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    private int i=0,j=0,k=0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int pos = i++;
            lineACCSeriesX.appendData(new DataPoint(pos, sensorEvent.values[0]), true, 40);
            lineACCSeriesY.appendData(new DataPoint(pos, sensorEvent.values[1]), true, 40);
            lineACCSeriesZ.appendData(new DataPoint(pos, sensorEvent.values[2]), true, 40);
        }

        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            int pos = j++;
            lineGYROSeriesX.appendData(new DataPoint(pos, sensorEvent.values[0]), true, 40);
            lineGYROSeriesY.appendData(new DataPoint(pos, sensorEvent.values[1]), true, 40);
            lineGYROSeriesZ.appendData(new DataPoint(pos, sensorEvent.values[2]), true, 40);
        }

        if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
            int pos = k++;
            Log.d("PROXIMITY",sensorEvent.values[0]+"");
            linePROXYSeries.appendData(new DataPoint(pos, sensorEvent.values[0]), true, 40);
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
}
