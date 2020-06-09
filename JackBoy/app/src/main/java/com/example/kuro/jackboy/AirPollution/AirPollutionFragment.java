package com.example.kuro.jackboy.AirPollution;


import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kuro.jackboy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirPollutionFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    double latitude = 0, longitude = 0;
    String address_final="";
    TextView textView;

    public AirPollutionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_air_pollution, container, false);
        final TextView buttonStart = (TextView) rootView.findViewById(R.id.startAirPollution);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.air_pollution_progressbar);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView inProgress = (TextView) rootView.findViewById(R.id.inprogresstext);
                inProgress.setVisibility(View.VISIBLE);
                buttonStart.setVisibility(View.GONE);
                progressBar.setProgress(45);
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Add a marker in Sydney and move the camera
        gps = new GPSTracker(getContext());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        address_final = getCompleteAddressString(latitude, longitude);
        textView = (TextView) rootView.findViewById(R.id.location_address);
        textView.setText(address_final);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}
