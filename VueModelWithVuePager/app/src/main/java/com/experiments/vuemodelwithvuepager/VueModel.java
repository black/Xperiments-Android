package com.experiments.vuemodelwithvuepager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VueModel extends ViewModel {
    private MutableLiveData<Integer> level;
    private MutableLiveData<String[]> data;
    public VueModel() {
        this.data = new MutableLiveData<>();
        this.level = new MutableLiveData<>();
    }

    public void setLevel(Integer val){
        level.setValue(val);
    }
    public LiveData<Integer> getLevel(){
        return level;
    }

    public void setData(String[] val){
        data.setValue(val);
    }
    public LiveData<String[]> getData(){
        return data;
    }
}
