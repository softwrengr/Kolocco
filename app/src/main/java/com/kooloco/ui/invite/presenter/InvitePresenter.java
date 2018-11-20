package com.kooloco.ui.invite.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.data.URLFactory;
import com.kooloco.di.PerActivity;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.invite.view.InviteView;

@PerActivity
public class InvitePresenter extends BasePresenter<InviteView> {
    @Inject
    public InvitePresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openInviteFriendsList() {
        navigator.openInviteListView().replace(true);
    }

    public void openWebOpenView(String title) {
        navigator.openWebOpenView().hasData(webOpenView ->webOpenView.setData(title, URLFactory.TERMS_CONDITIONS) ).replace(true);
    }
}
