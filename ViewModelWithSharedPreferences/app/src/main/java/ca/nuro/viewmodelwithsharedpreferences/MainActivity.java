package ca.nuro.viewmodelwithsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewModelClass viewModelClass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private int fish;
    private float animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("NUOS_RESEARCH", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        viewModelClass = ViewModelProviders.of(this).get(ViewModelClass.class);

        viewModelClass.setFishes(sharedPreferences.getInt("fish", 10));
        viewModelClass.setAnimals(sharedPreferences.getFloat("animal", 10));

        final TextView fishView = findViewById(R.id.fishes);
        final TextView animalView = findViewById(R.id.animals);

        viewModelClass.getFishes().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                fishView.setText(String.valueOf(value));
                fish = value;
            }
        });

        viewModelClass.getAnimals().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float value) {
                animalView.setText(String.valueOf(value));
                animal = value;
            }
        });
    }

    public void changeFish(View view) {
        fish++;
        viewModelClass.setFishes(fish);
        editor.putInt("fish",fish);
        editor.commit();
    }

    public void changeAnimal(View view) {
        animal++;
        viewModelClass.setAnimals(animal);
        editor.putFloat("animal",animal);
        editor.commit();
    }
}
