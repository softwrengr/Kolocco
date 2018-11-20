package com.kooloco.ui.chat.view;
/**
 * Created by hlink44 on 26/9/17.
 */

import com.kooloco.model.Chat;
import com.kooloco.model.RecentChat;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface RecentChatView extends RootView {
    void setData(List<Chat> data);
}
