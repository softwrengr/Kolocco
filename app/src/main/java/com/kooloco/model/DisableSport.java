package com.kooloco.model;

import java.util.Date;

/**
 * Created by hlink44 on 11/10/17.
 */

public class DisableSport {
    private String name;
    private String startDate = "";
    private String endDate = "";
    private boolean isEditable = true;

    private Date selectStartDate;
    private Date selectEndDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public Date getSelectStartDate() {
        return selectStartDate;
    }

    public void setSelectStartDate(Date selectStartDate) {
        this.selectStartDate = selectStartDate;
    }

    public Date getSelectEndDate() {
        return selectEndDate;
    }

    public void setSelectEndDate(Date selectEndDate) {
        this.selectEndDate = selectEndDate;
    }
}
