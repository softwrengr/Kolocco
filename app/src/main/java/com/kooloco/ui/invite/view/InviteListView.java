package com.kooloco.ui.invite.view;
/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.model.Invite;
import com.kooloco.ui.base.RootView;

import java.util.List;

public interface InviteListView extends RootView {
    void setData(List<Invite> data);
}
