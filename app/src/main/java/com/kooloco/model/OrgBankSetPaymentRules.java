package com.kooloco.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlink on 20/6/18.
 */

public class OrgBankSetPaymentRules {
    private boolean isEdit = false;

    private Map<String, String> map = new HashMap<>();


    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
