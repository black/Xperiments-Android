package com.example.onboardingexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context ctx;
    List<ScreenItem> screenItemList;

    public IntroViewPagerAdapter(Context ctx, List<ScreenItem> screenItemList) {
        this.ctx = ctx;
        this.screenItemList = screenItemList;
    }

    @Override
    public int getCount() {
        return screenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutSCreen = inflater.inflate(R.layout.layout_screen,null);
        ImageView imageView = layoutSCreen.findViewById(R.id.titleImage);
        TextView title = layoutSCreen.findViewById(R.id.title);
        TextView description = layoutSCreen.findViewById(R.id.description);

        imageView.setImageResource(screenItemList.get(position).screenImg);
        title.setText(screenItemList.get(position).title);
        description.setText(screenItemList.get(position).desciption);
        container.addView(layoutSCreen);
        return layoutSCreen;
    }
}
