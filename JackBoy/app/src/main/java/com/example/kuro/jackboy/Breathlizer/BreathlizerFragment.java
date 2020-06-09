package com.example.kuro.jackboy.Breathlizer;


import android.animation.ObjectAnimator;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kuro.jackboy.AirPollution.GPSTracker;
import com.example.kuro.jackboy.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreathlizerFragment extends Fragment {

    private GoogleMap mMap;
    private GPSTracker gps;
    double latitude = 0, longitude = 0;
    String address_final ="";
    TextView mtextView;

    public BreathlizerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_breathlizer, container, false);
        final ImageView startButton = (ImageView) rootView.findViewById(R.id.startBreathlizer);
        final TextView textView = (TextView) rootView.findViewById(R.id.values_breathlizer);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                for(int val= 0; val < 101; val++) {
                    progressBar.setProgress(val);
                    textView.setText(String.valueOf(val));
                }
            }
        });


        gps = new GPSTracker(getContext());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        address_final = getCompleteAddressString(latitude, longitude);
        mtextView = (TextView) rootView.findViewById(R.id.locationaddress);
        mtextView.setText(address_final);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
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
