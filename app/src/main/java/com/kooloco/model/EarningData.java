package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 10/3/18.
 */

public class EarningData {
    @SerializedName("rating_list")
    @Expose
    private List<EarningRate> earningRates = new ArrayList<>();

    @SerializedName("average_rate")
    @Expose
    private String tootalReview = "0";

    public List<EarningRate> getEarningRates() {
        return earningRates;
    }

    public void setEarningRates(List<EarningRate> earningRates) {
        this.earningRates = earningRates;
    }

    public String getTootalReview() {
        return tootalReview;
    }

    public void setTootalReview(String tootalReview) {
        this.tootalReview = tootalReview;
    }


    @SerializedName("monthly_earning")
    @Expose
    List<MonthEarning> monthEarnings = new ArrayList<>();

    @SerializedName("total_amount")
    @Expose
    private String totalAmount = "0.00";


    public List<MonthEarning> getMonthEarnings() {
        return monthEarnings;
    }

    public void setMonthEarnings(List<MonthEarning> monthEarnings) {
        this.monthEarnings = monthEarnings;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
