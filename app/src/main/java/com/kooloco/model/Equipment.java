package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 24/11/17.
 */

public class Equipment {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_approved")
    @Expose
    private String isAdminApprove;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;

    @SerializedName("is_inbuilt")
    @Expose
    private String isInbuilt;

    @SerializedName("is_selected")
    @Expose
    private String isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
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

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getIsInbuilt() {
        return isInbuilt;
    }

    public void setIsInbuilt(String isInbuilt) {
        this.isInbuilt = isInbuilt;
    }
}
