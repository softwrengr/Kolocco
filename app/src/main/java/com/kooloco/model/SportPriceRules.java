package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hlink44 on 27/11/17.
 */

public class SportPriceRules {

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price_per_hour")
    @Expose
    private String pricePerHour = "";

    @SerializedName("minimum_duration")
    @Expose
    private String minDuration = "3";

    @SerializedName("is_multiple_booking")
    @Expose
    private String isGroupBooking = "1";

    @SerializedName("new_participant_perc")
    @Expose
    private String addPersonPer = "";

    @SerializedName("number_of_participant")
    @Expose
    private String addPerson = "";

    @SerializedName("sports")
    @Expose
    private List<SportPriceRulesSport> sports;


    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getIsGroupBooking() {
        return isGroupBooking;
    }

    public void setIsGroupBooking(String isGroupBooking) {
        this.isGroupBooking = isGroupBooking;
    }

    public String getAddPersonPer() {
        return addPersonPer;
    }

    public void setAddPersonPer(String addPersonPer) {
        this.addPersonPer = addPersonPer;
    }

    public String getAddPerson() {
        return addPerson;
    }

    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson;
    }

    public List<SportPriceRulesSport> getSports() {
        return sports;
    }

    public void setSports(List<SportPriceRulesSport> sports) {
        this.sports = sports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
