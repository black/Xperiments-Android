package com.example.viewpagerwithdynamicviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VueModel extends ViewModel {
    private MutableLiveData<PagerModel> bool;

    public VueModel(){
        this.bool = new MutableLiveData<>();
    }

    // threshold
    public void setPosition(PagerModel val){
        bool.setValue(val);
    }
    public LiveData<PagerModel> getPosition(){
        return bool;
    }
}
