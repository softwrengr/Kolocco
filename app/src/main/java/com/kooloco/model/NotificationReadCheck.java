package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 6/6/18.
 */

public class NotificationReadCheck {
    @SerializedName("is_all_read")
    @Expose
    private String isReadAll = "0";


    public String getIsReadAll() {
        return isReadAll;
    }

    public void setIsReadAll(String isReadAll) {
        this.isReadAll = isReadAll;
    }
}
