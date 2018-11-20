package com.kooloco.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 21/11/17.
 */

public class SportSubActivityEntity {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("is_admin_approve")
    @Expose
    private String isAdminApprove;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("is_selected")
    @Expose
    private String isSelected;

    @SerializedName("price")
    @Expose
    private String price="";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
