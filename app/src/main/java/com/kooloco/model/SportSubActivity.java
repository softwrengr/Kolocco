package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 4/10/17.
 */

public class SportSubActivity {

    private String id = "";

    private String name = "";

    private String icon = "";

    private String parentId = "";

    private String price = "";

    private boolean isSelect = false;

    private boolean isGroup = false;

    private String pricePerPerPerson = "";
    private String addParticipants = "";
    private String minimumDuration = "";


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getPricePerPerPerson() {
        return pricePerPerPerson;
    }

    public void setPricePerPerPerson(String pricePerPerPerson) {
        this.pricePerPerPerson = pricePerPerPerson;
    }

    public String getAddParticipants() {
        return addParticipants;
    }

    public void setAddParticipants(String addParticipants) {
        this.addParticipants = addParticipants;
    }

    public String getMinimumDuration() {
        return minimumDuration;
    }

    public void setMinimumDuration(String minimumDuration) {
        this.minimumDuration = minimumDuration;
    }
}
