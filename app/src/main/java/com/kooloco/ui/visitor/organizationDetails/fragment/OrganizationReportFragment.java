package com.kooloco.ui.visitor.organizationDetails.fragment;
/**
 * Created by hlink44 on 6/10/17.
 */

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.organizationDetails.presenter.OrganizationReportPresenter;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationReportView;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrganizationReportFragment extends BaseFragment<OrganizationReportPresenter, OrganizationReportView> implements OrganizationReportView {
    @BindView(R.id.customEditTextTitle)
    AppCompatEditText customEditTextTitle;
    @BindView(R.id.customEditTextDesc)
    AppCompatEditText customEditTextDesc;
    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        return R.layout.fragment_organation_report;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrganizationReportView createView() {
        return this;
    }

    @Override
    protected void bindData() {

    }

    @OnClick({R.id.imageViewClose, R.id.buttonSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewClose:
                goBack();
                break;
            case R.id.buttonSend:
                try {
                    validator.submit(customEditTextTitle).checkEmpty().errorMessage("Please enter report title").check();
                    goBack();
                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
        }
    }

}
