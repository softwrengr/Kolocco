package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 5/2/18.
 */

public class PaymentRuleList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("organisation_id")
    @Expose
    private String organisationId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("payment_option_id")
    @Expose
    private String paymentOptionId="";

    @SerializedName("payment_option_value")
    @Expose
    private String paymentOptionValue="";

    @SerializedName("payment_value")
    @Expose
    private String paymentValue;
    @SerializedName("rule_type")
    @Expose
    private String ruleType;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("assigned_local")
    @Expose
    private List<PaymentRuleLocal> assignedLocal = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaymentOptionId() {
        return paymentOptionId;
    }

    public void setPaymentOptionId(String paymentOptionId) {
        this.paymentOptionId = paymentOptionId;
    }

    public String getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(String paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
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

    public List<PaymentRuleLocal> getAssignedLocal() {
        return assignedLocal;
    }

    public void setAssignedLocal(List<PaymentRuleLocal> assignedLocal) {
        this.assignedLocal = assignedLocal;
    }

    public String getPaymentOptionValue() {
        return paymentOptionValue;
    }

    public void setPaymentOptionValue(String paymentOptionValue) {
        this.paymentOptionValue = paymentOptionValue;
    }
}
