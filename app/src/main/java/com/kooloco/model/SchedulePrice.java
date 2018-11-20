package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 16/4/18.
 */

public class SchedulePrice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("is_day_off")
    @Expose
    private String isDayOff;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;

    @SerializedName("price_per_participant")
    @Expose
    private String price;

    @SerializedName("is_multiple_day")
    @Expose
    private String isMultipleDay;

    @SerializedName("duration_in_days")
    @Expose
    private String durationInDays;


    @SerializedName("maximum_participant")
    @Expose
    private String maximumParticipant;

    @SerializedName("remain_participant")
    @Expose
    private String reminParticipant;

    @SerializedName("is_disable")
    @Expose
    private String isDisable = "0";


    public String getMaximumParticipant() {
        return maximumParticipant;
    }

    public void setMaximumParticipant(String maximumParticipant) {
        this.maximumParticipant = maximumParticipant;
    }

    public String getReminParticipant() {
        return reminParticipant;
    }

    public void setReminParticipant(String reminParticipant) {
        this.reminParticipant = reminParticipant;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
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

    public String getIsDayOff() {
        return isDayOff;
    }

    public void setIsDayOff(String isDayOff) {
        this.isDayOff = isDayOff;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getIsMultipleDay() {
        return isMultipleDay;
    }

    public void setIsMultipleDay(String isMultipleDay) {
        this.isMultipleDay = isMultipleDay;
    }

    public String getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(String durationInDays) {
        this.durationInDays = durationInDays;
    }

    @SerializedName("duration")
    @Expose
    private String duration = "00:00";

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
