package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.StringToBoolean;

/**
 * Created by hlink on 20/6/18.
 */

public class Bank {

    @SerializedName("id")
    @Expose
    private String bankId;

    @SerializedName("bank_name")
    @Expose
    private String bankName = "";

    @SerializedName("account_number")
    @Expose
    private String accountNumber = "";

    @JsonAdapter(StringToBoolean.class)
    @SerializedName("is_default")
    @Expose
    private boolean isSelect;


    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
