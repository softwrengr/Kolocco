package com.kooloco.core;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.kooloco.model.User;
import com.kooloco.ui.navigation.AppNavigator;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by hlink21 on 11/7/16.
 */
@Singleton
public class AppSession implements Session {

    private final String apiKey;
    private AppPreferences appPreferences;
    private Context context;
    private User user;
    private String language;

    private String currency;
    private String lCurrency;

    @Inject
    Gson gson;

    @Inject
    public AppSession(AppPreferences appPreferences, Context context, @Named("api-key") String apiKey) {
        this.appPreferences = appPreferences;
        this.context = context;
        this.apiKey = apiKey;
        language = "english";
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getUserSession() {
        return appPreferences.getString(USER_SESSION);
    }

    @Override
    public String getUserId() {
        return appPreferences.getString(USER_ID);
    }

    @Override
    public void setApiKey(String apiKey) {
    }

    @Override
    public void setUserSession(String userSession) {
        appPreferences.putString(USER_SESSION, userSession);
    }

    @Override
    public void setUserId(String userId) {
        appPreferences.putString(USER_ID, userId);
    }

    @Override
    public String getDeviceId() {
        String token = "000";

  /*      FirebaseApp.initializeApp(context);*/

        token = FirebaseInstanceId.getInstance().getToken();

        if (token == null) {
            token = "000";
        }

        if (token == null && token.isEmpty())
            token = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return token;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        String userJson = gson.toJson(user);
        if (userJson != null) {

            setCurrency(user.getvCurrency(), user.getlCurrency());
            appPreferences.putString(Common.USER_JSON, userJson);
        }
    }

    @Override
    public User getUser() {

        if (user == null) {
            String userJSON = appPreferences.getString(Common.USER_JSON);
            user = gson.fromJson(userJSON, User.class);
        }

        return user;
    }


    @Override
    public String getCurrency() {
        currency = "CHF";

        User user = getUser();
        if (user != null) {

            if (user.getRole().equalsIgnoreCase("L")) {
                currency = appPreferences.getString(Common.LOCAL_CURRENCY);
            } else {
                currency = appPreferences.getString(Common.USER_CURRENCY);
            }

            if (currency == null) {
                currency = "CHF";
            }
            if (currency.isEmpty()) {
                currency = "CHF";
            }

        }
        return currency;
    }

    @Override
    public String getAppLanguage() {

        language = appPreferences.getString(com.kooloco.constant.Common.APPLANG);

        if (language.isEmpty()) {
            Locale locale = Resources.getSystem().getConfiguration().locale;

            switch (locale.getLanguage()) {
                case "de":
                    language = "german";
                    break;
                case "es":
                    language = "spanish";
                    break;
                case "fr":
                    language = "french";
                    break;
                default:
                    language = "english"; 
                    break;
            }
        }

        return language;
    }

    @Override
    public void setCurrency(String currency, String lCurrency) {
        this.currency = currency;
        this.lCurrency = lCurrency;
        appPreferences.putString(Common.USER_CURRENCY, currency);
        appPreferences.putString(Common.LOCAL_CURRENCY, lCurrency);

    }

    @Override
    public void clearSession() {
        appPreferences.clearAll();
    }

    @Override
    public String getLanguage() {
        return language;
    }
}
