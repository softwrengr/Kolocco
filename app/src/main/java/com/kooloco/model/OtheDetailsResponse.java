package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 2/5/18.
 */

public class OtheDetailsResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("highlight")
    @Expose
    private String highlight="";
    @SerializedName("included")
    @Expose
    private String included="";
    @SerializedName("not_included")
    @Expose
    private String notIncluded="";
    @SerializedName("tags")
    @Expose
    private List<String> tags= new ArrayList<>();
    @SerializedName("recommended")
    @Expose
    private List<OtherDetailsFieldsSelect> recommended = new ArrayList<>();
    @SerializedName("prefered_for")
    @Expose
    private List<OtherDetailsFieldsSelect> preferedFor = new ArrayList<>();
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;

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

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getNotIncluded() {
        return notIncluded;
    }

    public void setNotIncluded(String notIncluded) {
        this.notIncluded = notIncluded;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<OtherDetailsFieldsSelect> getRecommended() {
        return recommended;
    }

    public void setRecommended(List<OtherDetailsFieldsSelect> recommended) {
        this.recommended = recommended;
    }

    public List<OtherDetailsFieldsSelect> getPreferedFor() {
        return preferedFor;
    }

    public void setPreferedFor(List<OtherDetailsFieldsSelect> preferedFor) {
        this.preferedFor = preferedFor;
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


}
