package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 28/3/18.
 */

public class OrganizationStaus {
    @SerializedName("organisation_detail")
    @Expose
    private OrganizationDetails organizationDetails;

    @SerializedName("steps")
    @Expose
    private List<OrganizationStep> organizationStauses = new ArrayList<>();

    public OrganizationDetails getOrganizationDetails() {
        return organizationDetails;
    }

    public void setOrganizationDetails(OrganizationDetails organizationDetails) {
        this.organizationDetails = organizationDetails;
    }

    public List<OrganizationStep> getOrganizationStauses() {
        return organizationStauses;
    }

    public void setOrganizationStauses(List<OrganizationStep> organizationStauses) {
        this.organizationStauses = organizationStauses;
    }
}
