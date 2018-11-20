package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 20/9/17.
 */

public class OrgLocal {

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("profile_image")
    @Expose
    private String imageUrl = "";

    @SerializedName("firstname")
    @Expose
    private String firstName = "";

    @SerializedName("lastname")
    @Expose
    private String lastName = "";

    private String name = "";

    private boolean isSelect = false;

    private boolean isPrevSelect = false;


    @SerializedName("sports_csv")
    @Expose
    private String sportCsvId= "";

    @SerializedName("local_id")
    @Expose
    private String localId= "";

    @SerializedName("is_added")
    @Expose
    private String isAdded= "0";

    @SerializedName("is_payment_rule_assigned")
    @Expose
    private String isPaymentRulesAssign= "0";

    @SerializedName("is_accept")
    @Expose
    private String isAccepted= "0";

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isPrevSelect() {
        return isPrevSelect;
    }

    public void setPrevSelect(boolean prevSelect) {
        isPrevSelect = prevSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSportCsvId() {
        return sportCsvId;
    }

    public void setSportCsvId(String sportCsvId) {
        this.sportCsvId = sportCsvId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getIsPaymentRulesAssign() {
        return isPaymentRulesAssign;
    }

    public void setIsPaymentRulesAssign(String isPaymentRulesAssign) {
        this.isPaymentRulesAssign = isPaymentRulesAssign;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }
}
