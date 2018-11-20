package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 18/4/18.
 */

public class ExpDetails {
    @SerializedName("id")
    @Expose
    private String id = "";

    private List<LocalImage> images = new ArrayList<>();


    @SerializedName("local_id")
    @Expose
    private String localId = "";

    @SerializedName("media")
    @Expose
    private List<Medium> media=new ArrayList<>();

    @SerializedName("title")
    @Expose
    private String Title = "";

    @SerializedName("rate")
    @Expose
    private String rating = "0";

    private String duration = "";

    @SerializedName("review_count")
    @Expose
    private String ratingCount = "0";

    @SerializedName("city")
    @Expose
    private String city = "";

    @SerializedName("country")
    @Expose
    private String country = "";

    private String price = "";

    @SerializedName("maximum_participant")
    @Expose
    private String capacity = "0";

    @SerializedName("description")
    @Expose
    private String desc = "";

    @SerializedName("sport")
    @Expose
    private Service sportActivity;

    @SerializedName("activity")
    @Expose
    private Activities activities;

    @SerializedName("activity_latitude")
    @Expose
    private String meetingLet = "0.0";

    @SerializedName("activity_longitude")
    @Expose
    private String meetingLng = "0.0";

    @SerializedName("activity_address")
    @Expose
    private String mettingAddress = "";


    @SerializedName("highlight")
    @Expose
    private String heighlight = "";

    @SerializedName("included")
    @Expose
    private String include = "";

    @SerializedName("not_included")
    @Expose
    private String notInclude = "";

    private List<CalendarDay> calendarDays = new ArrayList<>();

    private String localName = "";
    private String localImage = "";
    private String localDesc = "";
    private String localLanguage = "";


    @SerializedName("extra_fields")
    @Expose
    private List<OtherDetailsAnotherFields> otherDetailsAnotherFields = new ArrayList<>();

    @SerializedName("cancellation_title")
    @Expose
    private String cancelletionPolicyName = "";
    @SerializedName("cancellation_policy_description")
    @Expose
    private String cancelletionPolicyDesc = "";

    @SerializedName("cancellation_policy_detail")
    @Expose
    private List<CancellationNewPolicy> cancelletionPolicyList = new ArrayList<>();



    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<>();

    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = new ArrayList<>();

    @SerializedName("calender_dates")
    @Expose
    List<String> calenderDates = new ArrayList<>();

    @SerializedName("local_info")
    @Expose
    private LocalInfoNew localInfoNew;

    public List<LocalImage> getImages() {
        return images;
    }

    public void setImages(List<LocalImage> images) {
        this.images = images;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Service getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(Service sportActivity) {
        this.sportActivity = sportActivity;
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }

    public String getMeetingLet() {
        return meetingLet;
    }

    public void setMeetingLet(String meetingLet) {
        this.meetingLet = meetingLet;
    }

    public String getMeetingLng() {
        return meetingLng;
    }

    public void setMeetingLng(String meetingLng) {
        this.meetingLng = meetingLng;
    }

    public String getMettingAddress() {
        return mettingAddress;
    }

    public void setMettingAddress(String mettingAddress) {
        this.mettingAddress = mettingAddress;
    }

    public String getHeighlight() {
        return heighlight;
    }

    public void setHeighlight(String heighlight) {
        this.heighlight = heighlight;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getNotInclude() {
        return notInclude;
    }

    public void setNotInclude(String notInclude) {
        this.notInclude = notInclude;
    }

    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(List<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalImage() {
        return localImage;
    }

    public void setLocalImage(String localImage) {
        this.localImage = localImage;
    }

    public String getLocalDesc() {
        return localDesc;
    }

    public void setLocalDesc(String localDesc) {
        this.localDesc = localDesc;
    }

    public String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage(String localLanguage) {
        this.localLanguage = localLanguage;
    }

    public List<OtherDetailsAnotherFields> getOtherDetailsAnotherFields() {
        return otherDetailsAnotherFields;
    }

    public void setOtherDetailsAnotherFields(List<OtherDetailsAnotherFields> otherDetailsAnotherFields) {
        this.otherDetailsAnotherFields = otherDetailsAnotherFields;
    }

    public String getCancelletionPolicyName() {
        return cancelletionPolicyName;
    }

    public void setCancelletionPolicyName(String cancelletionPolicyName) {
        this.cancelletionPolicyName = cancelletionPolicyName;
    }

    public String getCancelletionPolicyDesc() {
        return cancelletionPolicyDesc;
    }

    public void setCancelletionPolicyDesc(String cancelletionPolicyDesc) {
        this.cancelletionPolicyDesc = cancelletionPolicyDesc;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    @SerializedName("recommended_leve")
    @Expose
    private String expLevel = "";

    @SerializedName("prefered_for")
    @Expose
    private String expPerfect = "";

    public String getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(String expLevel) {
        this.expLevel = expLevel;
    }

    public String getExpPerfect() {
        return expPerfect;
    }

    public void setExpPerfect(String expPerfect) {
        this.expPerfect = expPerfect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public List<String> getCalenderDates() {
        return calenderDates;
    }

    public void setCalenderDates(List<String> calenderDates) {
        this.calenderDates = calenderDates;
    }

    public LocalInfoNew getLocalInfoNew() {
        return localInfoNew;
    }

    public void setLocalInfoNew(LocalInfoNew localInfoNew) {
        this.localInfoNew = localInfoNew;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    private String localRate="0.0";

    public String getLocalRate() {
        return localRate;
    }

    public void setLocalRate(String localRate) {
        this.localRate = localRate;
    }

    public List<CancellationNewPolicy> getCancelletionPolicyList() {
        return cancelletionPolicyList;
    }

    public void setCancelletionPolicyList(List<CancellationNewPolicy> cancelletionPolicyList) {
        this.cancelletionPolicyList = cancelletionPolicyList;
    }

    @SerializedName("is_favorite")
    @Expose
    private String isFavorite = "0";

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }
}
