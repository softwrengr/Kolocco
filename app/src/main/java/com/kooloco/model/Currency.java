package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 30/6/18.
 */

public class Currency {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currancy_type")
    @Expose
    private String currancyType;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("inserdate")
    @Expose
    private String inserdate;
    @SerializedName("is_selected")
    @Expose
    private String isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrancyType() {
        return currancyType;
    }

    public void setCurrancyType(String currancyType) {
        this.currancyType = currancyType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getInserdate() {
        return inserdate;
    }

    public void setInserdate(String inserdate) {
        this.inserdate = inserdate;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

}
