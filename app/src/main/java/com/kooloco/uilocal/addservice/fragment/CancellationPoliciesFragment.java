package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 4/10/17.
 */

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.CancellationPoliciciesAdapter;
import com.kooloco.uilocal.addservice.presenter.CancellationPoliciesPresenter;
import com.kooloco.uilocal.addservice.view.CancellationPoliciesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CancellationPoliciesFragment extends BaseFragment<CancellationPoliciesPresenter, CancellationPoliciesView> implements CancellationPoliciesView {

    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;

    @BindView(R.id.recyclerCancellation)
    RecyclerView recyclerCancellation;

    private boolean isEdit = false;

    List<CancellationPolicy> cancellationPolicies;

    CancellationPoliciciesAdapter cancellationPoliciesAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_set_cancellation_policies;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CancellationPoliciesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (isEdit) {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_update));
        } else {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
        }

        if (cancellationPolicies == null) {
            cancellationPolicies = new ArrayList<>();
            presenter.getCancelltion();
        }

        recyclerCancellation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cancellationPoliciesAdapter = new CancellationPoliciciesAdapter(getActivity(), cancellationPolicies);
        recyclerCancellation.setAdapter(cancellationPoliciesAdapter);
    }

    @OnClick({R.id.buttonNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.buttonNext:
                presenter.callWs(cancellationPolicies, isEdit);
                break;

        }
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setData(List<CancellationPolicy> data) {
        cancellationPolicies.addAll(data);
        if (cancellationPolicies.size() != 0) {

            if (checkIsSelectFirst(data)) {
                CancellationPolicy cancellationPolicy = cancellationPolicies.get(0);
                cancellationPolicy.setIsSelected("1");
                cancellationPolicies.set(0, cancellationPolicy);
            }

        }

        if (cancellationPoliciesAdapter != null) {
            cancellationPoliciesAdapter.notifyDataSetChanged();
        }

    }

    private boolean checkIsSelectFirst(List<CancellationPolicy> cancellationPolicies) {
        boolean isSelect = true;

        for (CancellationPolicy cancellationPolicy : cancellationPolicies) {
            if (cancellationPolicy.getIsSelected().equalsIgnoreCase("1")) {
                isSelect = false;
                break;
            }
        }
        return isSelect;
    }

}
