package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 3/5/18.
 */

public class ScheduleAndPriceResponse {
    @SerializedName("maximum_participant")
    @Expose
    private String maximumParticipant;
    @SerializedName("special_slot")
    @Expose
    private List<SchedulePrice> slot = new ArrayList<>();

    @SerializedName("is_available")
    @Expose
    private String is_avability = "0";

    public String getMaximumParticipant() {
        return maximumParticipant;
    }

    public void setMaximumParticipant(String maximumParticipant) {
        this.maximumParticipant = maximumParticipant;
    }

    public List<SchedulePrice> getSlot() {
        return slot;
    }

    public void setSlot(List<SchedulePrice> slot) {
        this.slot = slot;
    }

    public String getIs_avability() {
        return is_avability;
    }

    public void setIs_avability(String is_avability) {
        this.is_avability = is_avability;
    }

    @SerializedName("slot")
    @Expose
    private SchedulePriceData schedulePriceData = new SchedulePriceData();

    public SchedulePriceData getSchedulePriceData() {
        return schedulePriceData;
    }

    public void setSchedulePriceData(SchedulePriceData schedulePriceData) {
        this.schedulePriceData = schedulePriceData;
    }
}
