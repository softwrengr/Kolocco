package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 5/10/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.addservice.presenter.SuccessLocalPresenter;
import com.kooloco.uilocal.addservice.view.SuccessLocalView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class SuccessLocalFragment extends BaseFragment<SuccessLocalPresenter, SuccessLocalView> implements SuccessLocalView {


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_sucess;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SuccessLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
    }

    @OnClick(R.id.buttonOkGreat)
    public void onViewClicked() {
        EventBus.getDefault().post(EventBusAction.LOCALEXPADD);
        getActivity().finish();
    }
}
