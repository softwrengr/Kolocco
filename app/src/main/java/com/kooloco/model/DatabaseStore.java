package com.kooloco.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hlink on 16/1/18.
 */

public class DatabaseStore extends RealmObject {
    @PrimaryKey
    private String apiName = "";

    private String response = "";

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
