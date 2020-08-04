package com.example.demonavdrawer.Viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> language;
    private MutableLiveData<String> voice;
    public SettingsViewModel(){
        this.language = new MutableLiveData<>();
        this.voice = new MutableLiveData<>();
    }

    public void setLanguage(String val){
        language.setValue(val);
    }
    public LiveData<String> getLanguage(){
        return language;
    }

    public void setVoice(String val){
        voice.setValue(val);
    }
    public LiveData<String> getVoice(){
        return voice;
    }
}
