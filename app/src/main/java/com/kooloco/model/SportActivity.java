package com.kooloco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 4/10/17.
 */

public class SportActivity {

    private String id;

    private String icon;

    private String parentId;

    private String name = "";

    private String price = "";

    private List<SportSubActivity> sportSubActivities = new ArrayList<>();

    private boolean isSelect = false;
    private boolean isExpand = false;

    private boolean isGroup = false;

    private boolean isOpen = false;

    private String pricePerPerPerson = "";
    private String addParticipants = "";
    private String minimumDuration = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<SportSubActivity> getSportSubActivities() {
        return sportSubActivities;
    }

    public void setSportSubActivities(List<SportSubActivity> sportSubActivities) {
        this.sportSubActivities = sportSubActivities;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
