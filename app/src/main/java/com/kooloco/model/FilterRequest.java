package com.kooloco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 31/1/18.
 */

public class FilterRequest {

    private String sportId = "";

    private String sportName = "";

    private String date = "";

    private String startTime = "";

    private String experienceId = "";

    private String activityName = "";

    private String languageId = "";

    private String languageName = "";


    private String priceMin = "";

    private String priceMax = "";

    private String rateMin = "";

    private String rateMax = "";

    private String latitude = "";

    private String longitude = "";

    private String city = "";

    private String country = "";

    private String addres = "";

    private String durationText = "";
    private String durationId = "";

    private String recomanText = "";
    private String recomanId = "";

    private String perfectForText = "";
    private String perfectId = "";


    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getRateMin() {
        return rateMin;
    }

    public void setRateMin(String rateMin) {
        this.rateMin = rateMin;
    }

    public String getRateMax() {
        return rateMax;
    }

    public void setRateMax(String rateMax) {
        this.rateMax = rateMax;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public String getDurationId() {
        return durationId;
    }

    public void setDurationId(String durationId) {
        this.durationId = durationId;
    }

    public String getRecomanText() {
        return recomanText;
    }

    public void setRecomanText(String recomanText) {
        this.recomanText = recomanText;
    }

    public String getRecomanId() {
        return recomanId;
    }

    public void setRecomanId(String recomanId) {
        this.recomanId = recomanId;
    }

    public String getPerfectForText() {
        return perfectForText;
    }

    public void setPerfectForText(String perfectForText) {
        this.perfectForText = perfectForText;
    }

    public String getPerfectId() {
        return perfectId;
    }

    public void setPerfectId(String perfectId) {
        this.perfectId = perfectId;
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

    private List<Service> services=new ArrayList<>();

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
