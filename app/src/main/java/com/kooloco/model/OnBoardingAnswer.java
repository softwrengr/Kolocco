package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 18/5/18.
 */

public class OnBoardingAnswer {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("is_yes")
    @Expose
    private String isYes;

    @SerializedName("sport_id")
    @Expose
    private String sportId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsYes() {
        return isYes;
    }

    public void setIsYes(String isYes) {
        this.isYes = isYes;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
}
