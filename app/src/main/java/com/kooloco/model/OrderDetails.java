package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.OrderStatusTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 18/1/18.
 */

public class OrderDetails {
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

    @SerializedName("price_individual")
    @Expose
    private String priceIndividual;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("additional_flat_amount")
    @Expose
    private String additionalFlatAmount;

    @SerializedName("additional_participant_charged")
    @Expose
    private String additionalParticipantCharged;

    @SerializedName("activity_total")
    @Expose
    private String activityTootal;

    @SerializedName("fees_for_booking")
    @Expose
    private String feesForBooking;

    @SerializedName("total_price")
    @Expose
    private String totalPrice;

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
    private String isObjected = "0";
    @SerializedName("objection_reason")
    @Expose
    private String objectionReason;
    @JsonAdapter(OrderStatusTag.class)
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("is_local_set_meeting_loc")
    @Expose
    private String isLocalSetMeetingLoc;
    @SerializedName("is_local_set_duration")
    @Expose
    private String isLocalSetDuration;
    @SerializedName("canceled_by")
    @Expose
    private String canceledBy;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("activities")
    @Expose
    private String activities;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("participant_count")
    @Expose
    private Integer participantCount;
    @SerializedName("participant")
    @Expose
    private List<AddParticipants> participant = new ArrayList<>();
    @SerializedName("sports")
    @Expose
    private List<Service> sports = new ArrayList<>();

    @SerializedName("price_rule")
    @Expose
    private SportPriceRules sportPriceRules;
    @SerializedName("is_published")
    @Expose
    private String isPublished;
    @SerializedName("blog_id")
    @Expose
    private String blogId;


    @SerializedName("commision_perc")
    @Expose
    private String commisionPerc;

    @SerializedName("commision_value")
    @Expose
    private String commisionValue;

    @SerializedName("fees_for_booking_perc")
    @Expose
    private String feesForBookingPerc;

    @SerializedName("is_objection_detail")
    @Expose
    private String isObjDetails;

    @SerializedName("objection_detail")
    @Expose
    private ObjectionDetails objectionDetails;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    public List<AddParticipants> getParticipant() {
        return participant;
    }

    public void setParticipant(List<AddParticipants> participant) {
        this.participant = participant;
    }

    public List<Service> getSports() {
        return sports;
    }

    public void setSports(List<Service> sports) {
        this.sports = sports;
    }

    public SportPriceRules getSportPriceRules() {
        return sportPriceRules;
    }

    public void setSportPriceRules(SportPriceRules sportPriceRules) {
        this.sportPriceRules = sportPriceRules;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
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

    public String getActivityTootal() {
        return activityTootal;
    }

    public void setActivityTootal(String activityTootal) {
        this.activityTootal = activityTootal;
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

    public String getIsObjDetails() {
        return isObjDetails;
    }

    public void setIsObjDetails(String isObjDetails) {
        this.isObjDetails = isObjDetails;
    }

    public ObjectionDetails getObjectionDetails() {
        return objectionDetails;
    }

    public void setObjectionDetails(ObjectionDetails objectionDetails) {
        this.objectionDetails = objectionDetails;
    }

    @SerializedName("device_type")
    @Expose
    private String deviceType = "A";

    @SerializedName("device_token")
    @Expose
    private String deviceToken = "123Abc";


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @SerializedName("is_review")
    @Expose
    private String isReview = "0";


    @SerializedName("review")
    @Expose
    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
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


    @SerializedName("experience")
    @Expose
    private ExpereinceNew expereinceNew;

    public ExpereinceNew getExpereinceNew() {
        return expereinceNew;
    }

    public void setExpereinceNew(ExpereinceNew expereinceNew) {
        this.expereinceNew = expereinceNew;
    }

    @SerializedName("is_multiple_day")
    @Expose
    private String inMultiDay = "0";

    public String getInMultiDay() {
        return inMultiDay;
    }

    public void setInMultiDay(String inMultiDay) {
        this.inMultiDay = inMultiDay;
    }

    @SerializedName("check_list_status")
    @Expose
    private String checkListStatus = "1";

    public String getCheckListStatus() {
        return checkListStatus;
    }

    public void setCheckListStatus(String checkListStatus) {
        this.checkListStatus = checkListStatus;
    }


    @SerializedName("is_rated")
    @Expose
    private String isRated = "0";

    public String getIsRated() {
        return isRated;
    }

    public void setIsRated(String isRated) {
        this.isRated = isRated;
    }


    @SerializedName("cancel_per")
    @Expose
    private String cancelPer = "0";

    public String getCancelPer() {
        return cancelPer;
    }

    public void setCancelPer(String cancelPer) {
        this.cancelPer = cancelPer;
    }

    @SerializedName("cancel_reason")
    @Expose
    private String cancelReason = "";

    public String getCancelReason() {
        return cancelReason.trim();
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }


    @SerializedName("is_referral")
    @Expose
    private String isReferral = "0";


    @SerializedName("referral_amount")
    @Expose
    private String referralAmount = "0.00";

    @SerializedName("referral_percentage")
    @Expose
    private String referralPercentage = "0.00";


    public String getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(String isReferral) {
        this.isReferral = isReferral;
    }

    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getReferralPercentage() {
        return referralPercentage;
    }

    public void setReferralPercentage(String referralPercentage) {
        this.referralPercentage = referralPercentage;
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


    @SerializedName("is_receipt")
    @Expose
    private String isReceipt = "0";


    @SerializedName("objection_id")
    @Expose
    private String objectionId = "0";

    public String getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(String objectionId) {
        this.objectionId = objectionId;
    }

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("country_code")
    @Expose
    private String countryCode = "+41";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt;
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
