package com.example.viewpagerwithdynamicviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.viewpagerwithdynamicviews.Views.FirstFragment;
import com.example.viewpagerwithdynamicviews.Views.SecondFragment;
import com.example.viewpagerwithdynamicviews.Views.ThridFragment;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private PageAdapter adapter;
    private ViewPager viewpager;
    private VueModel vueModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vueModel = new ViewModelProvider(this).get(VueModel.class);

        viewpager = findViewById(R.id.vp);
        adapter = new PageAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        addFragments();

        /*--- Page Change Listner ---*/
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if(position==1 && adapter.getCount()>2){
//                    adapter.removeFragment(position);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setCurrentItem(0);
        vueModel.getPosition().observe(this, new Observer<PagerModel>() {
            @Override
            public void onChanged(PagerModel pagerModel) {
                if(pagerModel.toPage) {
                    adapter.addFragment(new ThridFragment(), "Fragment 3");
                    viewpager.setCurrentItem(adapter.getCount());
                }else {
                    viewpager.setCurrentItem(pagerModel.pageNumber);
                    adapter.removeFragment(adapter.getCount()-1);
                }
                Log.d("Current Page: ", " size: " + adapter.getCount());
            }
        });
    }

    private void addFragments() {
        adapter.addFragment(new FirstFragment(), "Fragment 1");
        adapter.addFragment(new SecondFragment(), "Fragment 2");
    }
}