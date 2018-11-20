package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 30/3/18.
 */

public class CheckPaymentRules {
    @SerializedName("is_assign")
    @Expose
    private String isAssign = "0";

    @SerializedName("payment_rule_text")
    @Expose
    private String paymentRuleText = "";

    public String getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(String isAssign) {
        this.isAssign = isAssign;
    }

    public String getPaymentRuleText() {
        return paymentRuleText;
    }

    public void setPaymentRuleText(String paymentRuleText) {
        this.paymentRuleText = paymentRuleText;
    }
}

