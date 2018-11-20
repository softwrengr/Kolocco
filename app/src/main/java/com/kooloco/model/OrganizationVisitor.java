package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 7/2/18.
 */

public class OrganizationVisitor {
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("organisation_id")
    @Expose
    private String organisationId;
    @SerializedName("organisation_name")
    @Expose
    private String organisationName;
    @SerializedName("organisation_description")
    @Expose
    private String organisationDescription;
    @SerializedName("organisation_logo")
    @Expose
    private String organisationLogo;
    @SerializedName("organisation_address")
    @Expose
    private String organisationAddress;

    @SerializedName("affilated_id")
    @Expose
    private String affilatedId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrganisationDescription() {
        return organisationDescription;
    }

    public void setOrganisationDescription(String organisationDescription) {
        this.organisationDescription = organisationDescription;
    }

    public String getOrganisationLogo() {
        return organisationLogo;
    }

    public void setOrganisationLogo(String organisationLogo) {
        this.organisationLogo = organisationLogo;
    }

    public String getOrganisationAddress() {
        return organisationAddress;
    }

    public void setOrganisationAddress(String organisationAddress) {
        this.organisationAddress = organisationAddress;
    }

    public String getAffilatedId() {
        return affilatedId;
    }

    public void setAffilatedId(String affilatedId) {
        this.affilatedId = affilatedId;
    }

}
