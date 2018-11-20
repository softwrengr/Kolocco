package com.kooloco.model;

/**
 * Created by hlink44 on 26/9/17.
 */

public class Sport {
    private String name = "";
    private boolean isSelect = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
