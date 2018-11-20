package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 10/10/17.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.uilocal.organaization.view.CreateYourOrganizationView;
import com.kooloco.uilocal.organaization.view.PreviewDetailsView;
import com.kooloco.util.SubscribeWithView;

@PerActivity
public class PreviewDetailsPresenter extends BasePresenter<PreviewDetailsView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public PreviewDetailsPresenter() {

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
        kooocoRepository.getOrganizationDetails().subscribe(new SubscribeWithView<Response<OrganizationDetails>>(view) {
            @Override
            public void onSuccess(Response<OrganizationDetails> organizationDetailsResponse) {
                view.setData(organizationDetailsResponse.getData());
            }
        });
    }

    public void openCreateOrganizationChange() {
        navigator.openCreateOrganization().hasData(new Passable<CreateYourOrganizationView>() {
            @Override
            public void passData(CreateYourOrganizationView createYourOrganizationView) {
             //   createYourOrganizationView.setStep(1);
            }
        }).replace(true);
    }

    public void openOrganaizationDetails() {
        navigator.openOrgniztionDashboardView().replace(true);
    }

    public void openCreateOrganization() {
        navigator.openCreateOrganization().hasData(new Passable<CreateYourOrganizationView>() {
            @Override
            public void passData(CreateYourOrganizationView createYourOrganizationView) {
             //   createYourOrganizationView.setStep(2);
            }
        }).replace(true);
    }
}
