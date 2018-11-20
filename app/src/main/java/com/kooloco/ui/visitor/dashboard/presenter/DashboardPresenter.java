package com.kooloco.ui.visitor.dashboard.presenter;/**
 * Created by hlink44 on 16/9/17.
 */

import android.os.Bundle;

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.CheckPaymentRules;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.HomeLocalAndExpResponse;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.Response;
import com.kooloco.model.ReviewData;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.dashboard.view.DashboardView;
import com.kooloco.ui.visitor.dashboard.view.SelectActivitiesView;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.util.SubscribeWithView;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@PerActivity
public class DashboardPresenter extends BasePresenter<DashboardView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    public DashboardPresenter() {

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

    public void openOrganizationDetails(OrganizationVisitor organizationVisitor) {
        navigator.openOrganisationDetails().hasData(organizationDetailsView -> {
            organizationDetailsView.setDataOrgVisitor(organizationVisitor);
        }).replace(true);
    }

    public void openMapScreen(final DashboardDetails dashboardDetails) {
        navigator.openLocalAndVistorMap().hasData(new Passable<LocalVisitorMapView>() {
            @Override
            public void passData(LocalVisitorMapView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }

    public void onBack() {
        navigator.goBack();
    }

    public void openSelectSportNew(final VisitorBooking visitorBooking, DashboardDetails dashboardDetails) {

        navigator.openSelectServiceNew().hasData(selectServiceNewView -> {
            selectServiceNewView.setVisitorBooking(visitorBooking);
            selectServiceNewView.setDashBoardDetails(dashboardDetails);
        }).replace(true);

    }


    public void openSelectActivites(final VisitorBooking visitorBooking) {
        navigator.openSelectActivitesView().hasData(new Passable<SelectActivitiesView>() {
            @Override
            public void passData(SelectActivitiesView selectActivitiesView) {
                selectActivitiesView.setVisitorBooking(visitorBooking);
            }
        }).replace(true);
/*  navigator.openSelectDateView().hasData(new Passable<SelectDateView>() {
      @Override
      public void passData(SelectDateView selectDateView) {
          selectDateView.setVisitorBooking(visitorBooking);
      }
  }).replace(true);
 */
    }

    public void getData(String id) {

     //   view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", id);
        map.put("latitude", "" + BaseFragment.latitude);
        map.put("longitude", "" + BaseFragment.longitude);

        kooocoRepository.getVisitorHomeLocalDetails(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> homeNewResponseResponse) {
                view.setData(homeNewResponseResponse.getData());
             //   view.hideLoader();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
              //  view.hideLoader();
                if (e instanceof ConnectException) {
                    new android.os.Handler().postDelayed(() -> navigator.goBack(), 700);
                }
            }
        });
    }

    public void getRating(int pageRate, String localId) {
        //    view.setDataRating(new ArrayList<>(), pageRate, 0);
        Map<String, String> param = new HashMap<>();
        param.put("user_id", localId);
        param.put("page", "" + pageRate);

        kooocoRepository.getRateLocal(param).subscribe(new SubscribeWithView<Response<ReviewData>>(view) {
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


    public void callCheckLocalOrg(String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", userId);

        kooocoRepository.getIsCheckLocalAffilted(map).subscribe(new SubscribeWithView<Response<CheckPaymentRules>>(view) {
            @Override
            public void onSuccess(Response<CheckPaymentRules> checkPaymentRulesResponse) {
                view.setOrgData(checkPaymentRulesResponse);
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
            }
        });
    }

    public void openExpDetails(ExpereinceNew expereinceNew, boolean isPreview) {

        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        if (isPreview) {

            navigator.openExperienceDetailsView().hasData(experienceDetailsView -> {
                experienceDetailsView.setIsLocalApp(true);
                experienceDetailsView.setExpId(expereinceNew.getId());
            }).replace(true);


        } else {
            navigator.openExperienceDetailsView().hasData(experienceDetailsView -> {
                experienceDetailsView.setIsLocalApp(false);
                experienceDetailsView.setExpId(expereinceNew.getId());
            }).replace(true);
        }
    }

    public void openExpSelectScreen(DashboardDetails data) {
        ExperienceBookingFlow experienceBookingFlow = new ExperienceBookingFlow();
        experienceBookingFlow.setLocalId(data.getId());
        experienceBookingFlow.setLocalName(data.getLocalName());
        experienceBookingFlow.setLocalProfile(data.getImageUrl());
        experienceBookingFlow.setLocalRating(data.getLocalRating());

        experienceBookingFlow.setExpereinceNews(data.getExpereinceNews());

        if (data.getExpereinceNews().size() == 1) {
            experienceBookingFlow.setExpereinceNew(data.getExpereinceNews().get(0));
            navigator.openExperienceSelectDateTimeView().hasData(experienceSelectDateTimeView -> experienceSelectDateTimeView.setExperienceBooking(experienceBookingFlow)).replace(true);
        } else {
            navigator.openExperienceSelectVisitorView().hasData(experienceSelectVisitorView -> {
                experienceSelectVisitorView.setExpBookingData(experienceBookingFlow);
            }).replace(true);
        }


    }

}
