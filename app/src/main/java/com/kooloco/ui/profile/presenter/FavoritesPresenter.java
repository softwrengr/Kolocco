package com.kooloco.ui.profile.presenter;/**
 * Created by hlink44 on 26/9/17.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Favorites;
import com.kooloco.model.Order;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.profile.view.FavoritesView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class FavoritesPresenter extends BasePresenter<FavoritesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public FavoritesPresenter() {

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

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }


    public void getData(int page) {
        if (page == 1) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();

        map.put("page", "" + page);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getFavExp(map).subscribe(new SubscribeWithView<Response<List<ExpereinceNew>>>(view) {
            @Override
            public void onSuccess(Response<List<ExpereinceNew>> orderResponse) {
                if (page == 1) {
                    view.hideLoader();
                }
                view.setData(orderResponse.getData(), page);
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
                view.setData(new ArrayList<>(), page);
                if (page == 1) {
                    view.hideLoader();
                }

            }
        });
    }

    public void getDataHide(int page) {

        Map<String, String> map = new HashMap<>();

        map.put("page", "" + page);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getFavExp(map).subscribe(new SubscribeWithView<Response<List<ExpereinceNew>>>(view) {
            @Override
            public void onSuccess(Response<List<ExpereinceNew>> orderResponse) {
                view.setData(orderResponse.getData(), page);
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
                view.setData(new ArrayList<>(), page);

            }
        });
    }


    public void openDashboard() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Dashboard).start();
    }
}
