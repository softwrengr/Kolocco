package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hlink44 on 21/9/17.
 */

public class SelectActivites {

    @SerializedName("experience")
    @Expose
    private List<Activities> activities;

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

}
