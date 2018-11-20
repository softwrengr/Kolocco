package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 25/9/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Response;
import com.kooloco.model.ReviewData;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.ViewProfileView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@PerActivity
public class ViewProfilePresenter extends BasePresenter<ViewProfileView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public ViewProfilePresenter() {

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

    public void onBack() {
        navigator.goBack();
    }

    public void getData() {
        view.showLoader();
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());

        kooocoRepository.getProfileVisitor(param).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {
                view.hideLoader();
                session.setUser(userResponse.getData());
                view.setData(userResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openOrderHistory() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrderHistory).start();
    }


    public void openEditProfile() {
        navigator.openEditProfileView().replace(true);
    }

    public void getRating(int pageRate) {

        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("page", "" + pageRate);

        kooocoRepository.getRateVisitor(param).subscribe(new SubscribeWithView<Response<ReviewData>>(view) {
            @Override
            public void onSuccess(Response<ReviewData> userResponse) {
                int count = 0;
                if (userResponse.getData().getReviewCount() != null) {
                    try {
                        count = Integer.parseInt(userResponse.getData().getReviewCount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        count = 0;
                    }
                }

                view.setDataRating(userResponse.getData().getList(), pageRate, count);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }

                view.setDataRating(new ArrayList<>(), pageRate, 0);

            }
        });
    }

    public void openSpeakLanguages() {
        navigator.openSpeekLanguageView().replace(true);
    }
}
