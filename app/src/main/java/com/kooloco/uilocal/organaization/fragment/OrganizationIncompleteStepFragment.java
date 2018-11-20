package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink on 20/3/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationStep;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.organaization.adapter.OrganizationInCompleteStepAdapter;
import com.kooloco.uilocal.organaization.presenter.OrganizationlIncompleteStepPresenter;
import com.kooloco.uilocal.organaization.view.OrganizationIncompleteStepView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;

/**
 * Created by hlink on 8/1/18.
 */

public class OrganizationIncompleteStepFragment extends BaseFragment<OrganizationlIncompleteStepPresenter, OrganizationIncompleteStepView> implements OrganizationIncompleteStepView {

    @BindView(R.id.recyclerStepInComplete)
    RecyclerView recyclerStepInComplete;
    @BindView(R.id.recyclerStepComplete)
    RecyclerView recyclerStepComplete;

    @Inject
    Session session;
    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.appCompatTextView2)
    AppCompatTextView appCompatTextView2;

    OrganizationDetails organizationDetails;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_incomplete_step;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected OrganizationIncompleteStepView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData(true);
    }


    @Override
    public void setData(List<OrganizationStep> data) {

        Observable.fromIterable(data).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("0")).toList().subscribe(profileStatuses -> {

            if (profileStatuses.size() == 0) {
                goBack();
                if (appCompatTextView != null) {
                    appCompatTextView.setVisibility(View.GONE);
                }
            } else {
                if (appCompatTextView != null) {
                    appCompatTextView.setVisibility(View.VISIBLE);
                }
            }

            if (recyclerStepInComplete != null) {
                recyclerStepInComplete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerStepInComplete.setAdapter(new OrganizationInCompleteStepAdapter(getActivity(), profileStatuses, v1 -> {
                    switch (v1.getStep()) {
                        case "1":
                            presenter.openCreateOrganization();
                            break;
                        case "2":
                            if (organizationDetails != null) {
                                presenter.openAddLocal(organizationDetails);
                            } else {
                                // presenter.openCreateOrganization();

                            }
                            break;
                        case "3":
                            if (organizationDetails != null) {
                                presenter.openSetPriceRules(organizationDetails);
                            } else {
                                //presenter.openCreateOrganization();
                            }
                            break;
                    }
                }));

            }
        });

        Observable.fromIterable(data).filter(profileStatus -> profileStatus.getIsComplete().equalsIgnoreCase("1")).toList().subscribe(profileStatuses -> {
            if (profileStatuses.size() == 0) {
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setVisibility(View.GONE);
                }
            } else {
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setVisibility(View.VISIBLE);
                }
            }


            if (recyclerStepComplete != null) {
                recyclerStepComplete.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerStepComplete.setAdapter(new OrganizationInCompleteStepAdapter(getActivity(), profileStatuses, v1 -> {

                }));

            }
        });

    }

    @Override
    public void setOrganizationDetails(OrganizationDetails data) {
        organizationDetails = data;
    }

    @OnClick(R.id.imageViewBack)
    public void onClick() {
        goBack();
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

}
