package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 9/5/18.
 */

public class HomeLocalAndExpResponse {
    @SerializedName("nearbylocals")
    @Expose
    List<LocalNew> localNews = new ArrayList<>();

    @SerializedName("nearbyexperiences")
    @Expose
    List<ExpereinceNew> expereinceNews = new ArrayList<>();

    public List<LocalNew> getLocalNews() {
        return localNews;
    }

    public void setLocalNews(List<LocalNew> localNews) {
        this.localNews = localNews;
    }

    public List<ExpereinceNew> getExpereinceNews() {
        return expereinceNews;
    }

    public void setExpereinceNews(List<ExpereinceNew> expereinceNews) {
        this.expereinceNews = expereinceNews;
    }
}
