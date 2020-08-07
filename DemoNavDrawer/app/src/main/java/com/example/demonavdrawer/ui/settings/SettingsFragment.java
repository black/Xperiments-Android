package com.example.demonavdrawer.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demonavdrawer.MainActivity;
import com.example.demonavdrawer.R;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RadioGroup setLanguage = view.findViewById(R.id.selectLanguage);
        setLanguage.check(R.id.english);
        setLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.english:
                        ((MainActivity)getActivity()).settingsViewModel.setLanguage("English");
                        break;
                    case R.id.french:
                        ((MainActivity)getActivity()).settingsViewModel.setLanguage("French");
                        break;
                }
            }
        });
    }
}