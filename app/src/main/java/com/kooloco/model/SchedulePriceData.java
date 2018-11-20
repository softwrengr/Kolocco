package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 16/4/18.
 */

public class SchedulePriceData {
    @SerializedName("Sun")
    @Expose
    private List<SchedulePrice> sun = new ArrayList<>();

    @SerializedName("Mon")
    @Expose
    private List<SchedulePrice> mon = new ArrayList<>();

    @SerializedName("Tue")
    @Expose
    private List<SchedulePrice> tue = new ArrayList<>();

    @SerializedName("Wed")
    @Expose
    private List<SchedulePrice> wed = new ArrayList<>();

    @SerializedName("Thu")
    @Expose
    private List<SchedulePrice> thu = new ArrayList<>();

    @SerializedName("Fri")
    @Expose
    private List<SchedulePrice> fri = new ArrayList<>();

    @SerializedName("Sat")
    @Expose
    private List<SchedulePrice> sat = new ArrayList<>();

    public List<SchedulePrice> getSun() {
        return sun;
    }

    public void setSun(List<SchedulePrice> sun) {
        this.sun = sun;
    }

    public List<SchedulePrice> getMon() {
        return mon;
    }

    public void setMon(List<SchedulePrice> mon) {
        this.mon = mon;
    }

    public List<SchedulePrice> getTue() {
        return tue;
    }

    public void setTue(List<SchedulePrice> tue) {
        this.tue = tue;
    }

    public List<SchedulePrice> getWed() {
        return wed;
    }

    public void setWed(List<SchedulePrice> wed) {
        this.wed = wed;
    }

    public List<SchedulePrice> getThu() {
        return thu;
    }

    public void setThu(List<SchedulePrice> thu) {
        this.thu = thu;
    }

    public List<SchedulePrice> getFri() {
        return fri;
    }

    public void setFri(List<SchedulePrice> fri) {
        this.fri = fri;
    }

    public List<SchedulePrice> getSat() {
        return sat;
    }

    public void setSat(List<SchedulePrice> sat) {
        this.sat = sat;
    }
}
