package com.experiment.androiddatausb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    UsbInterface usbInterface;
    UsbEndpoint usbEndpointIN, usbEndpointOUT;
    UsbDeviceConnection usbDeviceConnection;
    UsbDevice deviceFound = null;
    USB USB;

    ArrayList<String> listInterface;
    ArrayList<UsbInterface> listUsbInterface;
    ArrayList<String> listEndPoint;
    ArrayList<UsbEndpoint> listUsbEndpoint;

    private static final int targetVendorID = 8260;
    private static final int ProductID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private boolean connectUsb() {
        boolean result = false;
        switch(checkDeviceInfo())
        {
            case ProductID:
                result = StartUSB();
                break;
        }
        return result;
    }

    private int checkDeviceInfo() {

        deviceFound = null;

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();

            if(device.getVendorId()==targetVendorID) {
                deviceFound = device;

                switch(device.getProductId()) {
                    case ProductID:
                        GetInterface(deviceFound);
                        GetEndpoint(deviceFound);
                        return ProductID;

                    default:
                        Toast.makeText(this, getString(R.string.err_unknow_device), Toast.LENGTH_LONG).show();
                        break;
                }
                return -1;
            }
        }
        return -1;
    }

    private void GetInterface(UsbDevice d) {
        listInterface = new ArrayList<String>();
        listUsbInterface = new ArrayList<UsbInterface>();
        for(int i=0; i<d.getInterfaceCount(); i++){
            UsbInterface usbif = d.getInterface(i);
            listInterface.add(usbif.toString());
            listUsbInterface.add(usbif);
        }

        if(d.getInterfaceCount() > 0)
        {
            usbInterface = listUsbInterface.get(1);
        }
        else usbInterface = null;
    }

    private void GetEndpoint(UsbDevice d) {
        int EndpointCount = usbInterface.getEndpointCount();
        listEndPoint = new ArrayList<String>();
        listUsbEndpoint = new ArrayList<UsbEndpoint>();

        for(int i=0; i<usbInterface.getEndpointCount(); i++) {
            UsbEndpoint usbEP = usbInterface.getEndpoint(i);
            listEndPoint.add(usbEP.toString());
            listUsbEndpoint.add(usbEP);
        }

        // deixar fixo para TxBlock USB
        if(EndpointCount > 0) {
            usbEndpointIN = usbInterface.getEndpoint(0);
            usbEndpointOUT = usbInterface.getEndpoint(1);
        }
        else {
            usbEndpointIN = null;
            usbEndpointOUT = null;
        }
    }

    private boolean StartUSB() {
        boolean result = false;
        UsbDevice deviceToRead = deviceFound;
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        Boolean permitToRead = manager.hasPermission(deviceToRead);

        if(permitToRead) {
            result = OpenDevice(deviceToRead);
        }else {
            Toast.makeText(this,
                    getString(R.string.err_no_permission) + permitToRead,
                    Toast.LENGTH_LONG).show();
        }

        return result;
    }

    private boolean OpenDevice(UsbDevice device){

        boolean forceClaim = true;

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        usbDeviceConnection = manager.openDevice(device);

        if(usbDeviceConnection != null){
            usbDeviceConnection.claimInterface(usbInterface, forceClaim);
            return true;
        }else{
            Toast.makeText(this,
                    getString(R.string.err_no_open_device),
                    Toast.LENGTH_LONG).show();
        }

        return false;
    }
}