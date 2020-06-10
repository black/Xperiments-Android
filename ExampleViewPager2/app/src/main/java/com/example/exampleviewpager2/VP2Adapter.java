package com.example.exampleviewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.exampleviewpager2.Fragments.FragmentOne;
import com.example.exampleviewpager2.Fragments.FragmentThree;
import com.example.exampleviewpager2.Fragments.FragmentTwo;

public class VP2Adapter extends FragmentStateAdapter {
    public VP2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
