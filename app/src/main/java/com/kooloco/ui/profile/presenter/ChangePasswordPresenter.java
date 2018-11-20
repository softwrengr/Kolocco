package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 27/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.profile.view.ChangePasswordView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ChangePasswordPresenter() {

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

    public void changePassword(String oldPassword, String newPassword) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("old_password", oldPassword);
        map.put("new_password", newPassword);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.changePassword(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                navigator.goBack();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

}
