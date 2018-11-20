package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kooloco.data.temp.Temp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public class ExpereinceNew {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("local_id")
    @Expose
    private String localId;

    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("image")
    @Expose
    private String image_url;


    @SerializedName("review_count")
    @Expose
    private String rateCount;

    @SerializedName("rate")
    @Expose
    private String rate = "0.0";

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("activity_icon_2")
    @Expose
    private String experience_url = "";

    @SerializedName("activity_icon")
    @Expose
    private String experience_url1 = "";

    @SerializedName("price")
    @Expose
    private String price = "00.00";

    @SerializedName("activity_address")
    @Expose
    private String activityAddress = "";

    @SerializedName("activity_latitude")
    @Expose
    private String activityLatitude = "0.0";

    @SerializedName("activity_longitude")
    @Expose
    private String activityLongitude = "0.0";

    @SerializedName("city")
    @Expose
    private String city = "";

    @SerializedName("state")
    @Expose
    private String state = "";

    @SerializedName("country")
    @Expose
    private String country = "";

    @SerializedName("maximum_participant")
    @Expose
    private String maximumParticipant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        if (city.isEmpty()) {
            return country;

        } else {
            return city + ", " + country;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRateCount() {
        return rateCount;
    }

    public void setRateCount(String rateCount) {
        this.rateCount = rateCount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExperience_url() {
        return experience_url;
    }

    public void setExperience_url(String experience_url) {
        this.experience_url = experience_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMaximumParticipant() {
        return maximumParticipant;
    }

    public void setMaximumParticipant(String maximumParticipant) {
        this.maximumParticipant = maximumParticipant;
    }


    @SerializedName("progress")
    @Expose
    private List<ProfileStatus> progress = new ArrayList<>();


    public List<ProfileStatus> getProgress() {
        return progress;
    }


    public void setProgress(List<ProfileStatus> progress) {
        this.progress = progress;
    }

    @SerializedName("is_approve")
    @Expose
    private String isAdminApprove = "0";

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
    }


    @SerializedName("is_deleted")
    @Expose
    private String isDeleted = "0";

    @SerializedName("is_active")
    @Expose
    private String isActive = "0";

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }


    @SerializedName("is_favorite")
    @Expose
    private String isFavorite = "0";

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    @SerializedName("images")
    @Expose
    private List<LocalImage> localImages = new ArrayList<>();

    public List<LocalImage> getLocalImages() {
        return localImages;
    }

    public void setLocalImages(List<LocalImage> localImages) {
        this.localImages = localImages;
    }

    public String getExperience_url1() {
        return experience_url1;
    }

    public void setExperience_url1(String experience_url1) {
        this.experience_url1 = experience_url1;
    }
}
