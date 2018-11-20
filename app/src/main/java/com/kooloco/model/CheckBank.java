package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.StringToBoolean;

/**
 * Created by hlink on 21/6/18.
 */

public class CheckBank {
    @JsonAdapter(StringToBoolean.class)
    @SerializedName("is_bank")
    @Expose
    private boolean isBank;

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
    }
}
