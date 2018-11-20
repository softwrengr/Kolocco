package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 10/5/18.
 */

public class DisableDateListResposne {
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("select_date")
    @Expose
    private List<String> selectDate = new ArrayList<>();

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public List<String> getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(List<String> selectDate) {
        this.selectDate = selectDate;
    }

    private List<CalendarDay> calendarDays = new ArrayList<>();

    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(List<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }
}
