package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 20/1/18.
 */

public class LocalInfo {
    @SerializedName("local_id")
    @Expose
    private String localId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("intro_your_self")
    @Expose
    private String introYourSelf;
    @SerializedName("profile_image_thumb")
    @Expose
    private String profileImageThumb;

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getIntroYourSelf() {
        return introYourSelf;
    }

    public void setIntroYourSelf(String introYourSelf) {
        this.introYourSelf = introYourSelf;
    }

    public String getProfileImageThumb() {
        return profileImageThumb;
    }

    public void setProfileImageThumb(String profileImageThumb) {
        this.profileImageThumb = profileImageThumb;
    }
}
