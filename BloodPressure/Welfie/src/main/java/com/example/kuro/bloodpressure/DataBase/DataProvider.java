package com.example.kuro.bloodpressure.DataBase;

/**
 * Created by Kuro on 4/19/2016.
 */
public class DataProvider {

    private String name;
    private String hr;
    private String bpr;
    private String dataStamp;
    private int no_of_rows;

    public DataProvider(String name, int no_of_rows, String hr, String bpr, String dataStamp){
        this.name = name;
        this.hr = hr;
        this.bpr = bpr;
        this.dataStamp = dataStamp;
        this.no_of_rows = no_of_rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getBpr() {
        return bpr;
    }

    public void setBpr(String bpr) {
        this.bpr = bpr;
    }

    public String getDataStamp() {
        return dataStamp;
    }

    public void setDataStamp(String dataStamp) {
        this.dataStamp = dataStamp;
    }

    public int getNo_of_rows() {
        return no_of_rows;
    }

    public void setNo_of_rows(int no_of_rows) {
        this.no_of_rows = no_of_rows;
    }




}
