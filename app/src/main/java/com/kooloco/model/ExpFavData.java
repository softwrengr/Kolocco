package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 26/6/18.
 */

public class ExpFavData {

    @SerializedName("is_favorite")
    @Expose
    private String isFav = "0";

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}
