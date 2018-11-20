package com.kooloco.ui.notification.view;
/**
 * Created by hlink44 on 26/9/17.
 */

import com.kooloco.model.ObjectionDetails;
import com.kooloco.ui.base.RootView;

public interface ReciveObjectionDetailsView extends RootView {
    void setData(ObjectionDetails data);

    void setId(String notificationId);
}
