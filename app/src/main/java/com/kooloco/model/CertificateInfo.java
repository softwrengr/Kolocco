package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 19/9/17.
 */

public class CertificateInfo {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("image_url")
    @Expose
    private String imageUrl = "";

    @SerializedName("title")
    @Expose
    private String name = "";

    @SerializedName("description")
    @Expose
    private String desc = "";

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @SerializedName("sport_name")
    @Expose
    private String type = "";

    @SerializedName("sub_sport_name")
    @Expose
    private String subType = "";

    @SerializedName("tags")
    @Expose
    private List<Tag> tags = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
