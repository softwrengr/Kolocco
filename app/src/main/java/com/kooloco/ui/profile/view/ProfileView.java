package com.kooloco.ui.profile.view;
/**
 * Created by hlink44 on 22/9/17.
 */

import com.kooloco.ui.base.RootView;

public interface ProfileView extends RootView {
    void showCustomMessage(String message);

    void updateData();

}
