package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 1/5/18.
 */

public class ExperienceResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("local_id")
    @Expose
    private String localId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("activity_id")
    @Expose
    private String activityId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("activity_address")
    @Expose
    private String activityAddress="";
    @SerializedName("activity_latitude")
    @Expose
    private String activityLatitude="0.0";
    @SerializedName("activity_longitude")
    @Expose
    private String activityLongitude="0.0";
    @SerializedName("cancellation_policy")
    @Expose
    private String cancellationPolicy;
    @SerializedName("city")
    @Expose
    private String city="";
    @SerializedName("state")
    @Expose
    private String state="";
    @SerializedName("country")
    @Expose
    private String country="";
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("maximum_participant")
    @Expose
    private String maximumParticipant;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("is_approve")
    @Expose
    private String isApprove;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("media")
    @Expose
    private List<Medium> media = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getActivityLatitude() {
        return activityLatitude;
    }

    public void setActivityLatitude(String activityLatitude) {
        this.activityLatitude = activityLatitude;
    }

    public String getActivityLongitude() {
        return activityLongitude;
    }

    public void setActivityLongitude(String activityLongitude) {
        this.activityLongitude = activityLongitude;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMaximumParticipant() {
        return maximumParticipant;
    }

    public void setMaximumParticipant(String maximumParticipant) {
        this.maximumParticipant = maximumParticipant;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(String isApprove) {
        this.isApprove = isApprove;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
