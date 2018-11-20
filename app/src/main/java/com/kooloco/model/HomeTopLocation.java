package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 20/4/18.
 */

public class HomeTopLocation {

    @SerializedName("image")
    @Expose
    private String topLocationImageUrl = "";


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("experience_count")
    @Expose
    private String expCount = "0";


    public String getTopLocationImageUrl() {
        return topLocationImageUrl;
    }

    public void setTopLocationImageUrl(String topLocationImageUrl) {
        this.topLocationImageUrl = topLocationImageUrl;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExpCount() {
        return expCount;
    }

    public void setExpCount(String expCount) {
        this.expCount = expCount;
    }

    @SerializedName("latitude")
    @Expose
    private String lett = "0.0";

    @SerializedName("longitude")
    @Expose
    private String lngt = "0.0";

    public String getLett() {
        return lett;
    }

    public void setLett(String lett) {
        this.lett = lett;
    }

    public String getLngt() {
        return lngt;
    }

    public void setLngt(String lngt) {
        this.lngt = lngt;
    }
}
