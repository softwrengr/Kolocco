package com.kooloco.ui.fragment;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.presenter.ChildLoginPresenter;
import com.kooloco.ui.view.LoginView;

/**
 * Created by hlink21 on 1/8/17.
 */

public class ChildFragment extends BaseFragment<ChildLoginPresenter, LoginView> implements LoginView {

    @Override
    protected int createLayout() {
        return R.layout.login_child_layout;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {

        fragmentComponent.inject(this);
    }

    @Override
    protected LoginView createView() {
        return this;
    }

    @Override
    protected void bindData() {

    }
}
