package com.example.demonavdrawer.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.demonavdrawer.MainActivity;
import com.example.demonavdrawer.R;

import java.util.Locale;

public class HomeFragment extends Fragment {
    private EditText textMsg;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textMsg = view.findViewById(R.id.textMsg);
        button = view.findViewById(R.id.playButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().speak(textMsg.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });

        ((MainActivity)getActivity()).settingsViewModel.getLanguage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "English":
                        MainActivity.getInstance().setLanguage(Locale.ENGLISH);
                        break;
                    case "French":
                        MainActivity.getInstance().setLanguage(Locale.FRENCH);
                        break;
                }
            }
        });
    }

}