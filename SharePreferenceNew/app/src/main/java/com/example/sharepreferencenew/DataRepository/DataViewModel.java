package com.example.sharepreferencenew.DataRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {
    private MutableLiveData<Float> speed;
    private MutableLiveData<Integer> level;

    public DataViewModel(){
        this.level = new MutableLiveData<>();
        this.speed = new MutableLiveData<>();
    }

    // Level
    public void setLevel(int val){
        this.level.setValue(val);
    }
    public LiveData<Integer> getLevel(){
        return level;
    }


    // speed
    public void setSpeed(Float val){
        this.speed.setValue(val);
    }
    public LiveData<Float> getSpeed(){
        return speed;
    }

}