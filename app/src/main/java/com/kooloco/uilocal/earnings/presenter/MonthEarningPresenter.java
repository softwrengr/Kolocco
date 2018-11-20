package com.kooloco.uilocal.earnings.presenter;/**
 * Created by hlink44 on 7/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.AllMonth;
import com.kooloco.model.Order;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.earnings.view.MonthEarningView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class MonthEarningPresenter extends BasePresenter<MonthEarningView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public MonthEarningPresenter() {

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

    public void openOrderDetails(Order order) {
        Bundle bundle = new Bundle();
        bundle.putString("orderStatus", new Gson().toJson(order));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.OrdeDetailsLocal).addBundle(bundle).start();
    }

    public void getData(AllMonth allMonth, int page) {

        if (page == 1) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();

        map.put("month", allMonth.getMonthNum());
        map.put("year", allMonth.getYear());
        map.put("page", "" + page);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getLocalMonthEarning(map).subscribe(new SubscribeWithView<Response<List<Order>>>(view) {
            @Override
            public void onSuccess(Response<List<Order>> orderResponse) {
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
}


