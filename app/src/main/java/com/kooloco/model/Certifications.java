package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 3/10/17.
 */

public class Certifications {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("image_url")
    @Expose
    private String imageUrl = "";

    @SerializedName("title")
    @Expose
    private String titles = "";

    @SerializedName("sport_name")
    @Expose
    private String type = "";

    @SerializedName("sub_sport_name")
    @Expose
    private String subType = "";

    @SerializedName("description")
    @Expose
    private String desc = "";

    @SerializedName("sport_type_id")
    @Expose
    private String sportTypeId;

    @SerializedName("sport_sub_type_id")
    @Expose
    private String sportSubTypeId;


    private SportActivity sportActivity;

    private SportSubActivity sportSubActivity;

    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;


    private String addImageId = "0";


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public SportActivity getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(SportActivity sportActivity) {
        this.sportActivity = sportActivity;
    }

    public SportSubActivity getSportSubActivity() {
        return sportSubActivity;
    }

    public void setSportSubActivity(SportSubActivity sportSubActivity) {
        this.sportSubActivity = sportSubActivity;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getAddImageId() {
        return addImageId;
    }

    public void setAddImageId(String addImageId) {
        this.addImageId = addImageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportTypeId() {
        return sportTypeId;
    }

    public void setSportTypeId(String sportTypeId) {
        this.sportTypeId = sportTypeId;
    }

    public String getSportSubTypeId() {
        return sportSubTypeId;
    }

    public void setSportSubTypeId(String sportSubTypeId) {
        this.sportSubTypeId = sportSubTypeId;
    }
}
