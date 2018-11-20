package com.kooloco.ui.myexperience.presenter;/**
 * Created by hlink44 on 28/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.myexperience.view.MyExperienceView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class MyExperiencePresenter extends BasePresenter<MyExperienceView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Inject
    public MyExperiencePresenter() {

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

    public void openMyExperinceDetails(ExperienceDetails experienceDetails) {
        Bundle bundle = new Bundle();
        bundle.putString("isExpStatus", new Gson().toJson(experienceDetails));
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.MyExpeDet).addBundle(bundle).start();
    }


    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Notification).start();
    }

    public void getData(int page, boolean isShowLoader) {
       // view.setData(Temp.getListMyExp(), page);

        if (isShowLoader) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);

        kooocoRepository.getExperience(map, page == 1).subscribe(new SubscribeWithView<Response<List<ExperienceDetails>>>(view) {
            @Override
            public void onSuccess(Response<List<ExperienceDetails>> listResponse) {
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page);
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
                view.setData(new ArrayList<ExperienceDetails>(), page);
            }
        });
    }

    public void getDataLocal() {
        view.setData(databaseCacheDataSource.getExperienceList().getData(), 1);
    }

    public void openBlogList() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BlogList).start();
    }
}
