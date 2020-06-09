package com.example.kuro.wifiapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    public  WifiManager wifiManager;
    public String ssidName = "DefaultWifiName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSpot();
    }

    public void createSpot(){
        wifiManager = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
        final EditText editText = (EditText)findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SSID NAME","Clicked");
                ssidName = editText.getText().toString();
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
                Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();
                boolean methodFound = false;
                for (Method method: wmMethods) {
                    if (method.getName().equals("setWifiApEnabled")) {
                        methodFound = true;
                        WifiConfiguration netConfig = new WifiConfiguration();
                        netConfig.SSID = ssidName;
                        netConfig.preSharedKey="DataAnywhere";
                        netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                        netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                        try {
                            boolean apstatus = (Boolean) method.invoke(wifiManager, netConfig, true);

                            for (Method isWifiApEnabledmethod: wmMethods) {
                                if (isWifiApEnabledmethod.getName().equals("isWifiApEnabled")) {
                                    while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {};
                                    for (Method method1: wmMethods) {
                                        if (method1.getName().equals("getWifiApState")) {
                                            int apstate;
                                            apstate = (Integer) method1.invoke(wifiManager);
                                            Log.i(this.getClass().toString(), "Apstate ::: " + apstate);
                                            Log.i("THIS ", " LEVEL " );
                                        }
                                    }
                                }
                            }
                            if (apstatus) {
                                Log.d("Splash Activity", "Access Point created");
                            } else {
                                Log.d("Splash Activity", "Access Point creation failed");
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!methodFound) {
                    Log.d("Splash Activity",
                            "cannot configure an access point");
                }
            }
        });
    }

    /**
     * Switching On data
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setMobileDataEnabled(Context context, boolean enabled) {
        Log.i("NetworkUtil", "Mobile data enabling" + enabled);
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            Class conmanClass = Class.forName(conman.getClass().getName());
            final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
            connectivityManagerField.setAccessible(true);
            final Object connectivityManager = connectivityManagerField.get(conman);
            final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());

            final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}