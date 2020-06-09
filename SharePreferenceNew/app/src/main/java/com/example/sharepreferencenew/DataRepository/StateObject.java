package com.example.sharepreferencenew.DataRepository;

public class StateObject {
    private int level;
    private float speed_zero;
    private float speed_one;
    private float speed_two;

    public StateObject() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getSpeed_zero() {
        return speed_zero;
    }
    public void setSpeed_zero(float speed_zero) {
        this.speed_zero = speed_zero;
    }

    public float getSpeed_one() {
        return speed_one;
    }
    public void setSpeed_one(float speed_one) {
        this.speed_one = speed_one;
    }

    public float getSpeed_two() {
        return speed_two;
    }
    public void setSpeed_two(float speed_two) {
        this.speed_two = speed_two;
    }
}
