package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.StringToBoolean;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hlink44 on 19/9/17.
 */

public class DashboardDetails {
    @SerializedName("sports_images")
    @Expose
    private List<LocalImage> localImages;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("profile_image")
    @Expose
    private String imageUrl = "";

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    private String localName = "";

    @SerializedName("rate")
    @Expose
    private String localRating = "";

    @SerializedName("intro_your_self")
    @Expose
    private String localNTMY = "";

    private String timeMon = "";
    private String timeTue = "";
    private String timeWed = "";
    private String timeThu = "";
    private String timeFri = "";
    private String timeSat = "";
    private String timeSun = "";

    @SerializedName("experience")
    @Expose
    private List<ServiceType> servicesTypes;

    @SerializedName("languages")
    @Expose
    private String localLanguages = "";

    @SerializedName("distance")
    @Expose
    private String localDistance = "";

    @SerializedName("location_address")
    @Expose
    private String localLocation = "";


    @SerializedName("sports")
    @Expose
    private List<Service> services;

    @SerializedName("certificates")
    @Expose
    private List<CertificateInfo> certificateInfos = new ArrayList<>();

    @SerializedName("achievements")
    @Expose
    private List<CertificateInfo> AchivmentsInfo = new ArrayList<>();

    private String orgName = "";
    private String orgDes = "";
    private String orgImage = "";
    private String orgAddress = "";

    @SerializedName("title")
    @Expose
    private String cancelPolicy = "";

    @SerializedName("description")
    @Expose
    private String cancelDes = "";

    private boolean isFav = false;

    @SerializedName("schedule")
    @Expose
    List<SchduleDashboard> schduleDashboards = new ArrayList<>();

    @SerializedName("organisation_detail")
    @Expose
    private OrganizationVisitor organizationVisitor;

    @SerializedName("is_affilated")
    @Expose
    private String isAffilated = "0";

    public String getIsAffilated() {
        return isAffilated;
    }

    public void setIsAffilated(String isAffilated) {
        this.isAffilated = isAffilated;
    }

    private List<Review> reviews = new ArrayList<>();

    public List<LocalImage> getLocalImages() {
        return localImages;
    }

    public void setLocalImages(List<LocalImage> localImages) {
        this.localImages = localImages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocalRating() {
        return localRating;
    }

    public void setLocalRating(String localRating) {
        this.localRating = localRating;
    }

    public String getLocalNTMY() {
        return localNTMY;
    }

    public void setLocalNTMY(String localNTMY) {
        this.localNTMY = localNTMY;
    }

    public String getTimeMon() {
        return timeMon;
    }

    public void setTimeMon(String timeMon) {
        this.timeMon = timeMon;
    }

    public String getTimeTue() {
        return timeTue;
    }

    public void setTimeTue(String timeTue) {
        this.timeTue = timeTue;
    }

    public String getTimeWed() {
        return timeWed;
    }

    public void setTimeWed(String timeWed) {
        this.timeWed = timeWed;
    }

    public String getTimeThu() {
        return timeThu;
    }

    public void setTimeThu(String timeThu) {
        this.timeThu = timeThu;
    }

    public String getTimeFri() {
        return timeFri;
    }

    public void setTimeFri(String timeFri) {
        this.timeFri = timeFri;
    }

    public String getTimeSat() {
        return timeSat;
    }

    public void setTimeSat(String timeSat) {
        this.timeSat = timeSat;
    }

    public String getTimeSun() {
        return timeSun;
    }

    public void setTimeSun(String timeSun) {
        this.timeSun = timeSun;
    }

    public List<ServiceType> getServicesTypes() {
        return servicesTypes;
    }

    public void setServicesTypes(List<ServiceType> servicesTypes) {
        this.servicesTypes = servicesTypes;
    }

    public String getLocalLanguages() {
        return localLanguages;
    }

    public void setLocalLanguages(String localLanguages) {
        this.localLanguages = localLanguages;
    }

    public String getLocalDistance() {
        return localDistance + " km";
    }

    public void setLocalDistance(String localDistance) {
        this.localDistance = localDistance;
    }

    public String getLocalLocation() {
        return localLocation;
    }

    public void setLocalLocation(String localLocation) {
        this.localLocation = localLocation;
    }

    public List<CertificateInfo> getCertificateInfos() {
        return certificateInfos;
    }

    public void setCertificateInfos(List<CertificateInfo> certificateInfos) {
        this.certificateInfos = certificateInfos;
    }

    public List<CertificateInfo> getAchivmentsInfo() {
        return AchivmentsInfo;
    }

    public void setAchivmentsInfo(List<CertificateInfo> achivmentsInfo) {
        AchivmentsInfo = achivmentsInfo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDes() {
        return orgDes;
    }

    public void setOrgDes(String orgDes) {
        this.orgDes = orgDes;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public String getCancelDes() {
        return cancelDes;
    }

    public void setCancelDes(String cancelDes) {
        this.cancelDes = cancelDes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getLocalName() {
        return firstname + " " + lastname;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getOrgImage() {
        return orgImage;
    }

    public void setOrgImage(String orgImage) {
        this.orgImage = orgImage;
    }

    public List<Service> getServices() {
        return services;
    }


    public void setServices(List<Service> services) {
        this.services = services;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public List<SchduleDashboard> getSchduleDashboards() {
        return schduleDashboards;
    }

    public void setSchduleDashboards(List<SchduleDashboard> schduleDashboards) {
        this.schduleDashboards = schduleDashboards;
    }

    @SerializedName("currancy")
    @Expose
    private String currency = "";

    @SerializedName("appointment_count")
    @Expose
    private String appointmentCount = "";

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(String appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @SerializedName("location_longitude")
    @Expose
    private String longitude = "0.0";

    @SerializedName("location_latitude")
    @Expose
    private String latitude = "0.0";

    @SerializedName("location_area")
    @Expose
    private String activityArea = "";

    @SerializedName("location_city")
    @Expose
    private String localCity = "";

    @SerializedName("location_state")
    @Expose
    private String localState = "";

    @SerializedName("location_country")
    @Expose
    private String localCountry = "";

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(String activityArea) {
        this.activityArea = activityArea;
    }

    public OrganizationVisitor getOrganizationVisitor() {
        return organizationVisitor;
    }

    public void setOrganizationVisitor(OrganizationVisitor organizationVisitor) {
        this.organizationVisitor = organizationVisitor;
    }

    @SerializedName("low_price")
    @Expose
    private String lowPrice = "";

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getLocalCity() {
        return localCity;
    }

    public void setLocalCity(String localCity) {
        this.localCity = localCity;
    }

    public String getLocalState() {
        return localState;
    }

    public void setLocalState(String localState) {
        this.localState = localState;
    }

    public String getLocalCountry() {
        return localCountry;
    }

    public void setLocalCountry(String localCountry) {
        this.localCountry = localCountry;
    }



    @SerializedName("is_admin_approve")
    @Expose
    private String isAdminApprove = "0";


    @SerializedName("is_want_to_complete")
    @Expose
    private String isWantToCompelte = "0";

    public String getIsWantToCompelte() {
        return isWantToCompelte;
    }

    public void setIsWantToCompelte(String isWantToCompelte) {
        this.isWantToCompelte = isWantToCompelte;
    }

    @SerializedName("profile_status")
    @Expose
    private List<ProfileStatus> profileStatuses = new ArrayList<>();

    public List<ProfileStatus> getProfileStatuses() {
        return profileStatuses;
    }

    public void setProfileStatuses(List<ProfileStatus> profileStatuses) {
        this.profileStatuses = profileStatuses;
    }

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
    }


    @SerializedName("experiences")
    @Expose
    private List<ExpereinceNew> expereinceNews = new ArrayList<>();

    public List<ExpereinceNew> getExpereinceNews() {
        return expereinceNews;
    }

    public void setExpereinceNews(List<ExpereinceNew> expereinceNews) {
        this.expereinceNews = expereinceNews;
    }


    @SerializedName("is_bank")
    @Expose
    private String isBank="0";

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    @SerializedName("country_code")
    @Expose
    private String countryCode="+41";

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
