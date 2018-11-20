package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hlink44 on 22/11/17.
 */

public class LanguageResponse {
    @SerializedName("app_language")
    @Expose
    private List<Language> appLanguage = null;
    @SerializedName("speaking_languages")
    @Expose
    private List<Language> speakingLanguages = null;

    public List<Language> getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(List<Language> appLanguage) {
        this.appLanguage = appLanguage;
    }

    public List<Language> getSpeakingLanguages() {
        return speakingLanguages;
    }

    public void setSpeakingLanguages(List<Language> speakingLanguages) {
        this.speakingLanguages = speakingLanguages;
    }
}
