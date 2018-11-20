package com.kooloco.ui.visitor.home.presenter;
/**
 * Created by hlink on 21/4/18.
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
import com.kooloco.model.FilterRequest;
import com.kooloco.model.HomeLocalAndExpResponse;
import com.kooloco.model.HomeNewResponse;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.home.view.HomeLocalAndExpView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class HomeLocalAndExpPresenter extends BasePresenter<HomeLocalAndExpView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Inject
    public HomeLocalAndExpPresenter() {
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

/*    public void getlocalData() {
        view.setLocalData(Temp.getListLocalNew());

    }

    public void getExpData() {
        view.setExpData(Temp.getListExpNew());
    }*/

    public void openNotification() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Notification).start();
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

    public void getData(boolean isShow) {

        if (isShow) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("page", "1");

        koolocoRepository.getVisitorOnePage(map).subscribe(new SubscribeWithView<Response<HomeLocalAndExpResponse>>(view) {
            @Override
            public void onSuccess(Response<HomeLocalAndExpResponse> homeNewResponseResponse) {

                if (homeNewResponseResponse.getData().getLocalNews().isEmpty() || homeNewResponseResponse.getData().getExpereinceNews().isEmpty()) {
                    view.setLocalData(new ArrayList<>(), 1);
                    view.setExpData(new ArrayList<>(), 1);

                } else {
                    view.setLocalData(homeNewResponseResponse.getData().getLocalNews(), 1);
                    view.setExpData(homeNewResponseResponse.getData().getExpereinceNews(), 1);
                }

                if (isShow) {
                    view.hideLoader();
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setLocalData(new ArrayList<>(), 1);
                        view.setExpData(new ArrayList<>(), 1);
                    }
                } else {
                    super.onError(e);
                }

                if (isShow) {
                    view.hideLoader();
                }

            }
        });
    }

    public void getDataLocal() {
        view.setLocalData(databaseCacheDataSource.getVisitorHomeOnePage().getData().getLocalNews(), 1);
        view.setExpData(databaseCacheDataSource.getVisitorHomeOnePage().getData().getExpereinceNews(), 1);
    }

    public void getlocalData(int pageLocal) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("page", "" + pageLocal);

        koolocoRepository.getVisitorHomeLocal(map).subscribe(new SubscribeWithView<Response<List<LocalNew>>>(view) {
            @Override
            public void onSuccess(Response<List<LocalNew>> homeNewResponseResponse) {

                view.setLocalData(homeNewResponseResponse.getData(), pageLocal);


            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setLocalData(new ArrayList<>(), pageLocal);
                    }
                } else {
                    super.onError(e);
                }
            }
        });
    }

    public void getExpData(int pageExp) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);
        map.put("page", "" + pageExp);

        koolocoRepository.getVisitorHomeExp(map).subscribe(new SubscribeWithView<Response<List<ExpereinceNew>>>(view) {
            @Override
            public void onSuccess(Response<List<ExpereinceNew>> homeNewResponseResponse) {

                view.setExpData(homeNewResponseResponse.getData(), pageExp);

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setExpData(new ArrayList<>(), pageExp);
                    }
                } else {
                    super.onError(e);
                }
            }
        });
    }


    public void getDataFilter(int page, boolean isShowLoader, FilterRequest filterRequest, String isStatus) {
        if (isShowLoader) {
            view.showLoader();
        }


//                "recommended_level":"1", "perfect_for":"1" }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("sport_id", filterRequest.getSportId());
        map.put("date", filterRequest.getDate());
        map.put("start_time", filterRequest.getStartTime());

        map.put("activity_id", filterRequest.getExperienceId());
        map.put("language_id", filterRequest.getLanguageId());

        if (!(filterRequest.getPriceMin().equalsIgnoreCase("0") && filterRequest.getPriceMax().equalsIgnoreCase("5000"))) {
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
            map.put("city", filterRequest.getCity());
            map.put("country", filterRequest.getCountry());

        }

        map.put("recommended_level", filterRequest.getRecomanId());
        map.put("perfect_for", filterRequest.getPerfectId());
        map.put("duration", filterRequest.getDurationId());

        map.put("page", "" + page);

        if (isStatus.equalsIgnoreCase("1")) {
            koolocoRepository.getFilterOnePage(map).subscribe(new SubscribeWithView<Response<HomeLocalAndExpResponse>>(view) {
                @Override
                public void onSuccess(Response<HomeLocalAndExpResponse> homeNewResponseResponse) {


                    if (homeNewResponseResponse.getData().getLocalNews().isEmpty() || homeNewResponseResponse.getData().getExpereinceNews().isEmpty()) {
                        view.setLocalData(new ArrayList<>(), 1);
                        view.setExpData(new ArrayList<>(), 1);

                    } else {
                        view.setLocalData(homeNewResponseResponse.getData().getLocalNews(), page);
                        view.setExpData(homeNewResponseResponse.getData().getExpereinceNews(), page);
                    }

                    if (isShowLoader) {
                        view.hideLoader();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof ServerException) {
                        ServerException exception = (ServerException) e;
                        if (exception.getServerCode() != 0) {
                            super.onError(e);
                        }else if (exception.getServerCode() == 0) {
                            view.setLocalData(new ArrayList<>(), page);
                            view.setExpData(new ArrayList<>(), page);
                        }
                    } else {
                        super.onError(e);
                    }
                    if (isShowLoader) {
                        view.hideLoader();
                    }

                }
            });
        } else if (isStatus.equalsIgnoreCase("2")) {
            koolocoRepository.getFilterLocal(map).subscribe(new SubscribeWithView<Response<List<LocalNew>>>(view) {
                @Override
                public void onSuccess(Response<List<LocalNew>> homeNewResponseResponse) {
                    if (isShowLoader) {
                        view.hideLoader();
                    }
                    view.setLocalData(homeNewResponseResponse.getData(), page);
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof ServerException) {
                        ServerException exception = (ServerException) e;
                        if (exception.getServerCode() != 0) {
                            super.onError(e);
                        }else if (exception.getServerCode() == 0) {
                            view.setLocalData(new ArrayList<>(), page);
                        }
                    } else {
                        super.onError(e);
                    }
                    if (isShowLoader) {
                        view.hideLoader();
                    }

                }
            });
        } else if (isStatus.equalsIgnoreCase("3")) {
            koolocoRepository.getFilterExp(map).subscribe(new SubscribeWithView<Response<List<ExpereinceNew>>>(view) {
                @Override
                public void onSuccess(Response<List<ExpereinceNew>> homeNewResponseResponse) {
                    if (isShowLoader) {
                        view.hideLoader();
                    }
                    view.setExpData(homeNewResponseResponse.getData(), page);

                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof ServerException) {
                        ServerException exception = (ServerException) e;
                        if (exception.getServerCode() != 0) {
                            super.onError(e);
                        }
                        else if (exception.getServerCode() == 0) {
                            view.setExpData(new ArrayList<>(), page);
                        }
                    } else {
                        super.onError(e);
                    }
                    if (isShowLoader) {
                        view.hideLoader();
                    }

                }
            });
        }

    }

}