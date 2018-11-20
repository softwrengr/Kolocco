package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 16/10/17.
 */

import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.organaization.view.PreviewDetailsSingalLocalView;
import com.kooloco.util.SubscribeWithView;

@PerActivity
public class PreviewDetailsSingalLocalPresenter extends BasePresenter<PreviewDetailsSingalLocalView> {

    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    public PreviewDetailsSingalLocalPresenter() {

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

}
