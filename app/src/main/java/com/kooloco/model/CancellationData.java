package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 21/6/18.
 */

public class CancellationData {
    @SerializedName("refund")
    @Expose
    private String refaund = "0";
    @SerializedName("total_booking")
    @Expose
    private String total_booking = "0";

    public String getRefaund() {
        return refaund;
    }

    public void setRefaund(String refaund) {
        this.refaund = refaund;
    }

    public String getTotal_booking() {
        return total_booking;
    }

    public void setTotal_booking(String total_booking) {
        this.total_booking = total_booking;
    }
}
