package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 19/6/18.
 */

public class CountryList {
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_code")
    @Expose
    private String shortCode;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @SerializedName("is_iban")
    @Expose
    private String isIban = "1";

    public String getIsIban() {
        return isIban;
    }

    public void setIsIban(String isIban) {
        this.isIban = isIban;
    }



    @SerializedName("form_no")
    @Expose
    private String formNo = "0";

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }
}
