package com.black.fragmenttoactivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstFragment extends Fragment {

    private static final int REQUEST_CODE = 11;
    private static final int RESULT_CODE = 11;


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);
        Button btn = view.findViewById(R.id.demoActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("val","First");
                intent.putExtra("resultCode", RESULT_CODE);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT REQUEST CODE ", resultCode + "");
        if (resultCode == RESULT_CODE) {
            String testResult = data.getStringExtra("result");
            Log.d("RESULTS",testResult);
            // TODO: Do something with your extra data
        }
    }

}
