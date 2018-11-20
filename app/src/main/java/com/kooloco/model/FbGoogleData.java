package com.kooloco.model;

/**
 * Created by hlink44 on 18/11/17.
 */

public class FbGoogleData {

    private String isSocialId="";
    private String firstName="";
    private String lastName="";
    private String email="";
    private String imageUrl="";

    private String signType="";


    public String getIsSocialId() {
        return isSocialId;
    }

    public void setIsSocialId(String isSocialId) {
        this.isSocialId = isSocialId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
