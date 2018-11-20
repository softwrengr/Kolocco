package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hlink44 on 19/9/17.
 */

public class Service {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("icon")
    @Expose
    String serviceImage = "";

    @SerializedName("name")
    @Expose
    String name = "";

    @SerializedName("is_expandable")
    @Expose
    private String isExpandable;

    @SerializedName("sub_sport_type")
    @Expose
    private List<SubService> subServices;

    @SerializedName("is_selected")
    @Expose
    private String isSelected = "0";

    @SerializedName("boarding_icon")
    @Expose
    String onBoardingImage = "";

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubService> subServices) {
        this.subServices = subServices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsExpandable() {
        return isExpandable;
    }

    public void setIsExpandable(String isExpandable) {
        this.isExpandable = isExpandable;
    }

    boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getOnBoardingImage() {
        return onBoardingImage;
    }

    public void setOnBoardingImage(String onBoardingImage) {
        this.onBoardingImage = onBoardingImage;
    }
}
