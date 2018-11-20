package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 25/9/17.
 */

public class OrderOrg {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("local_id")
    @Expose
    private String localId;
    @SerializedName("sport_id")
    @Expose
    private String sportId;
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
    @SerializedName("meeting_address")
    @Expose
    private String meetingAddress;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
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


    @SerializedName("is_local_set_duration")
    @Expose
    private String isLocalSetDuration;


    @SerializedName("is_local_set_meeting_loc")
    @Expose
    private String isLocalSetMeetingLoc;


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

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
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


    @SerializedName("meeting_latitude")
    @Expose
    private String meetingLatitude;

    @SerializedName("meeting_longitude")
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

    public String getIsLocalSetDuration() {
        return isLocalSetDuration;
    }

    public void setIsLocalSetDuration(String isLocalSetDuration) {
        this.isLocalSetDuration = isLocalSetDuration;
    }

    public String getIsLocalSetMeetingLoc() {
        return isLocalSetMeetingLoc;
    }

    public void setIsLocalSetMeetingLoc(String isLocalSetMeetingLoc) {
        this.isLocalSetMeetingLoc = isLocalSetMeetingLoc;
    }

    @SerializedName("local_first_name")
    @Expose
    private String localFirstName = "";

    @SerializedName("local_last_name")
    @Expose
    private String localLastName = "";

    @SerializedName("payment_rules_text")
    @Expose
    private String paymentRuleText = "";

    public String getLocalFirstName() {
        return localFirstName;
    }

    public void setLocalFirstName(String localFirstName) {
        this.localFirstName = localFirstName;
    }

    public String getLocalLastName() {
        return localLastName;
    }

    public void setLocalLastName(String localLastName) {
        this.localLastName = localLastName;
    }

    public String getPaymentRuleText() {
        return paymentRuleText;
    }

    public void setPaymentRuleText(String paymentRuleText) {
        this.paymentRuleText = paymentRuleText;
    }

    @SerializedName("city")
    @Expose
    private String city = "";


    @SerializedName("country")
    @Expose
    private String country = "";


    @SerializedName("experience_title")
    @Expose
    private String experienceTitle = "";


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
