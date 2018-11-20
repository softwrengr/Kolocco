package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 8/3/18.
 */

public class RateSelectOption {
    @SerializedName("option_id")
    @Expose
    private String optionId;

    @SerializedName("rate")
    @Expose
    private String rate;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
