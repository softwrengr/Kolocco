package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 19/9/17.
 */

public class ServiceType {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String serviceName = "";

    @SerializedName("price_per_hour")
    @Expose
    private String servicePrice = "";

    private String servicePriceAs = "/hr";

    @SerializedName("description")
    @Expose
    private String desc = "";

    @SerializedName("icon")
    @Expose
    private String imageUrl = "";



    @SerializedName("icon_2")
    @Expose
    private String imageUrlTwo = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return "$" + servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServicePriceAs() {
        return servicePriceAs;
    }

    public void setServicePriceAs(String servicePriceAs) {
        this.servicePriceAs = servicePriceAs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlTwo() {
        return imageUrlTwo;
    }

    public void setImageUrlTwo(String imageUrlTwo) {
        this.imageUrlTwo = imageUrlTwo;
    }
}
