package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 25/5/18.
 */

public class CancellationNewPolicy {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("duration_to")
    @Expose
    private String title;

    @SerializedName("refund_perc")
    @Expose
    private String refaund;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRefaund() {
        return refaund;
    }

    public void setRefaund(String refaund) {
        this.refaund = refaund;
    }
}
