package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kooloco.ui.expereince.adapter.ExpAnotherFieldsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink on 31/1/18.
 */

public class FilterGetData {
    @SerializedName("experience")
    @Expose
    private List<Activities> experience=new ArrayList<>();

    @SerializedName("sports")
    @Expose
    private List<Service> sports=new ArrayList<>();

    @SerializedName("language")
    @Expose
    private List<Language> languages=new ArrayList<>();


    public List<Activities> getExperience() {
        return experience;
    }

    public void setExperience(List<Activities> experience) {
        this.experience = experience;
    }

    public List<Service> getSports() {
        return sports;
    }

    public void setSports(List<Service> sports) {
        this.sports = sports;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    @SerializedName("duration")
    @Expose
    private List<DurationFilter> durationFilters=new ArrayList<>();


    @SerializedName("recommended_level")
    @Expose
    private List<OtherDetailsFieldsSelect> recommendedLevel=new ArrayList<>();

    @SerializedName("perfect_for")
    @Expose
    private List<OtherDetailsFieldsSelect> perfectFor=new ArrayList<>();


    public List<OtherDetailsFieldsSelect> getRecommendedLevel() {
        return recommendedLevel;
    }

    public void setRecommendedLevel(List<OtherDetailsFieldsSelect> recommendedLevel) {
        this.recommendedLevel = recommendedLevel;
    }

    public List<OtherDetailsFieldsSelect> getPerfectFor() {
        return perfectFor;
    }

    public void setPerfectFor(List<OtherDetailsFieldsSelect> perfectFor) {
        this.perfectFor = perfectFor;
    }

    public List<DurationFilter> getDurationFilters() {
        return durationFilters;
    }

    public void setDurationFilters(List<DurationFilter> durationFilters) {
        this.durationFilters = durationFilters;
    }
}
