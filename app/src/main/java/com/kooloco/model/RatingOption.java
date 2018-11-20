package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 8/3/18.
 */

public class RatingOption {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("star")
    @Expose
    private String star;
    @SerializedName("option_text")
    @Expose
    private String optionText;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;

    @SerializedName("rate")
    @Expose
    private String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
