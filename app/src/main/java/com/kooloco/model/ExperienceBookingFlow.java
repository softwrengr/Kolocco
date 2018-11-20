package com.kooloco.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 9/5/18.
 */

public class ExperienceBookingFlow {

    //It is used to set data local
    private String localId = "";
    private String localName = "";
    private String localProfile = "";
    private String localRating = "0.0";

    private List<ExpereinceNew> expereinceNews = new ArrayList<>();


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

    public String getLocalProfile() {
        return localProfile;
    }

    public void setLocalProfile(String localProfile) {
        this.localProfile = localProfile;
    }

    public List<ExpereinceNew> getExpereinceNews() {
        return expereinceNews;
    }

    public void setExpereinceNews(List<ExpereinceNew> expereinceNews) {
        this.expereinceNews = expereinceNews;
    }

    public String getLocalRating() {
        return localRating;
    }

    public void setLocalRating(String localRating) {
        this.localRating = localRating;
    }

    //It is used to set data exp

    private ExpereinceNew expereinceNew;


    public ExpereinceNew getExpereinceNew() {
        return expereinceNew;
    }

    public void setExpereinceNew(ExpereinceNew expereinceNew) {
        this.expereinceNew = expereinceNew;
    }


    private boolean isSelectDateExp = false;

    private java.util.Date dateExp;

    public boolean isSelectDateExp() {
        return isSelectDateExp;
    }

    public void setSelectDateExp(boolean selectDateExp) {
        isSelectDateExp = selectDateExp;
    }

    public java.util.Date getDateExp() {
        return dateExp;
    }

    public void setDateExp(java.util.Date dateExp) {
        this.dateExp = dateExp;
    }


    //It is used to get Exp booking flow


    private String selectDate = "";

    private SchedulePrice schedulePrice;

    public SchedulePrice getSchedulePrice() {
        return schedulePrice;
    }

    public void setSchedulePrice(SchedulePrice schedulePrice) {
        this.schedulePrice = schedulePrice;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    //Experience list

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

    private String feesForBooking = "";
    private String tootalPriceActivity = "";

    private String tootalPrice = "";

    private String commisionPer = "";
    private String commisionValue = "";
    private String feesForBookingPer = "";

    private String refferalAmount = "";

    private String isRefferal = "0";

    private String setRefferalTotal = "0.00";

    private String referralPer = "0.00";

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

    public String getRefferalAmount() {
        return refferalAmount;
    }

    public void setRefferalAmount(String refferalAmount) {
        this.refferalAmount = refferalAmount;
    }

    public String getIsRefferal() {
        return isRefferal;
    }

    public void setIsRefferal(String isRefferal) {
        this.isRefferal = isRefferal;
    }

    public String getSetRefferalTotal() {
        return setRefferalTotal;
    }

    public void setSetRefferalTotal(String setRefferalTotal) {
        this.setRefferalTotal = setRefferalTotal;
    }

    public String getReferralPer() {
        return referralPer;
    }

    public void setReferralPer(String referralPer) {
        this.referralPer = referralPer;
    }

    //More about appointment summery

    private String moreAbout = "";

    public String getMoreAbout() {
        return moreAbout;
    }

    public void setMoreAbout(String moreAbout) {
        this.moreAbout = moreAbout;
    }


}
