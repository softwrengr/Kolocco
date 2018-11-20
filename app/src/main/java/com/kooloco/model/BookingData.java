package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 14/3/18.
 */

public class BookingData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("local_id")
    @Expose
    private String localId;
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("extra_participant")
    @Expose
    private String extraParticipant;
    @SerializedName("user_duration")
    @Expose
    private String userDuration;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("additional_participant_charged")
    @Expose
    private String additionalParticipantCharged;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("fees_for_booking")
    @Expose
    private String feesForBooking;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("commision_perc")
    @Expose
    private String commisionPerc;
    @SerializedName("commision_value")
    @Expose
    private String commisionValue;
    @SerializedName("fees_for_booking_perc")
    @Expose
    private String feesForBookingPerc;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("meeting_address")
    @Expose
    private String meetingAddress;
    @SerializedName("meeting_latitude")
    @Expose
    private String meetingLatitude;
    @SerializedName("meeting_longitude")
    @Expose
    private String meetingLongitude;
    @SerializedName("more_about")
    @Expose
    private String moreAbout;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("is_objected")
    @Expose
    private String isObjected;
    @SerializedName("objection_reason")
    @Expose
    private String objectionReason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("canceled_by")
    @Expose
    private String canceledBy;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("is_local_set_meeting_loc")
    @Expose
    private String isLocalSetMeetingLoc;
    @SerializedName("is_local_set_duration")
    @Expose
    private String isLocalSetDuration;
    @SerializedName("activity_total")
    @Expose
    private String activityTotal;
    @SerializedName("price_individual")
    @Expose
    private String priceIndividual;
    @SerializedName("additional_flat_amount")
    @Expose
    private String additionalFlatAmount;

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

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExtraParticipant() {
        return extraParticipant;
    }

    public void setExtraParticipant(String extraParticipant) {
        this.extraParticipant = extraParticipant;
    }

    public String getUserDuration() {
        return userDuration;
    }

    public void setUserDuration(String userDuration) {
        this.userDuration = userDuration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAdditionalParticipantCharged() {
        return additionalParticipantCharged;
    }

    public void setAdditionalParticipantCharged(String additionalParticipantCharged) {
        this.additionalParticipantCharged = additionalParticipantCharged;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFeesForBooking() {
        return feesForBooking;
    }

    public void setFeesForBooking(String feesForBooking) {
        this.feesForBooking = feesForBooking;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCommisionPerc() {
        return commisionPerc;
    }

    public void setCommisionPerc(String commisionPerc) {
        this.commisionPerc = commisionPerc;
    }

    public String getCommisionValue() {
        return commisionValue;
    }

    public void setCommisionValue(String commisionValue) {
        this.commisionValue = commisionValue;
    }

    public String getFeesForBookingPerc() {
        return feesForBookingPerc;
    }

    public void setFeesForBookingPerc(String feesForBookingPerc) {
        this.feesForBookingPerc = feesForBookingPerc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getMoreAbout() {
        return moreAbout;
    }

    public void setMoreAbout(String moreAbout) {
        this.moreAbout = moreAbout;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIsObjected() {
        return isObjected;
    }

    public void setIsObjected(String isObjected) {
        this.isObjected = isObjected;
    }

    public String getObjectionReason() {
        return objectionReason;
    }

    public void setObjectionReason(String objectionReason) {
        this.objectionReason = objectionReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = canceledBy;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getIsLocalSetMeetingLoc() {
        return isLocalSetMeetingLoc;
    }

    public void setIsLocalSetMeetingLoc(String isLocalSetMeetingLoc) {
        this.isLocalSetMeetingLoc = isLocalSetMeetingLoc;
    }

    public String getIsLocalSetDuration() {
        return isLocalSetDuration;
    }

    public void setIsLocalSetDuration(String isLocalSetDuration) {
        this.isLocalSetDuration = isLocalSetDuration;
    }

    public String getActivityTotal() {
        return activityTotal;
    }

    public void setActivityTotal(String activityTotal) {
        this.activityTotal = activityTotal;
    }

    public String getPriceIndividual() {
        return priceIndividual;
    }

    public void setPriceIndividual(String priceIndividual) {
        this.priceIndividual = priceIndividual;
    }

    public String getAdditionalFlatAmount() {
        return additionalFlatAmount;
    }

    public void setAdditionalFlatAmount(String additionalFlatAmount) {
        this.additionalFlatAmount = additionalFlatAmount;
    }
}
