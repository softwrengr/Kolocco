package com.kooloco.uilocal.Notification.view;
/**
 * Created by hlink44 on 9/10/17.
 */

import com.kooloco.model.Notification;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface NotificationLocalView extends RootView {
    void setData(List<Notification> notificationsLocal, int page);
}
