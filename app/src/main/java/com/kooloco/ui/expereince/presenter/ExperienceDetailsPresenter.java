package com.kooloco.ui.expereince.presenter;
/**
 * Created by hlink on 18/4/18.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.DisableDateListResposne;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.Response;
import com.kooloco.model.ReviewData;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.expereince.view.ExperienceDetailsView;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.visitor.dashboard.view.DashboardView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.util.SubscribeWithView;
import com.kooloco.util.TimeConvertUtils;

import java.net.ConnectException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperienceDetailsPresenter extends BasePresenter<ExperienceDetailsView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public ExperienceDetailsPresenter() {
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

    public void getData(String expId, boolean isLocalApp) {
        //{"experience_id":"1","month":"05","year":"2018"}
        //  view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        Calendar calendar = Calendar.getInstance();

        String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        if (!isLocalApp) {
            map.put("user_id", session.getUser().getId());
        }

        map.put("month", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "MM"));
        map.put("year", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "yyyy"));
        koolocoRepository.getExpDetails(map).subscribe(new SubscribeWithView<Response<ExpDetails>>(view) {
            @Override
            public void onSuccess(Response<ExpDetails> expDetailsResponse) {
                //       view.hideLoader();

                if (isLocalApp) {
                    //view.setData(expDetailsResponse.getData());
                    getDataYearFirst(expId, TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "yyyy"), expDetailsResponse.getData());
                } else {
                    view.setData(expDetailsResponse.getData());
                    // getDataYearFirst(expId, TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "yyyy"), expDetailsResponse.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //    view.hideLoader();
                if (e instanceof ConnectException) {
                    new android.os.Handler().postDelayed(() -> navigator.goBack(), 700);
                }
            }
        });

    }

    public void getDataCal(String expId, Calendar calendar) {
        //{"experience_id":"1","month":"05","year":"2018"}
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);

        String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        map.put("month", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "MM"));
        map.put("year", TimeConvertUtils.dateTimeConvertLocalToLocal(selectDate, "yyyy-MM-dd", "yyyy"));
        koolocoRepository.getExpDetails(map).subscribe(new SubscribeWithView<Response<ExpDetails>>(view) {
            @Override
            public void onSuccess(Response<ExpDetails> expDetailsResponse) {
                view.hideLoader();
                view.setDataCal(expDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void getRating(int pageRate, String expId) {
        Map<String, String> param = new HashMap<>();
        param.put("experience_id", expId);
        param.put("page", "" + pageRate);

        koolocoRepository.getRateExp(param).subscribe(new SubscribeWithView<Response<ReviewData>>(view) {
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

    public void openSelectExpDate(ExpDetails experienceDetails) {


        ExperienceBookingFlow experienceBookingFlow = new ExperienceBookingFlow();
        experienceBookingFlow.setLocalId(experienceDetails.getLocalId());
        experienceBookingFlow.setLocalName(experienceDetails.getLocalName());
        experienceBookingFlow.setLocalProfile(experienceDetails.getLocalImage());
        experienceBookingFlow.setLocalRating(experienceDetails.getLocalRate());

        experienceBookingFlow.setExpereinceNews(new ArrayList<>());


        ExpereinceNew expereinceNew = new ExpereinceNew();
        expereinceNew.setId(experienceDetails.getId());
        expereinceNew.setTitle(experienceDetails.getTitle());
        expereinceNew.setImage_url(experienceDetails.getImages().get(0).getLocalImageUrl());
        expereinceNew.setRate(experienceDetails.getRating());
        expereinceNew.setRateCount(experienceDetails.getRatingCount());
        expereinceNew.setExperience_url(experienceDetails.getActivities().getImageUrl());

        expereinceNew.setLocation(experienceDetails.getCity() + ", " + experienceDetails.getCountry());
        expereinceNew.setLocalId(experienceDetails.getLocalId());
        expereinceNew.setCity(experienceDetails.getCity());
        expereinceNew.setCountry(experienceDetails.getCountry());
        expereinceNew.setActivityAddress(experienceDetails.getMettingAddress());
        expereinceNew.setActivityLatitude(experienceDetails.getMeetingLet());
        expereinceNew.setActivityLongitude(experienceDetails.getMeetingLng());

        expereinceNew.setPrice(experienceDetails.getPrice());

        experienceBookingFlow.setExpereinceNew(expereinceNew);

        navigator.openExperienceSelectDateTimeView().hasData(experienceSelectDateTimeView -> experienceSelectDateTimeView.setExperienceBooking(experienceBookingFlow)).replace(true);
    }

    public void openSelectExpDateForDate(ExpDetails experienceDetails, java.util.Date date) {


        ExperienceBookingFlow experienceBookingFlow = new ExperienceBookingFlow();
        experienceBookingFlow.setLocalId(experienceDetails.getLocalId());
        experienceBookingFlow.setLocalName(experienceDetails.getLocalName());
        experienceBookingFlow.setLocalProfile(experienceDetails.getLocalImage());
        experienceBookingFlow.setLocalRating(experienceDetails.getLocalRate());

        experienceBookingFlow.setExpereinceNews(new ArrayList<>());
        experienceBookingFlow.setSelectDateExp(true);
        experienceBookingFlow.setDateExp(date);

        ExpereinceNew expereinceNew = new ExpereinceNew();
        expereinceNew.setId(experienceDetails.getId());
        expereinceNew.setTitle(experienceDetails.getTitle());
        expereinceNew.setImage_url(experienceDetails.getImages().get(0).getLocalImageUrl());
        expereinceNew.setRate(experienceDetails.getRating());
        expereinceNew.setRateCount(experienceDetails.getRatingCount());
        expereinceNew.setExperience_url(experienceDetails.getActivities().getImageUrl());

        expereinceNew.setLocation(experienceDetails.getCity() + ", " + experienceDetails.getCountry());
        expereinceNew.setLocalId(experienceDetails.getLocalId());
        expereinceNew.setCity(experienceDetails.getCity());
        expereinceNew.setCountry(experienceDetails.getCountry());
        expereinceNew.setActivityAddress(experienceDetails.getMettingAddress());
        expereinceNew.setActivityLatitude(experienceDetails.getMeetingLet());
        expereinceNew.setActivityLongitude(experienceDetails.getMeetingLng());

        expereinceNew.setPrice(experienceDetails.getPrice());

        experienceBookingFlow.setExpereinceNew(expereinceNew);

        navigator.openExperienceSelectDateTimeView().hasData(experienceSelectDateTimeView -> experienceSelectDateTimeView.setExperienceBooking(experienceBookingFlow)).replace(true);
    }

    public void getData() {
        view.setData(Temp.getExpDetails());
    }

    public void openMapScreen(ExpDetails expereinceNew) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(expereinceNew.getLocalName());
        dashboardDetails.setLastname(" ");

        dashboardDetails.setImageUrl(expereinceNew.getLocalImage());

        dashboardDetails.setLocalLocation(expereinceNew.getMettingAddress());

        dashboardDetails.setLatitude(expereinceNew.getMeetingLet());
        dashboardDetails.setLongitude(expereinceNew.getMeetingLng());

        navigator.openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
            @Override
            public void passData(MettingLocationView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);

    }

    public void openLocalDetails(ExpDetails localNew, boolean isLocalApp) {

        boolean isPreview;
        if (isLocalApp) {
            isPreview = true;

        } else {
            isPreview = false;
        }


        Bundle bundle = new Bundle();
        DashboardDetails dashboardDetails = new DashboardDetails();
        dashboardDetails.setId(localNew.getLocalId());

        navigator.openDashboard().hasData(new Passable<DashboardView>() {
            @Override
            public void passData(DashboardView dashboardView) {
                dashboardView.setDataNew(dashboardDetails);
                dashboardView.setIsPreview(isPreview);
                dashboardView.setIsOrg(false);
            }
        }).replace(true);

    }


    public void getDataYear(String expId, String year) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("year", year);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getDisableDateList(map).subscribe(new SubscribeWithView<Response<DisableDateListResposne>>(view) {
            @Override
            public void onSuccess(Response<DisableDateListResposne> disableDateListResposneResponse) {
                view.setCalData(disableDateListResposneResponse.getData().getCalendarDays());
                view.hideLoader();
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
                view.hideLoader();
            }
        });

    }

    public void getDataYearFirst(String expId, String year, ExpDetails data) {
        //  view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expId);
        map.put("year", year);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getDisableDateList(map).subscribe(new SubscribeWithView<Response<DisableDateListResposne>>(view) {
            @Override
            public void onSuccess(Response<DisableDateListResposne> disableDateListResposneResponse) {
                view.setData(data);
                view.setCalData(disableDateListResposneResponse.getData().getCalendarDays());
                // view.hideLoader();
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
                }               // view.hideLoader();
            }
        });

    }
}