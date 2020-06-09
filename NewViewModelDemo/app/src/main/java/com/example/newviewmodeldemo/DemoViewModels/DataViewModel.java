package com.example.newviewmodeldemo.DemoViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private MutableLiveData<Integer> tiger;
    private MutableLiveData<Integer> cat;
    private MutableLiveData<Integer> hippo;
    private MutableLiveData<Integer> dolphin;

    public DataViewModel(){
        this.tiger = new MutableLiveData<>();
        this.cat = new MutableLiveData<>();
        this.hippo = new MutableLiveData<>();
        this.dolphin = new MutableLiveData<>();
    }

    // activation type
    public void setTiger(Integer val){
        tiger.setValue(val);
    }
    public LiveData<Integer> getTiger(){
        return tiger;
    }

    // navigation type
    public void setCat(Integer val){
        cat.setValue(val);
    }
    public LiveData<Integer> getCat(){
        return cat;
    }

    // interface
    public void setHippo(Integer val){
        hippo.setValue(val);
    }
    public LiveData<Integer> getHippo(){
        return hippo;
    }

    // threshold
    public void setDolphin(Integer val){
        dolphin.setValue(val);
    }
    public LiveData<Integer> getDolphin(){
        return dolphin;
    }

}
