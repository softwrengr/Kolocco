package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 16/4/18.
 */

public class OtherDetailsAnotherFields {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("experience_id")
    @Expose
    private String experienceId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String desc;

    @SerializedName("media")
    @Expose
    private List<Medium> media=new ArrayList<>();

    private List<AddImages> addImages=new ArrayList<>();

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

    public List<AddImages> getAddImages() {
        return addImages;
    }

    public void setAddImages(List<AddImages> addImages) {
        this.addImages = addImages;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }
}
