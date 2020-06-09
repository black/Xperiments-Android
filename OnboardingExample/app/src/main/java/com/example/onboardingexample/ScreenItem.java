package com.example.onboardingexample;

public class ScreenItem {
    String title,desciption;
    int screenImg;

    public ScreenItem(String title, String desciption, int screenImg) {
        this.title = title;
        this.desciption = desciption;
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getScreenImg() {
        return screenImg;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }
}
