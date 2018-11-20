package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.DateConvert;
import com.kooloco.gsonadapter.StringToBoolean;

/**
 * Created by hlink44 on 24/11/17.
 */

public class CheckEquipment {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_approved")
    @Expose
    private String isAdminApprove;

    @SerializedName("is_new")
    @Expose
    private String isNew;

    @JsonAdapter(StringToBoolean.class)
    @SerializedName("is_selected")
    @Expose
    private boolean isSelect;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
