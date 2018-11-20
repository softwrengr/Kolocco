package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 13/4/18.
 */

public class LocalNew {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("sports_images")
    @Expose
    private List<LocalImage> localImagesNew = new ArrayList<>();

    public List<LocalImage> getLocalImagesNew() {
        return localImagesNew;
    }

    public void setLocalImagesNew(List<LocalImage> localImagesNew) {
        this.localImagesNew = localImagesNew;
    }

    @SerializedName("firstname")
    @Expose
    private String firstName;

    @SerializedName("lastname")
    @Expose
    private String lastName;

    @SerializedName("location")
    @Expose
    private String location;


    @SerializedName("location_address")
    @Expose
    private String locationAddress = "";

    @SerializedName("location_latitude")
    @Expose
    private String locationLatitude = "0.0";

    @SerializedName("location_longitude")
    @Expose
    private String locationLongitude = "0.0";

    @SerializedName("location_city")
    @Expose
    private String city = "";

    @SerializedName("location_state")
    @Expose
    private String state = "";

    @SerializedName("location_country")
    @Expose
    private String country = "";


    @SerializedName("rate")
    @Expose
    private String rate;

    @SerializedName("review_count")
    @Expose
    private String rateCount = "0";


    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("sports")
    @Expose
    private List<Service> services;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRateCount() {
        return rateCount;
    }

    public void setRateCount(String rateCount) {
        this.rateCount = rateCount;
    }


    @SerializedName("is_certified")
    @Expose
    private String isVerifed = "0";

    public String getIsVerifed() {
        return isVerifed;
    }

    public void setIsVerifed(String isVerifed) {
        this.isVerifed = isVerifed;
    }
}
