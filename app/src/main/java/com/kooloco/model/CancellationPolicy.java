package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kooloco.data.temp.Temp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 24/11/17.
 */

public class CancellationPolicy {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("duration_type")
    @Expose
    private String durationType;
    @SerializedName("refund_ratio")
    @Expose
    private String refundRatio;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("is_selected")
    @Expose
    private String isSelected;



    @SerializedName("policy_detail")
    @Expose
    List<CancellationNewPolicy> cancellationNewPolicyList = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public String getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(String refundRatio) {
        this.refundRatio = refundRatio;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public List<CancellationNewPolicy> getCancellationNewPolicyList() {
        return cancellationNewPolicyList;
    }

    public void setCancellationNewPolicyList(List<CancellationNewPolicy> cancellationNewPolicyList) {
        this.cancellationNewPolicyList = cancellationNewPolicyList;
    }
}
