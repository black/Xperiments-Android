package ca.nuro.viewmodelwithsharedpreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelClass extends ViewModel {
    private MutableLiveData<Integer> fishes;
    private MutableLiveData<Float> animals;

    public ViewModelClass(){
        this.fishes = new MutableLiveData<>();
        this.animals = new MutableLiveData<>();
    }

    // fishes
    public void setFishes(int val){
        fishes.setValue(val);
    }
    public LiveData<Integer> getFishes(){
        return fishes;
    }

    // animals
    public void setAnimals(float val){
        animals.setValue(val);
    }
    public LiveData<Float> getAnimals(){
        return animals;
    }

}
