package com.example.themeselectordemo;

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils {
    private static int cTheme;
    final static int BLACK = 0;
    final static int BLUE = 1;

    public static void changeToTheme(Activity activity, int theme){
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme){
            case BLACK:
                activity.setTheme(R.style.BlackTheme);
                break;
            case BLUE:
                activity.setTheme(R.style.BlueTheme);
                break;
        }

    }
}
