package com.kooloco.uilocal.organaization.presenter;
/**
 * Created by hlink on 26/3/18.
 */


import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationStaus;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.uilocal.organaization.view.OrganizationStatusView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@PerActivity
public class OrganizationStatusPresenter extends BasePresenter<OrganizationStatusView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public OrganizationStatusPresenter() {
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


    public void openCreateOrganization() {
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.CREATORG).start();
    }

    public void openEditOrganization() {
        navigator.openEditOrganizationView().replace(true);
    }

    public void openAddLocal(OrganizationDetails organizationDetails) {
        if (organizationDetails != null) {
            navigator.openAddLocal().hasData(assignLocalView -> {
                assignLocalView.setOrderDetails(organizationDetails);
            }).replace(true);
        }
    }

    public void openSetPriceRules(OrganizationDetails organizationDetails) {
        if (organizationDetails != null) {
            navigator.openSetPaymentRules().hasData(setPaymentRulesView -> setPaymentRulesView.setOrderDetails(organizationDetails)).replace(true);
        }
    }

    public void openPreviewOrganization() {
        OrganizationVisitor organizationVisitor = new OrganizationVisitor();
        organizationVisitor.setOwnerId(session.getUser().getId());
        navigator.openOrganisationDetails().hasData(organizationDetailsView -> {
            organizationDetailsView.setDataOrgVisitor(organizationVisitor);
            organizationDetailsView.setIsPreview(true);
            organizationDetailsView.setIsDeleteOrg(true);

        }).replace(true);
    }

    public void openDashbaordOrganization(OrganizationDetails organizationDetails) {
        navigator.openOrgniztionDashboardView().hasData(organizationDashboardView -> organizationDashboardView.setOrgData(organizationDetails)).replace(true);
    }

    public void getData(boolean isShowLoder) {
        if (isShowLoder) {
            view.showLoader();
        }
        Map<String, String> param = new HashMap<>();
        param.put("user_id", session.getUser().getId());
        koolocoRepository.getOrgStatus(param).subscribe(new SubscribeWithView<Response<OrganizationStaus>>(view) {
            @Override
            public void onSuccess(Response<OrganizationStaus> organizationStausResponse) {
                if (isShowLoder) {
                    view.hideLoader();
                }
                view.setData(organizationStausResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isShowLoder) {

                    view.hideLoader();
                }
            }
        });
    }

    public void openOrgInComplete() {
        navigator.openOrgInComplete().replace(true);
    }

    public void openOrgAddBank(String orgId) {
        navigator.openOrgAddBankView().hasData(orgAddBankView -> {
            orgAddBankView.setOrgId(orgId);
        }).replace(true, "OrgAddBank");
    }
}