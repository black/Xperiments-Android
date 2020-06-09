package com.example.kuro.bloodpressure.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuro.bloodpressure.R;

public class MeasureFragment extends Fragment {

    ImageView img;
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);

    public MeasureFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measure_layout, container, false);
        img = (ImageView) view.findViewById(R.id.phone);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        getActivity().registerReceiver(bcReceived, intentFilter);
        super.onResume();
    }

    //
    private BroadcastReceiver bcReceived = new BroadcastReceiver() {
        public void onReceive(Context ctx, Intent intent) {
//            AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d("TAG", "NOT CONNECTED");
                        img.setImageResource(R.drawable.phone_disconnected);
                        TextView button2 = (TextView) getActivity().findViewById(R.id.start_button);
                        button2.setVisibility(View.GONE);
                        break;
                    case 1:
                        Log.d("TAG", "CONNECTED");
                        img.setImageResource(R.drawable.phone_connected);
                        TextView button1 = (TextView) getActivity().findViewById(R.id.start_button);
                        button1.setVisibility(View.VISIBLE);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("TAG", "CLICKED");
                                Fragment fragment = new PeopleFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.peopleFragment, fragment).commit();
                            }
                        });
                        break;
                    default:
                        Log.d("TAG", "I have no idea what the headset state is");
                }
            }
        }
    };

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(bcReceived);
        super.onPause();
    }


}
