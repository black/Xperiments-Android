package com.example.kuro.bloodpressure.RecyclerView;

import android.support.annotation.DrawableRes;

/**
 * Created by Kuro on 5/2/2016.
 */
public class Item {

    private int mDrawableRes;

    private String mTitle;

    public Item(@DrawableRes int drawable, String title) {
        mDrawableRes = drawable;
        mTitle = title;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }

}