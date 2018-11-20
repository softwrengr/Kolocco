package com.kooloco.ui.notification.view;
/**
 * Created by hlink44 on 26/9/17.
 */

import com.kooloco.model.Notification;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface NotificationView extends RootView {
    void setData(List<Notification> notifications, int page);
}
