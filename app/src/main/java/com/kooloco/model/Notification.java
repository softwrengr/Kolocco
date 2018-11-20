package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.NotificationTag;

/**
 * Created by hlink44 on 26/9/17.
 */

public class Notification {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("message")
    @Expose
    private String title = "";

    @SerializedName("description")
    @Expose
    private String subTitle = "";

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("receiver_id")
    @Expose
    private String receiverId;

    @SerializedName("is_replied")
    @Expose
    private String isReplied;

    @SerializedName("sender_id")
    @Expose
    private String senderId;


    @SerializedName("is_read")
    @Expose
    private String isRead;

    @SerializedName("insertdate")
    @Expose
    private String time = "";

    @JsonAdapter(NotificationTag.class)
    @SerializedName("notification_tag")
    @Expose
    private int status = 0;

    @SerializedName("status")
    @Expose
    private String statusNotification;


    //New

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @SerializedName("meeting_address")
    @Expose
    private String meetingAddress;

    @SerializedName("meeting_latitude")
    @Expose
    private String mettingLatitude;

    @SerializedName("meeting_longitude")
    @Expose
    private String meetingLongitude;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(String isReplied) {
        this.isReplied = isReplied;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getStatusNotification() {
        return statusNotification;
    }

    public void setStatusNotification(String statusNotification) {
        this.statusNotification = statusNotification;
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

    //New


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public String getMettingLatitude() {
        return mettingLatitude;
    }

    public void setMettingLatitude(String mettingLatitude) {
        this.mettingLatitude = mettingLatitude;
    }

    public String getMeetingLongitude() {
        return meetingLongitude;
    }

    public void setMeetingLongitude(String meetingLongitude) {
        this.meetingLongitude = meetingLongitude;
    }


    @SerializedName("request_to_admin")
    @Expose
    private String requestToAdmin = "0";

    public String getRequestToAdmin() {
        return requestToAdmin;
    }

    public void setRequestToAdmin(String requestToAdmin) {
        this.requestToAdmin = requestToAdmin;
    }
}
