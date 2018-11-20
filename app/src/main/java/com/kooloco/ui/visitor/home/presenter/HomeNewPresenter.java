package com.kooloco.ui.visitor.home.presenter;
/**
 * Created by hlink on 19/4/18.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.HomeNewResponse;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.home.view.HomeNewView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class HomeNewPresenter extends BasePresenter<HomeNewView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Inject
    public HomeNewPresenter() {
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


    public void getData() {

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);

        koolocoRepository.getVisitorHomeNew(map).subscribe(new SubscribeWithView<Response<HomeNewResponse>>(view) {
            @Override
            public void onSuccess(Response<HomeNewResponse> homeNewResponseResponse) {

                view.setData(homeNewResponseResponse.getData());
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

                view.setData(new HomeNewResponse());
            }
        });
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }

    public void openLocalDetails(LocalNew localNew) {
        Bundle bundle = new Bundle();
        DashboardDetails dashboardDetails = new DashboardDetails();
        dashboardDetails.setId(localNew.getId());
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();
    }

    public void getDataLocal() {
        view.setData(databaseCacheDataSource.getVisitorHomeNew().getData());
    }
}