package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 21/9/17.
 */

public class Time {
    @SerializedName("slot")
    @Expose
    private String time = "";

    @SerializedName("is_disable")
    @Expose
    private String isDisable = "0";

    @SerializedName("is_visible")
    @Expose
    private String isVisible = "0";

    public String isSelectedStTEn = "0";

    public String getTime() {
        return time;
    }

    public String isDisallow = "0";

    private boolean isSelect;

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getIsSelectedStTEn() {
        return isSelectedStTEn;
    }

    public void setIsSelectedStTEn(String isSelectedStTEn) {
        this.isSelectedStTEn = isSelectedStTEn;
    }

    public String getIsDisallow() {
        return isDisallow;
    }

    public void setIsDisallow(String isDisallow) {
        this.isDisallow = isDisallow;
    }
}
