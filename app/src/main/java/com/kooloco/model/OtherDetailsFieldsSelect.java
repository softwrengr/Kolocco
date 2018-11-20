package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kooloco.gsonadapter.StringToBoolean;

/**
 * Created by hlink on 30/4/18.
 */

public class OtherDetailsFieldsSelect {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String name;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("insertdate")
    @Expose
    private String insertdate;


    @SerializedName("is_selected")
    @Expose
    @JsonAdapter(StringToBoolean.class)
    private boolean isSelect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
