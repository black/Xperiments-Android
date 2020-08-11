package com.example.recyclerviewgrids;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.recyclerviewgrids.Demo1.Demo1Fragment;
import com.example.recyclerviewgrids.Demo2.Demo2Fragment;
import com.example.recyclerviewgrids.Demo3.Demo3Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Demo1Fragment();
            case 1:
                return new Demo2Fragment();
            case 2:
                return new Demo3Fragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
