package com.kooloco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 13/12/17.
 */

public class VisitorBooking {

    //First screen to get data
    private String localId = "0";
    private String localName = "";
    private String localImage = "";
    private String localLati = "0.0";
    private String localLang = "0.0";
    private String localAddress = "";
    private String moreAbout = " ";

    private String rating = "";

    private String localLatiOld = "0.0";
    private String localLangOld = "0.0";
    private String localAddressOld = "";

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

    public String getLocalLati() {
        return localLati;
    }

    public void setLocalLati(String localLati) {
        this.localLati = localLati;
    }

    public String getLocalLang() {
        return localLang;
    }

    public void setLocalLang(String localLang) {
        this.localLang = localLang;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLocalLatiOld() {
        return localLatiOld;
    }

    public void setLocalLatiOld(String localLatiOld) {
        this.localLatiOld = localLatiOld;
    }

    public String getLocalLangOld() {
        return localLangOld;
    }

    public void setLocalLangOld(String localLangOld) {
        this.localLangOld = localLangOld;
    }

    public String getLocalAddressOld() {
        return localAddressOld;
    }

    public void setLocalAddressOld(String localAddressOld) {
        this.localAddressOld = localAddressOld;
    }

    //Select activity screen

    //Experience Data

    private String experienceId = "";

    private String experienceTitle = "";

    private String experienceDesc = "";

    private String durationExperience = "";

    private String priceExperience = "";

    private String isGroupBookingExperience = "1";

    private String participantPerExperience = "";

    private String participantExperience = "";

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

    public String getExperienceDesc() {
        return experienceDesc;
    }

    public void setExperienceDesc(String experienceDesc) {
        this.experienceDesc = experienceDesc;
    }

    public String getDurationExperience() {
        return durationExperience;
    }

    public void setDurationExperience(String durationExperience) {
        this.durationExperience = durationExperience;
    }

    public String getPriceExperience() {
        return priceExperience;
    }

    public void setPriceExperience(String priceExperience) {
        this.priceExperience = priceExperience;
    }

    public String getIsGroupBookingExperience() {
        return isGroupBookingExperience;
    }

    public void setIsGroupBookingExperience(String isGroupBookingExperience) {
        this.isGroupBookingExperience = isGroupBookingExperience;
    }

    public String getParticipantPerExperience() {
        return participantPerExperience;
    }

    public void setParticipantPerExperience(String participantPerExperience) {
        this.participantPerExperience = participantPerExperience;
    }

    public String getParticipantExperience() {
        return participantExperience;
    }

    public void setParticipantExperience(String participantExperience) {
        this.participantExperience = participantExperience;
    }

    //Service data

    private String sportId = "";

    private String sportName = "";


    private String sportImage = "";


    private String sportMainName = "";

    private String sportSubName = "";


    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportImage() {
        return sportImage;
    }

    public String getSportSubName() {
        return sportSubName;
    }

    public void setSportSubName(String sportSubName) {
        this.sportSubName = sportSubName;
    }

    public void setSportImage(String sportImage) {
        this.sportImage = sportImage;
    }

    public String getSportMainName() {
        return sportMainName;
    }

    public void setSportMainName(String sportMainName) {
        this.sportMainName = sportMainName;
    }

    //Select Date time

    private String date = "";
    private Time startTime = null;
    private Time endTime = null;

    private String userSelectTime = "";

    private boolean isLocalSelectTime = false;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getUserSelectTime() {
        return userSelectTime;
    }

    public void setUserSelectTime(String userSelectTime) {
        this.userSelectTime = userSelectTime;
    }

    public boolean isLocalSelectTime() {
        return isLocalSelectTime;
    }

    public void setLocalSelectTime(boolean localSelectTime) {
        isLocalSelectTime = localSelectTime;
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

    //wather data

    private String wather = "";

    public String getWather() {
        return wather;
    }

    public void setWather(String wather) {
        this.wather = wather;
    }

    //Local select address

    private boolean isLocalSelectAddress = false;

    public boolean isLocalSelectAddress() {
        return isLocalSelectAddress;
    }

    public void setLocalSelectAddress(boolean localSelectAddress) {
        isLocalSelectAddress = localSelectAddress;
    }

    public String getMoreAbout() {
        return moreAbout;
    }

    public void setMoreAbout(String moreAbout) {
        this.moreAbout = moreAbout;
    }

    //Visitor Booking New Flow
}
