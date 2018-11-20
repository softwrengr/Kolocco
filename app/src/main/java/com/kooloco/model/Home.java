package com.kooloco.model;

import java.util.List;

/**
 * Created by hlink44 on 19/9/17.
 */

public class Home {
    private String localName = "";
    private String localRating = "";
    private String localDistance = "";
    private String imageProfile = "";
    private boolean isFav = false;

    private List<ServiceType> serviceTypes;
    private List<Service> services;
    private List<LocalImage> localImages;

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalRating() {
        return localRating;
    }

    public void setLocalRating(String localRating) {
        this.localRating = localRating;
    }

    public String getLocalDistance() {
        return localDistance;
    }

    public void setLocalDistance(String localDistance) {
        this.localDistance = localDistance;
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<LocalImage> getLocalImages() {
        return localImages;
    }

    public void setLocalImages(List<LocalImage> localImages) {
        this.localImages = localImages;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
