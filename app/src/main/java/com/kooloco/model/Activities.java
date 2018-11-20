package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hlink44 on 20/9/17.
 */

public class Activities {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("icon")
    @Expose
    private String imageUrl = "";

    @SerializedName("icon_2")
    @Expose
    private String imageUrlTwo = "";

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("description")
    @Expose
    private String desc = "";


    @SerializedName("is_selected")
    @Expose
    private String isSelected = "0";

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }


    //This section for used visitor side only

    @SerializedName("minimum_duration")
    @Expose
    private String minimumDuration = "";

    @SerializedName("is_multiple_booking")
    @Expose
    private String isMultipaleBooking = "";

    @SerializedName("new_participant_perc")
    @Expose
    private String participantPer = "";

    @SerializedName("number_of_participant")
    @Expose
    private String numberOfPerticipant = "";


    @SerializedName("price_per_hour")
    @Expose
    private String pricePerHour = "";


    public String getMinimumDuration() {
        return minimumDuration;
    }

    public void setMinimumDuration(String minimumDuration) {
        this.minimumDuration = minimumDuration;
    }

    public String getIsMultipaleBooking() {
        return isMultipaleBooking;
    }

    public void setIsMultipaleBooking(String isMultipaleBooking) {
        this.isMultipaleBooking = isMultipaleBooking;
    }

    public String getParticipantPer() {
        return participantPer;
    }

    public void setParticipantPer(String participantPer) {
        this.participantPer = participantPer;
    }

    public String getNumberOfPerticipant() {
        return numberOfPerticipant;
    }

    public void setNumberOfPerticipant(String numberOfPerticipant) {
        this.numberOfPerticipant = numberOfPerticipant;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @SerializedName("sports")
    @Expose
    private List<Service> services;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    boolean isSelect=false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImageUrlTwo() {
        return imageUrlTwo;
    }

    public void setImageUrlTwo(String imageUrlTwo) {
        this.imageUrlTwo = imageUrlTwo;
    }
}
