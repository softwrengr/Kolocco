package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 20/2/18.
 */

public class BookingFeeAndComision {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("commision")
    @Expose
    private String commision;
    @SerializedName("fees_for_booking")
    @Expose
    private String feesForBooking;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getFeesForBooking() {
        return feesForBooking;
    }

    public void setFeesForBooking(String feesForBooking) {
        this.feesForBooking = feesForBooking;
    }


    @SerializedName("is_referral")
    @Expose
    private String isReferral = "0";


    @SerializedName("referral_amount")
    @Expose
    private String referralAmount = "0.00";

    @SerializedName("referral_percentage")
    @Expose
    private String referralPercentage = "0.00";

    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(String isReferral) {
        this.isReferral = isReferral;
    }

    public String getReferralPercentage() {
        return referralPercentage;
    }

    public void setReferralPercentage(String referralPercentage) {
        this.referralPercentage = referralPercentage;
    }

    @SerializedName("cancellation_title")
    @Expose
    private String cancelletionPolicyName = "";

    @SerializedName("cancellation_policy_description")
    @Expose
    private String cancelletionPolicyDesc = "";

    @SerializedName("cancellation_policy")
    @Expose
    private List<CancellationNewPolicy> cancelletionPolicyList = new ArrayList<>();

    public String getCancelletionPolicyName() {
        return cancelletionPolicyName;
    }

    public void setCancelletionPolicyName(String cancelletionPolicyName) {
        this.cancelletionPolicyName = cancelletionPolicyName;
    }

    public String getCancelletionPolicyDesc() {
        return cancelletionPolicyDesc;
    }

    public void setCancelletionPolicyDesc(String cancelletionPolicyDesc) {
        this.cancelletionPolicyDesc = cancelletionPolicyDesc;
    }

    public List<CancellationNewPolicy> getCancelletionPolicyList() {
        return cancelletionPolicyList;
    }

    public void setCancelletionPolicyList(List<CancellationNewPolicy> cancelletionPolicyList) {
        this.cancelletionPolicyList = cancelletionPolicyList;
    }
}
