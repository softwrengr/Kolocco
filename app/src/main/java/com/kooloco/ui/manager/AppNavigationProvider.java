package com.kooloco.ui.manager;

import android.support.annotation.StyleRes;

/**
 * Created by hlink21 on 29/5/17.
 */

public interface AppNavigationProvider {


    interface HasPage {
    }

    boolean requestAllPermission(PermissionGrantedListener permissionGrantedListener);
    interface PermissionGrantedListener {
        void onGranted();
    }
    interface HasThemeColor {
        String THEME_COLOR = "THEME_COLOR";

        @StyleRes
        int getTheme();
    }
}
