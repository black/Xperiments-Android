package com.experiments.vuemodelwithvuepager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.experiments.vuemodelwithvuepager.Pages.FirstFragment;
import com.experiments.vuemodelwithvuepager.Pages.SecondFragment;
import com.experiments.vuemodelwithvuepager.Pages.ThirdFragment;

public class MainActivity extends AppCompatActivity {

    private static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMEN = 1;

    private VueModel vueModel;
    private ViewPager viewPager;
    private PagerAdapter myPagerAdapter;

    private int currentinterface = 0;

    private String[] levels = {"oneData","twoData","threeData"};

    private String[] oneData = {"Lv 1A","Lv 1B","Lv 1C"};
    private String[] twoData = {"Lv 2A","Lv 2B","Lv 2C"};
    private String[] threeData = {"Lv 3A","Lv 3B","Lv 3C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vueModel = new ViewModelProvider(this).get(VueModel.class);

        viewPager = findViewById(R.id.demoPager);
        myPagerAdapter = new PagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMEN);
        viewPager.setAdapter(myPagerAdapter);
        addFragment();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(currentinterface);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                vueModel.setLevel(pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addFragment(){
        myPagerAdapter.addFragment(new FirstFragment());
        myPagerAdapter.addFragment(new SecondFragment());
        myPagerAdapter.addFragment(new ThirdFragment());
        initApp();
        observers();
    }

    private void initApp(){
        vueModel.setLevel(0);
    }

    private void observers(){
        vueModel.getLevel().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer pos) {
                switch (pos){
                    case 0:
                        vueModel.setData(oneData);
                        break;
                    case 1:
                        vueModel.setData(twoData);
                        break;
                    case 2:
                        vueModel.setData(threeData);
                        break;
                }
            }
        });
    }

}