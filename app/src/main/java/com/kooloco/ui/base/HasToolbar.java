package com.kooloco.ui.base;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by hlink21 on 20/12/16.
 */

public interface HasToolbar {
    void showToolbar(boolean b);

    void setToolbarTitle(@NonNull String title);

    void setToolbarTitle(@StringRes int title);

    void showBackButton(boolean b);

    void setToolbarColor(@ColorRes int color);

    void setDrawerToolbarColor(@ColorRes int color);

    void setToolbarElevation(boolean isVisible);
}
