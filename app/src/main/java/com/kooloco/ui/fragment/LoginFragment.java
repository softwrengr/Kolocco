package com.kooloco.ui.fragment;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.presenter.LoginPresenter;
import com.kooloco.ui.view.LoginView;

/**
 * Created by hlink21 on 14/7/17.
 */

public class LoginFragment extends BaseFragment<LoginPresenter, LoginView> implements LoginView {


    @Override
    protected int createLayout() {
        return R.layout.login_layout;
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

    @Override
    public int getChildPlaceHolder() {
        return R.id.childPlaceHolder;
    }
}
