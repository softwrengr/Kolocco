package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 28/11/17.
 */

public class SelectSport {

    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
