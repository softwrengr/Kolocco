package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 9/5/18.
 */

public class HomeNewResponse {

    @SerializedName("recommended")
    @Expose
    private List<ExpereinceNew> recommended = new ArrayList<>();

    @SerializedName("sports")
    @Expose
    private List<Service> sports = new ArrayList<>();

    @SerializedName("nearest_locals")
    @Expose
    private List<LocalNew> nearestLocals = new ArrayList<>();

    @SerializedName("trendy_experience")
    @Expose
    private List<ExpereinceNew> trendyExperience = new ArrayList<>();

    @SerializedName("top_sport_destination")
    @Expose
    private List<HomeTopLocation> topSportDestination = new ArrayList<>();

    @SerializedName("new_experience")
    @Expose
    private List<ExpereinceNew> newExperience = new ArrayList<>();

    @SerializedName("staff_picks")
    @Expose
    private List<ExpereinceNew> staffPicks = new ArrayList<>();

    public List<ExpereinceNew> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<ExpereinceNew> recommended) {
        this.recommended = recommended;
    }

    public List<Service> getSports() {
        return sports;
    }

    public void setSports(List<Service> sports) {
        this.sports = sports;
    }

    public List<LocalNew> getNearestLocals() {
        return nearestLocals;
    }

    public void setNearestLocals(List<LocalNew> nearestLocals) {
        this.nearestLocals = nearestLocals;
    }

    public List<ExpereinceNew> getTrendyExperience() {
        return trendyExperience;
    }

    public void setTrendyExperience(List<ExpereinceNew> trendyExperience) {
        this.trendyExperience = trendyExperience;
    }

    public List<HomeTopLocation> getTopSportDestination() {
        return topSportDestination;
    }

    public void setTopSportDestination(List<HomeTopLocation> topSportDestination) {
        this.topSportDestination = topSportDestination;
    }

    public List<ExpereinceNew> getNewExperience() {
        return newExperience;
    }

    public void setNewExperience(List<ExpereinceNew> newExperience) {
        this.newExperience = newExperience;
    }

    public List<ExpereinceNew> getStaffPicks() {
        return staffPicks;
    }

    public void setStaffPicks(List<ExpereinceNew> staffPicks) {
        this.staffPicks = staffPicks;
    }
}
