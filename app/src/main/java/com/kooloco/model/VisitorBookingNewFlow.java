package com.kooloco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 23/4/18.
 */

public class VisitorBookingNewFlow {

    //First screen to get data
    private String localId = "0";
    private String localName = "";
    private String localImage = "";
    private String localRating = "";


    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public String getLocalRating() {
        return localRating;
    }

    public void setLocalRating(String localRating) {
        this.localRating = localRating;
    }

    //Experience Data

    private String experienceId = "";

    private String experienceTitle = "";

    private String experienceImage = "";

    private String experienceLet = "";
    private String experienceLng = "";
    private String experienceMeetingAddress = "";


    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getExperienceTitle() {
        return experienceTitle;
    }

    public void setExperienceTitle(String experienceTitle) {
        this.experienceTitle = experienceTitle;
    }

    public String getExperienceImage() {
        return experienceImage;
    }

    public void setExperienceImage(String experienceImage) {
        this.experienceImage = experienceImage;
    }

    public String getExperienceLet() {
        return experienceLet;
    }

    public void setExperienceLet(String experienceLet) {
        this.experienceLet = experienceLet;
    }

    public String getExperienceLng() {
        return experienceLng;
    }

    public void setExperienceLng(String experienceLng) {
        this.experienceLng = experienceLng;
    }

    public String getExperienceMeetingAddress() {
        return experienceMeetingAddress;
    }

    public void setExperienceMeetingAddress(String experienceMeetingAddress) {
        this.experienceMeetingAddress = experienceMeetingAddress;
    }

    //Select Date time


    private String expTimeSlotsId = "";
    private String expDate = "";
    private String expSelectTime = "";
    private String expStartTime = "";
    private String expEndTime = "";
    private String expDuration = "";
    private String expPrice = "";
    private String expPriceAddPart = "";

    private String expIsGroupBookingExperience = "1";

    private String expParticipants = "0";

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpTimeSlotsId() {
        return expTimeSlotsId;
    }

    public void setExpTimeSlotsId(String expTimeSlotsId) {
        this.expTimeSlotsId = expTimeSlotsId;
    }

    public String getExpSelectTime() {
        return expSelectTime;
    }

    public void setExpSelectTime(String expSelectTime) {
        this.expSelectTime = expSelectTime;
    }

    public String getExpPrice() {
        return expPrice;
    }

    public void setExpPrice(String expPrice) {
        this.expPrice = expPrice;
    }

    public String getExpIsGroupBookingExperience() {
        return expIsGroupBookingExperience;
    }

    public void setExpIsGroupBookingExperience(String expIsGroupBookingExperience) {
        this.expIsGroupBookingExperience = expIsGroupBookingExperience;
    }

    public String getExpParticipants() {
        return expParticipants;
    }

    public void setExpParticipants(String expParticipants) {
        this.expParticipants = expParticipants;
    }

    public String getExpStartTime() {
        return expStartTime;
    }

    public void setExpStartTime(String expStartTime) {
        this.expStartTime = expStartTime;
    }

    public String getExpEndTime() {
        return expEndTime;
    }

    public void setExpEndTime(String expEndTime) {
        this.expEndTime = expEndTime;
    }

    public String getExpPriceAddPart() {
        return expPriceAddPart;
    }

    public void setExpPriceAddPart(String expPriceAddPart) {
        this.expPriceAddPart = expPriceAddPart;
    }

    private String addParticipants = "[]";

    private List<AddParticipants> addParticipantsList = new ArrayList<>();

    public String getAddParticipants() {
        return addParticipants;
    }

    public void setAddParticipants(String addParticipants) {
        this.addParticipants = addParticipants;
    }

    public List<AddParticipants> getAddParticipantsList() {
        return addParticipantsList;
    }

    public void setAddParticipantsList(List<AddParticipants> addParticipantsList) {
        this.addParticipantsList = addParticipantsList;
    }

    public String getExpDuration() {
        return expDuration;
    }

    public void setExpDuration(String expDuration) {
        this.expDuration = expDuration;
    }


    private String price = "";
    private String additionalPricePerHour = "";
    private String feesForBooking = "";
    private String tootalPriceActivity = "";

    private String tootalPrice = "";

    private String commisionPer = "";
    private String commisionValue = "";
    private String feesForBookingPer = "";

    public String getTootalPrice() {
        return tootalPrice;
    }

    public void setTootalPrice(String tootalPrice) {
        this.tootalPrice = tootalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdditionalPricePerHour() {
        return additionalPricePerHour;
    }

    public void setAdditionalPricePerHour(String additionalPricePerHour) {
        this.additionalPricePerHour = additionalPricePerHour;
    }

    public String getFeesForBooking() {
        return feesForBooking;
    }

    public void setFeesForBooking(String feesForBooking) {
        this.feesForBooking = feesForBooking;
    }

    public String getTootalPriceActivity() {
        return tootalPriceActivity;
    }

    public void setTootalPriceActivity(String tootalPriceActivity) {
        this.tootalPriceActivity = tootalPriceActivity;
    }

    public String getCommisionPer() {
        return commisionPer;
    }

    public void setCommisionPer(String commisionPer) {
        this.commisionPer = commisionPer;
    }

    public String getCommisionValue() {
        return commisionValue;
    }

    public void setCommisionValue(String commisionValue) {
        this.commisionValue = commisionValue;
    }

    public String getFeesForBookingPer() {
        return feesForBookingPer;
    }

    public void setFeesForBookingPer(String feesForBookingPer) {
        this.feesForBookingPer = feesForBookingPer;
    }

}
