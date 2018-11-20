package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 19/9/17.
 */

public class Review {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("blog_id")
    @Expose
    private String blogId;


    @SerializedName("firstname")
    @Expose
    private String firstname = "Biking in the";

    @SerializedName("lastname")
    @Expose
    private String lastname = "Boro";

    private String name = "";

    @SerializedName("format_date")
    @Expose
    private String date = "";

    @SerializedName("comment")
    @Expose
    private String desc = "";

    @SerializedName("profile_image")
    @Expose
    private String imageUrl = "";

    public String getName() {
        return firstname + " " + lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
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

    @SerializedName("rate")
    @Expose
    private String rate = "";

    @SerializedName("review")
    @Expose
    private String review = "";

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @SerializedName("insertdate")
    @Expose
    private String insertDate = "";

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}
