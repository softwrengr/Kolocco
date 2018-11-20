package com.kooloco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 4/10/17.
 */

public class CheckSportEquipment {
    private String name = "";
    List<Sport> sports = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
