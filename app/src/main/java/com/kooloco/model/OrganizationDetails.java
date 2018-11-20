package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 20/9/17.
 */

public class OrganizationDetails {
    @SerializedName("id")
    @Expose
    private String id = "";


    @SerializedName("logo")
    @Expose
    private String imageUrl = "";

    @SerializedName("name")
    @Expose
    private String orgName = "";

    @SerializedName("activity")
    @Expose
    private List<Activities> activities=new ArrayList<>();

    @SerializedName("sports")
    @Expose
    private List<Service> services=new ArrayList<>();

    @SerializedName("local_sports")
    @Expose
    private List<Service> servicesNew=new ArrayList<>();

    private String orgActivityTypes = "";

    private String orgSportTypes = "";

    @SerializedName("location")
    @Expose
    private String orgLocation = "";

    @SerializedName("latitude")
    @Expose
    private String orgLatitude = "";

    @SerializedName("longitude")
    @Expose
    private String orgLongitude = "";

    @SerializedName("description")
    @Expose
    private String orgDescripation = "";

    @SerializedName("locals")
    @Expose
    private List<OrgLocal> orgLocals;

    @SerializedName("media")
    @Expose
    private List<OrgImage> orgImage;


    @SerializedName("site_url")
    @Expose
    private String siteUrl = "";

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgActivityTypes() {
        return orgActivityTypes;
    }

    public void setOrgActivityTypes(String orgActivityTypes) {
        this.orgActivityTypes = orgActivityTypes;
    }

    public String getOrgSportTypes() {
        return orgSportTypes;
    }

    public void setOrgSportTypes(String orgSportTypes) {
        this.orgSportTypes = orgSportTypes;
    }

    public String getOrgLocation() {
        return orgLocation;
    }

    public void setOrgLocation(String orgLocation) {
        this.orgLocation = orgLocation;
    }

    public List<OrgLocal> getOrgLocals() {
        return orgLocals;
    }

    public void setOrgLocals(List<OrgLocal> orgLocals) {
        this.orgLocals = orgLocals;
    }

    public List<OrgImage> getOrgImage() {
        return orgImage;
    }

    public void setOrgImage(List<OrgImage> orgImage) {
        this.orgImage = orgImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getOrgLatitude() {
        return orgLatitude;
    }

    public void setOrgLatitude(String orgLatitude) {
        this.orgLatitude = orgLatitude;
    }

    public String getOrgLongitude() {
        return orgLongitude;
    }

    public void setOrgLongitude(String orgLongitude) {
        this.orgLongitude = orgLongitude;
    }

    public String getOrgDescripation() {
        return orgDescripation;
    }

    public void setOrgDescripation(String orgDescripation) {
        this.orgDescripation = orgDescripation;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }



    @SerializedName("is_approved")
    @Expose
    private String isApprove = "0";

    public String getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(String isApprove) {
        this.isApprove = isApprove;
    }

    public List<Service> getServicesNew() {
        return servicesNew;
    }

    public void setServicesNew(List<Service> servicesNew) {
        this.servicesNew = servicesNew;
    }
}
