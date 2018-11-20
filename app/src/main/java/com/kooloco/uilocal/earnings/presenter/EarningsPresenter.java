package com.kooloco.uilocal.earnings.presenter;/**
 * Created by hlink44 on 7/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.AllMonth;
import com.kooloco.model.EarningData;
import com.kooloco.model.EarningRate;
import com.kooloco.model.Month;
import com.kooloco.model.MonthEarning;
import com.kooloco.model.Response;
import com.kooloco.model.ReviewData;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.earnings.view.EarningsView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class EarningsPresenter extends BasePresenter<EarningsView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    Gson gson;

    @Inject
    public EarningsPresenter() {

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

    public void openMonthEarningView(List<MonthEarning> monthEarnings, String tootalAmount) {
        Bundle bundle = new Bundle();
        bundle.putString("monthEarning", gson.toJson(monthEarnings));
        bundle.putString("monthEarningTotal", tootalAmount);

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.AllMonthAppEarning).addBundle(bundle).start();
    }

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.NotificationLocal).start();
    }

    public void openMonthData(Month month, int monthSelectPos, MonthEarning monthEarning) {

        AllMonth allMonth = new AllMonth();

        allMonth.setPos(monthSelectPos);
        allMonth.setMonthName(month.getFullName());
        allMonth.setShortName(month.getName());
        allMonth.setMonthNum(month.getMonthNum());
        allMonth.setYear(month.getYear());

        allMonth.setPrice(monthEarning.getTotalAmount());

        Bundle bundle = new Bundle();
        bundle.putString("monthAll", gson.toJson(allMonth));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MONTHEARNING).addBundle(bundle).start();
    }

    public void getRating(int pageRate) {

        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        param.put("page", "" + pageRate);

        koolocoRepository.getRateLocal(param).subscribe(new SubscribeWithView<Response<ReviewData>>(view) {
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

    public void getData() {
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        koolocoRepository.getEarningLocal(param).subscribe(new SubscribeWithView<Response<EarningData>>(view) {
            @Override
            public void onSuccess(Response<EarningData> earningDataResponse) {
                view.setData(earningDataResponse.getData());
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

                view.setData(new EarningData());

            }
        });
    }
}
