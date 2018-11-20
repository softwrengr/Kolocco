package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 20/1/18.
 */

public class BlogDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("local_id")
    @Expose
    private String localId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("experience_date")
    @Expose
    private String experienceDate;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("experience_id")
    @Expose
    private String experienceId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("meeting_address")
    @Expose
    private String meetingAddress;
    @SerializedName("meeting_latitude")
    @Expose
    private String meetingLatitude;
    @SerializedName("meeting_longitude")
    @Expose
    private String meetingLongitude;
    @SerializedName("blog_media")
    @Expose
    private List<BlogMedia> blogMedia = new ArrayList<>();
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("is_like")
    @Expose
    private String isLike;
    @SerializedName("sport")
    @Expose
    private String sport;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("total_like")
    @Expose
    private String totalLike;

    @SerializedName("comments")
    @Expose
    private String totalComment = "0";

    @SerializedName("local_info")
    @Expose
    private LocalInfo localInfo;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExperienceDate() {
        return experienceDate;
    }

    public void setExperienceDate(String experienceDate) {
        this.experienceDate = experienceDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
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

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
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

    public List<BlogMedia> getBlogMedia() {
        return blogMedia;
    }

    public void setBlogMedia(List<BlogMedia> blogMedia) {
        this.blogMedia = blogMedia;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public String getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    public LocalInfo getLocalInfo() {
        return localInfo;
    }

    public void setLocalInfo(LocalInfo localInfo) {
        this.localInfo = localInfo;
    }
}
