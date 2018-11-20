package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 17/4/18.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.CancellationPoliciciesAdapter;
import com.kooloco.uilocal.expereince.adapter.CancellationPoliciciesNewAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperienceCancellationPoliciesPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceCancellationPoliciesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceCancellationPoliciesFragment extends BaseFragment<ExperienceCancellationPoliciesPresenter, ExperienceCancellationPoliciesView> implements ExperienceCancellationPoliciesView {
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;

    @BindView(R.id.recyclerCancellation)
    RecyclerView recyclerCancellation;

    private boolean isEdit = false;

    List<CancellationPolicy> cancellationPolicies;

    CancellationPoliciciesNewAdapter cancellationPoliciesAdapter;
    private String expId = "";

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_cancellation_policies;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceCancellationPoliciesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (isEdit) {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_done));
        } else {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
        }

        if (cancellationPolicies == null) {
            cancellationPolicies = new ArrayList<>();
            presenter.getCancelltion(expId);
        }

        recyclerCancellation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

       // cancellationPoliciesAdapter = new CancellationPoliciciesAdapter(getActivity(), cancellationPolicies);

        cancellationPoliciesAdapter = new CancellationPoliciciesNewAdapter(getActivity(), cancellationPolicies);

        recyclerCancellation.setAdapter(cancellationPoliciesAdapter);
    }

    @OnClick({R.id.buttonNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.buttonNext:
                presenter.callWs(expId, cancellationPolicies, isEdit);
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

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
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
