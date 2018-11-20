package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 28/11/17.
 */

public class SetSportPrice {
    @SerializedName("sport_id")
    @Expose
    private String sportId;
    @SerializedName("activity_id")
    @Expose
    private String experienceId;
    @SerializedName("price_per_hour")
    @Expose
    private String pricePerHour;
    @SerializedName("new_participant_perc")
    @Expose
    private String newParticipantPerc;
    @SerializedName("number_of_participant")
    @Expose
    private String numberOfParticipant;
    @SerializedName("minimum_duration")
    @Expose
    private String minimumDuration;
    @SerializedName("is_multiple_booking")
    @Expose
    private String isMultipleBooking;

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getNewParticipantPerc() {
        return newParticipantPerc;
    }

    public void setNewParticipantPerc(String newParticipantPerc) {
        this.newParticipantPerc = newParticipantPerc;
    }

    public String getNumberOfParticipant() {
        return numberOfParticipant;
    }

    public void setNumberOfParticipant(String numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getMinimumDuration() {
        return minimumDuration;
    }

    public void setMinimumDuration(String minimumDuration) {
        this.minimumDuration = minimumDuration;
    }

    public String getIsMultipleBooking() {
        return isMultipleBooking;
    }

    public void setIsMultipleBooking(String isMultipleBooking) {
        this.isMultipleBooking = isMultipleBooking;
    }

}
