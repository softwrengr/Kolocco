package com.kooloco.ui.visitor.home.presenter;/**
 * Created by hlink44 on 14/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.FilterRequest;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.home.view.HomeView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;


    @Inject
    public HomePresenter() {

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

    public void openDashBoard(DashboardDetails dashboardDetails) {
        // navigator.openDashboard().replace(true);

        Bundle bundle = new Bundle();
        bundle.putString("localDetails", new Gson().toJson(dashboardDetails));

        navigator.openIsloatedFullActivity().addBundle(bundle).setPage(AppNavigator.Pages.Dashboard).start();
    }

    public void getData(int page, boolean isShowLoader) {
        if (isShowLoader) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("page", "" + page);

        kooocoRepository.getVisitorHome(map, page == 1).subscribe(new SubscribeWithView<Response<List<DashboardDetails>>>(view) {
            @Override
            public void onSuccess(Response<List<DashboardDetails>> listResponse) {
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page, false);
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
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<DashboardDetails>(), page, false);
            }
        });
    }

    public void getDataFilter(int page, boolean isShowLoader, FilterRequest filterRequest) {
        if (isShowLoader) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("sport_id", filterRequest.getSportId());
        map.put("date", filterRequest.getDate());
        map.put("start_time", filterRequest.getStartTime());
        map.put("experience_id", filterRequest.getExperienceId());
        map.put("language_id", filterRequest.getLanguageId());

        if (!(filterRequest.getPriceMin().equalsIgnoreCase("0") && filterRequest.getPriceMax().equalsIgnoreCase("500"))) {
            map.put("price_min", filterRequest.getPriceMin());
            map.put("price_max", filterRequest.getPriceMax());
        }

        if (!(filterRequest.getRateMin().equalsIgnoreCase("0") && filterRequest.getRateMax().equalsIgnoreCase("5"))) {
            map.put("rate_min", filterRequest.getRateMin());
            map.put("rate_max", filterRequest.getRateMax());
        }


        if (!filterRequest.getAddres().isEmpty()) {
            map.put("latitude", filterRequest.getLatitude());
            map.put("longitude", filterRequest.getLongitude());
        }

        map.put("page", "" + page);

        kooocoRepository.getVisitorHomeFilter(map).subscribe(new SubscribeWithView<Response<List<DashboardDetails>>>(view) {
            @Override
            public void onSuccess(Response<List<DashboardDetails>> listResponse) {
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page, true);
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
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<DashboardDetails>(), page, true);
            }
        });
    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Notification).start();
    }

    public void openBlogList() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BlogList).start();
    }

    public void getDataLocal() {
        view.setData(databaseCacheDataSource.getVsitorHome().getData(), 1, false);
    }
}
