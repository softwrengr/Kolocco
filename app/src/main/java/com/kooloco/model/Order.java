package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kooloco.core.ORDERSTATUS;

/**
 * Created by hlink44 on 25/9/17.
 */

public class Order {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("local_id")
    @Expose
    private String localId;

    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;


    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    @SerializedName("activity_address")
    @Expose
    private String meetingAddress;


    @SerializedName("experience_name")
    @Expose
    private String experienceName;
    @SerializedName("status")
    @Expose
    private String status = "";
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("sport")
    @Expose
    private String sport;

    @SerializedName("booking_date")
    @Expose
    private String bookingDate;

    @SerializedName("rate")
    @Expose
    private String rating = "0";

    @SerializedName("activity_total")
    @Expose
    private String activityTootal;

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

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getExperienceName() {
        return experienceName;
    }

    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    @SerializedName("activity_latitude")
    @Expose
    private String meetingLatitude;

    @SerializedName("activity_longitude")
    @Expose
    private String meetingLongitude;

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

    public String getActivityTootal() {
        return activityTootal;
    }

    public void setActivityTootal(String activityTootal) {
        this.activityTootal = activityTootal;
    }

    @SerializedName("visitor_rate")
    @Expose
    private String visitorRate = "";

    public String getVisitorRate() {
        return visitorRate;
    }

    public void setVisitorRate(String visitorRate) {
        this.visitorRate = visitorRate;
    }


    @SerializedName("city")
    @Expose
    private String city = "";

    @SerializedName("state")
    @Expose
    private String state = "";
    @SerializedName("country")
    @Expose
    private String country = "";

    @SerializedName("experience_title")
    @Expose
    private String experienceTitle = "";

    @SerializedName("icon")
    @Expose
    private String expIcon = "";


    @SerializedName("review_count")
    @Expose
    private String reviewCount = "0";


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExperienceTitle() {
        return experienceTitle;
    }

    public void setExperienceTitle(String experienceTitle) {
        this.experienceTitle = experienceTitle;
    }

    public String getExpIcon() {
        return expIcon;
    }

    public void setExpIcon(String expIcon) {
        this.expIcon = expIcon;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    @SerializedName("visitor_currency")
    @Expose
    private String visitorCurrency = "CHF";


    public String getVisitorCurrency() {
        return visitorCurrency;
    }

    public void setVisitorCurrency(String visitorCurrency) {
        this.visitorCurrency = visitorCurrency;
    }

    @SerializedName("is_objected")
    @Expose
    private String isObjected = "0";


    @SerializedName("is_receipt")
    @Expose
    private String isReceipt = "0";


    public String getIsObjected() {
        return isObjected;
    }

    public void setIsObjected(String isObjected) {
        this.isObjected = isObjected;
    }

    @SerializedName("objection_id")
    @Expose
    private String objectionId = "0";

    public String getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(String objectionId) {
        this.objectionId = objectionId;
    }

    public String getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt;
    }
}
