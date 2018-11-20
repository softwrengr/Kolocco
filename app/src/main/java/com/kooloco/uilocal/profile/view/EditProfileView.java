package com.kooloco.uilocal.profile.view;
/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.model.User;
import com.kooloco.ui.base.RootView;

public interface EditProfileView extends RootView {
    void setData(User user);

    void setIsLocal(boolean isLocal);
}
