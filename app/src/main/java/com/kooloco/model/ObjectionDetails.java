package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 19/1/18.
 */

public class ObjectionDetails {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("reference_id")
    @Expose
    private String referenceId;
    @SerializedName("refecence_table")
    @Expose
    private String refecenceTable;
    @SerializedName("cr_type")
    @Expose
    private String crType;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("meeting_address")
    @Expose
    private String meetingAddress;
    @SerializedName("meeting_latitude")
    @Expose
    private String meetingLatitude;
    @SerializedName("meeting_longitude")
    @Expose
    private String meetingLongitude;
    @SerializedName("objection_amount")
    @Expose
    private String objectionAmount;
    @SerializedName("objection_perc")
    @Expose
    private String objectionPerc;
    @SerializedName("objection_type")
    @Expose
    private String objectionType;
    @SerializedName("is_objected")
    @Expose
    private String isObjected;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;

    @SerializedName("order")
    @Expose
    private OrderDetails orderDetails;

    @SerializedName("visitor_rate")
    @Expose
    private String visitorRate;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getRefecenceTable() {
        return refecenceTable;
    }

    public void setRefecenceTable(String refecenceTable) {
        this.refecenceTable = refecenceTable;
    }

    public String getCrType() {
        return crType;
    }

    public void setCrType(String crType) {
        this.crType = crType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public String getMeetingLatitude() {
        return meetingLatitude;
    }

    public void setMeetingLatitude(String meetingLatitude) {
        this.meetingLatitude = meetingLatitude;
    }

    public String getMeetingLongitude() {
        return meetingLongitude;
    }

    public void setMeetingLongitude(String meetingLongitude) {
        this.meetingLongitude = meetingLongitude;
    }

    public String getObjectionAmount() {
        return objectionAmount;
    }

    public void setObjectionAmount(String objectionAmount) {
        this.objectionAmount = objectionAmount;
    }

    public String getObjectionPerc() {
        return objectionPerc;
    }

    public void setObjectionPerc(String objectionPerc) {
        this.objectionPerc = objectionPerc;
    }

    public String getObjectionType() {
        return objectionType;
    }

    public void setObjectionType(String objectionType) {
        this.objectionType = objectionType;
    }

    public String getIsObjected() {
        return isObjected;
    }

    public void setIsObjected(String isObjected) {
        this.isObjected = isObjected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getVisitorRate() {
        return visitorRate;
    }

    public void setVisitorRate(String visitorRate) {
        this.visitorRate = visitorRate;
    }
}
