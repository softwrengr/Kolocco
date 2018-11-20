package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 30/3/18.
 */

public class OrderDashboard {

    @SerializedName("id")
    @Expose
    private List<OrderOrg> orderOrgs=new ArrayList<>();

    public List<OrderOrg> getOrderOrgs() {
        return orderOrgs;
    }

    public void setOrderOrgs(List<OrderOrg> orderOrgs) {
        this.orderOrgs = orderOrgs;
    }
}
