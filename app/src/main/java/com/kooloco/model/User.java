package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.StringToBoolean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink21 on 23/6/17.
 */

public class User {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("signup_step")
    @Expose
    private String signupStep;
    @SerializedName("intro_your_self")
    @Expose
    private String introYourSelf;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("country_code")
    @Expose
    private String countryCode = "+41";
    @SerializedName("phone_doc")
    @Expose
    private String phoneDoc;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("signup_type")
    @Expose
    private String signupType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("app_lang_id")
    @Expose
    private String appLangId;

    @SerializedName("app_language")
    @Expose
    private String appLang = "";

    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("app_notification")
    @Expose
    private String appNotification;
    @SerializedName("mail_notification")
    @Expose
    private String mailNotification;
    @SerializedName("referral_code")
    @Expose
    private String referralCode = "";

    @SerializedName("referral_amount")
    @Expose
    private String referralAmount = "0.00";

    @SerializedName("is_admin_approve")
    @Expose
    private String isAdminApprove;
    @SerializedName("is_login")
    @Expose
    private String isLogin;
    @SerializedName("is_verifed")
    @Expose
    private String isVerifed;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;
    @SerializedName("profile_image_thumb")
    @Expose
    private String profileImageThumb;

    @SerializedName("add_image_id")
    @Expose
    private String addImagesId = "0";

    @SerializedName("is_organization")
    @Expose
    private String isOrgaization = "0";

    @SerializedName("is_affilated")
    @Expose
    private String isAffilated = "0";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignupStep() {
        return signupStep;
    }

    public void setSignupStep(String signupStep) {
        this.signupStep = signupStep;
    }

    public String getIntroYourSelf() {
        return introYourSelf;
    }

    public void setIntroYourSelf(String introYourSelf) {
        this.introYourSelf = introYourSelf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneDoc() {
        return phoneDoc;
    }

    public void setPhoneDoc(String phoneDoc) {
        this.phoneDoc = phoneDoc;
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

    public String getSignupType() {
        return signupType;
    }

    public void setSignupType(String signupType) {
        this.signupType = signupType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAppLangId() {
        return appLangId;
    }

    public void setAppLangId(String appLangId) {
        this.appLangId = appLangId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAppNotification() {
        return appNotification;
    }

    public void setAppNotification(String appNotification) {
        this.appNotification = appNotification;
    }

    public String getMailNotification() {
        return mailNotification;
    }

    public void setMailNotification(String mailNotification) {
        this.mailNotification = mailNotification;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getIsAdminApprove() {
        return isAdminApprove;
    }

    public void setIsAdminApprove(String isAdminApprove) {
        this.isAdminApprove = isAdminApprove;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsVerifed() {
        return isVerifed;
    }

    public void setIsVerifed(String isVerifed) {
        this.isVerifed = isVerifed;
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

    public String getProfileImageThumb() {
        return profileImageThumb;
    }

    public void setProfileImageThumb(String profileImageThumb) {
        this.profileImageThumb = profileImageThumb;
    }

    public String getAddImagesId() {
        return addImagesId;
    }

    public void setAddImagesId(String addImagesId) {
        this.addImagesId = addImagesId;
    }

    public String getIsOrgaization() {
        return isOrgaization;
    }

    public void setIsOrgaization(String isOrgaization) {
        this.isOrgaization = isOrgaization;
    }

    public String getIsAffilated() {
        return isAffilated;
    }

    public void setIsAffilated(String isAffilated) {
        this.isAffilated = isAffilated;
    }

    public String getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(String referralAmount) {
        this.referralAmount = referralAmount;
    }

    @SerializedName("review_list")
    @Expose
    private List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @SerializedName("rate_list")
    @Expose
    private List<RatingOption> ratingOptions = new ArrayList<>();

    public List<RatingOption> getRatingOptions() {
        return ratingOptions;
    }

    public void setRatingOptions(List<RatingOption> ratingOptions) {
        this.ratingOptions = ratingOptions;
    }


    @SerializedName("language")
    @Expose
    private String language = "";

    @SerializedName("appointment_count")
    @Expose
    private String appointmentCount = "";

    @SerializedName("currency")
    @Expose
    private String currency = "";

    @SerializedName("visitor_currency")
    @Expose
    private String vCurrency = "USD";

    @SerializedName("local_currency")
    @Expose
    private String lCurrency = "USD";


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(String appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    @SerializedName("sports")
    @Expose
    private List<Service> services = new ArrayList<>();

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @SerializedName("isSetOnBoarding")
    @Expose
    private String isOnBoarding = "0";

    public String getIsOnBoarding() {
        return isOnBoarding;
    }

    public void setIsOnBoarding(String isOnBoarding) {
        this.isOnBoarding = isOnBoarding;
    }

    @SerializedName("is_bank")
    @Expose
    private String isBank = "0";

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getvCurrency() {
        return vCurrency;
    }

    public void setvCurrency(String vCurrency) {
        this.vCurrency = vCurrency;
    }

    public String getlCurrency() {
        return lCurrency;
    }

    public void setlCurrency(String lCurrency) {
        this.lCurrency = lCurrency;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAppLang() {
        return appLang;
    }

    public void setAppLang(String appLang) {
        this.appLang = appLang;
    }
}
