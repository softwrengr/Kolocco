package com.kooloco.core;


import com.kooloco.model.User;

/**
 * Created by hlink21 on 11/7/16.
 */
public interface Session {

    String API_KEY = "API-KEY";
    String USER_SESSION = "token";
    String USER_ID = "USER_ID";
    String DEVICE_TYPE = "A";

    String getApiKey();

    String getUserSession();

    String getUserId();

    void setApiKey(String apiKey);

    void setUserSession(String userSession);

    void setUserId(String userId);

    String getDeviceId();

    void setUser(User user);

    User getUser();

    void clearSession();

    String getLanguage();

    String getCurrency();

    String getAppLanguage();

    void setCurrency(String currency, String lCurrency);

}
