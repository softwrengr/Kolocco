package com.kooloco.uilocal.expereince.presenter;
/**
 * Created by hlink on 13/4/18.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.expereince.view.ExperienceView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class ExperiencePresenter extends BasePresenter<ExperienceView> {
    @Inject
    Session session;
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public ExperiencePresenter() {
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

    public void getData(boolean isShowLoder) {
        if (isShowLoder) {
            view.showLoader();
        }
        Map<String, String> map = new HashMap<>();
        map.put("local_id", session.getUser().getId());

        koolocoRepository.getExpListLocal(map).subscribe(new SubscribeWithView<Response<List<ExpereinceNew>>>(view) {
            @Override
            public void onSuccess(Response<List<ExpereinceNew>> listResponse) {
                if (isShowLoder) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData());
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
                if (isShowLoder) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<>());
            }
        });
    }

    public void openLocalApp() {

        navigator.startHomeLocalActivity().byFinishingAll().start();
    }

    public void openAddBank() {
        navigator.openAddBankView().replace(true);
    }


    public void openEditExperience(ExpereinceNew expereinceNew) {
        navigator.openExperienceEditView().hasData(editExperienceView -> {
            editExperienceView.setExpId(expereinceNew.getId());
            editExperienceView.setExpNew(expereinceNew);
        }).replace(true);
    }

    public void openExpAddDetails() {
        // navigator.openExperienceAddDetailsView().replace(true);
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPADDDETAILS).start();
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {
        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCALSIDE).addBundle(bundle).start();

        //       navigator.openExperienceDetailsView().hasData(experienceDetailsView -> experienceDetailsView.setIsLocalApp(true)).replace(true);
    }

    public void callWsDeleteExp(int pos, ExpereinceNew expereinceNew) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("experience_id", expereinceNew.getId());
        koolocoRepository.deleteExp(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.deleteExp(pos, expereinceNew);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();

            }
        });
    }

    public void getDataUser() {

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getLocalProfile(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
            @Override
            public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {

                User user = session.getUser();

                user.setRate(organizationDetailsResponse.getData().getLocalRating());
                user.setFirstname(organizationDetailsResponse.getData().getFirstname());
                user.setLastname(organizationDetailsResponse.getData().getLastname());
                user.setIsWantToCompelte(organizationDetailsResponse.getData().getIsWantToCompelte());
                user.setProfileStatuses(organizationDetailsResponse.getData().getProfileStatuses());
                user.setIsAdminApprove(organizationDetailsResponse.getData().getIsAdminApprove());
                user.setIsAffilated(organizationDetailsResponse.getData().getIsAffilated());

                session.setUser(user);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

}