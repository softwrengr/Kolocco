package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 9/3/18.
 */

public class ReviewData {
    @SerializedName("review_count")
    @Expose
    private String reviewCount = "0";
    @SerializedName("list")
    @Expose
    private List<Review> list = new ArrayList<>();

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Review> getList() {
        return list;
    }

    public void setList(List<Review> list) {
        this.list = list;
    }
}
