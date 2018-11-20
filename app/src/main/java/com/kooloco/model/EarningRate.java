package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 7/10/17.
 */

public class EarningRate {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("option_text")
    @Expose
    String name = "0";

    @SerializedName("improve")
    @Expose
    String improved = "0";

    @SerializedName("wrong")
    @Expose
    String wrong = "0";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImproved() {
        return improved;
    }

    public void setImproved(String improved) {
        this.improved = improved;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

