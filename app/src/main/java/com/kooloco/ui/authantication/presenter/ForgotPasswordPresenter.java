package com.kooloco.ui.authantication.presenter;/**
 * Created by hlink44 on 27/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Response;
import com.kooloco.ui.authantication.view.ForgotPasswordView;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.profile.view.ChangePasswordView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import javax.inject.Inject;

@PerActivity
public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {
    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ForgotPasswordPresenter() {

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

    public void forgotPassword(String email) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("email", email);

        koolocoRepository.forgotPassword(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response response) {
                view.hideLoader();
                view.showMessage(response.getMessage());
                new android.os.Handler().postDelayed(() -> navigator.goBack(), 500);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }
}
