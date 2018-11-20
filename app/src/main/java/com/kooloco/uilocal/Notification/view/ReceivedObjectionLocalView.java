package com.kooloco.uilocal.Notification.view;
/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.model.ObjectionDetails;
import com.kooloco.ui.base.RootView;

public interface ReceivedObjectionLocalView extends RootView {
    void setNotificationId(String id);

    void setData(ObjectionDetails data);
}
