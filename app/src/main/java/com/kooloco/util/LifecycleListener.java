package com.kooloco.util;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.gson.Gson;
import com.kooloco.constant.Common;
import com.kooloco.core.AppPreferences;
import com.kooloco.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlink on 4/6/18.
 */

public class LifecycleListener implements LifecycleObserver {
    FirebaseFirestore dbApplication;
    AppPreferences appPreferences;
    private Context context;
    Gson gson;

    public LifecycleListener(Context context) {
        this.context = context;
        appPreferences = new AppPreferences(context);
        gson = new Gson();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLifeResume() {
        Log.e("App", "OnResume");
        setIsOnLine(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onLifePause() {
        Log.e("App", "OnPause");
        setIsOnLine(false);
    }


    public FirebaseFirestore getDatabaseBaseActivity() {

        if (dbApplication == null) {
            dbApplication = FirebaseFirestore.getInstance();

            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            dbApplication.setFirestoreSettings(settings);
        }
        return dbApplication;

    }

    private void setIsOnLine(boolean isOnLine) {
        if (appPreferences.getBoolean("isLogin")) {

            Map<String, Object> chat = new HashMap<>();

            String userJSON = appPreferences.getString(com.kooloco.core.Common.USER_JSON);
            User user = gson.fromJson(userJSON, User.class);

            chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TYPE, user.getDeviceType());
            chat.put(Common.FireStore.FIELD_SENDER_DEVICE_TOKEN, user.getDeviceId());
            chat.put(Common.FireStore.FIELD_IS_ONLINE, isOnLine);
            chat.put(Common.FireStore.FIELD_SENDER_IMAGE_URL, user.getProfileImage());

            getDatabaseBaseActivity().collection(Common.FireStore.TAB_NAME_USER).document(user.getId()).set(chat).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Log.e(":::::: Base Activity", "Data updated successfully");
                }
            });

        }
    }
}
