package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink on 26/3/18.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationStaus;
import com.kooloco.model.OrganizationStep;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.presenter.OrganizationStatusPresenter;
import com.kooloco.uilocal.organaization.view.OrganizationStatusView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class OrganizationStatusFragment extends BaseFragment<OrganizationStatusPresenter, OrganizationStatusView> implements OrganizationStatusView {

    @BindView(R.id.imageViewOrgLogo)
    ImageView imageViewOrgLogo;
    @BindView(R.id.textViewOrgName)
    AppCompatTextView textViewOrgName;
    @BindView(R.id.linearLayoutOrgDetails)
    LinearLayout linearLayoutOrgDetails;
    @BindView(R.id.textViewStepCount)
    AppCompatTextView textViewStepCount;
    @BindView(R.id.segmented_progressbar)
    SegmentedProgressBar segmentedProgressbar;
    @BindView(R.id.textViewDetails)
    AppCompatTextView textViewDetails;
    @BindView(R.id.linearLayoutOpenEditProfile)
    LinearLayout linearLayoutOpenEditProfile;
    @BindView(R.id.textViewCreateOrg)
    AppCompatTextView textViewCreateOrg;
    @BindView(R.id.textViewAddLocao)
    AppCompatTextView textViewAddLocao;
    @BindView(R.id.textViewSetPaymentRules)
    AppCompatTextView textViewSetPaymentRules;
    @BindView(R.id.linearLayoutCreOrg)
    LinearLayout linearLayoutCreOrg;
    @BindView(R.id.textViewOrgDashboard)
    AppCompatTextView textViewOrgDashboard;
    @BindView(R.id.textViewPreview)
    AppCompatTextView textViewPreview;

    OrganizationStaus organizationStaus;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.textViewAdminApprove)
    AppCompatTextView textViewAdminApprove;
    @BindView(R.id.textViewBankDetails)
    AppCompatTextView textViewBankDetails;

    @Override
    protected int createLayout() {
        return R.layout.fragment_org_status;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrganizationStatusView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData(true);
    }

    @OnClick(R.id.imageViewBack)
    public void onClick() {
        goBack();
    }

    @OnClick({R.id.textViewCreateOrg, R.id.textViewAddLocao, R.id.textViewSetPaymentRules, R.id.textViewOrgDashboard, R.id.textViewPreview, R.id.textViewBankDetails})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewCreateOrg:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                        presenter.openEditOrganization();
                    } else {
                        presenter.openCreateOrganization();
                    }
                }
                break;
            case R.id.textViewAddLocao:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                        presenter.openAddLocal(organizationStaus.getOrganizationDetails());
                    }
                }
                break;
            case R.id.textViewSetPaymentRules:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                        presenter.openSetPriceRules(organizationStaus.getOrganizationDetails());
                    }
                }
                break;
            case R.id.textViewOrgDashboard:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                        presenter.openDashbaordOrganization(organizationStaus.getOrganizationDetails());
                    }
                }
                break;
            case R.id.textViewPreview:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                        presenter.openPreviewOrganization();
                    }
                }
                break;

            case R.id.textViewBankDetails:
                if (organizationStaus != null) {
                    if (organizationStaus.getOrganizationDetails() != null) {
                         presenter.openOrgAddBank(organizationStaus.getOrganizationDetails().getId());
                    }
                }
                break;
        }
    }

    @Override
    public void setData(OrganizationStaus data) {
        organizationStaus = data;

        if (data.getOrganizationStauses() != null) {
            if (data.getOrganizationDetails() != null) {
                setTextViewVisibility(true);
            } else {
                setTextViewVisibility(false);
            }
        } else {
            setTextViewVisibility(false);
        }

        setData();
        if (data.getOrganizationStauses() != null) {
            getStepData(data.getOrganizationStauses());

            if (textViewAdminApprove != null) {
                textViewAdminApprove.setVisibility((data.getOrganizationStauses().get(0).getIsComplete().equalsIgnoreCase("1") && data.getOrganizationDetails().getIsApprove().equalsIgnoreCase("0")) ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void setTextViewVisibility(boolean status) {
        if (textViewAddLocao != null) {
            textViewAddLocao.setEnabled(status);
            textViewAddLocao.setAlpha(status ? 1.0f : 0.60f);
        }
        if (textViewSetPaymentRules != null) {
            textViewSetPaymentRules.setEnabled(status);
            textViewSetPaymentRules.setAlpha(status ? 1.0f : 0.60f);
        }
        if (textViewOrgDashboard != null) {
            textViewOrgDashboard.setEnabled(status);
            textViewOrgDashboard.setAlpha(status ? 1.0f : 0.60f);
        }
        if (textViewPreview != null) {
            textViewPreview.setEnabled(status);
            textViewPreview.setAlpha(status ? 1.0f : 0.60f);
        }
        if (textViewBankDetails != null) {
            textViewBankDetails.setEnabled(status);
            textViewBankDetails.setAlpha(status ? 1.0f : 0.60f);
        }

    }

    private void setData() {
        if (organizationStaus == null) {
            return;
        }

        if (organizationStaus.getOrganizationDetails() == null) {
            if (linearLayoutOrgDetails != null) {
                linearLayoutOrgDetails.setVisibility(View.GONE);
            }
            return;
        }

        OrganizationDetails organizationDetails = organizationStaus.getOrganizationDetails();

        if (linearLayoutOrgDetails != null) {
            linearLayoutOrgDetails.setVisibility(View.VISIBLE);
        }

        if (imageViewOrgLogo != null) {
            Picasso.with(getContext()).load(organizationDetails.getImageUrl().isEmpty() ? "android.resource://com.kooloco/drawable/user_round" : organizationDetails.getImageUrl()).placeholder(R.drawable.user_round).error(R.drawable.user_round).transform(new CircleTransform()).into(imageViewOrgLogo);
        }

        if (textViewOrgName != null) {
            textViewOrgName.setText(organizationDetails.getOrgName());
        }

        if (textViewCreateOrg != null) {
            textViewCreateOrg.setText(organizationStaus.getOrganizationDetails() != null ? getString(R.string.profile_edit_your_own_organization) : getString(R.string.create_organization));
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventBusAction action) {
        if (action == EventBusAction.UPDATEORG) {
            presenter.getData(false);
        }
    }

    private void getStepData(List<OrganizationStep> organizationSteps) {
        int stepPendingCount = 0;
        for (OrganizationStep profileStatus : organizationSteps) {
            if (profileStatus.getIsComplete().equalsIgnoreCase("0")) {
                stepPendingCount = stepPendingCount + 1;
            }
        }

        setStepCountData(stepPendingCount, organizationSteps.size());

    }

    private void setStepCountData(int pendingCount, int stepTootalCount) {

        if (segmentedProgressbar != null) {
            if (stepTootalCount != 0) {
                segmentedProgressbar.setSegmentCount(stepTootalCount);
            }
            segmentedProgressbar.setCompletedSegments(stepTootalCount - pendingCount);
        }
        if (textViewStepCount != null) {
            textViewStepCount.setText((pendingCount) + " " + getResources().getString(R.string.steps_left));
        }
        if (linearLayoutOpenEditProfile != null) {
            linearLayoutOpenEditProfile.setVisibility((pendingCount == 0) ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick(R.id.linearLayoutOpenEditProfile)
    public void onClickOpenInCompleteOrg() {
        presenter.openOrgInComplete();
    }

}
