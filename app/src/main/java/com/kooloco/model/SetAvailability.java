package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 24/11/17.
 */

public class SetAvailability {

    @SerializedName("user_id")
    @Expose
    private String userId = "";

    @SerializedName("day")
    @Expose
    private String day = "";

    @SerializedName("start_time")
    @Expose
    private String startTime = "";

    @SerializedName("end_time")
    @Expose
    private String endTime = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
