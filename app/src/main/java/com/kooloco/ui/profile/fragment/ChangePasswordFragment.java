package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 27/9/17.
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
import com.kooloco.ui.profile.presenter.ChangePasswordPresenter;
import com.kooloco.ui.profile.view.ChangePasswordView;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePasswordFragment extends BaseFragment<ChangePasswordPresenter, ChangePasswordView> implements ChangePasswordView {

    @BindView(R.id.signUpEditTextOldPassword)
    AppCompatEditText signUpEditTextOldPassword;
    @BindView(R.id.signUpEditTextNewPassword)
    AppCompatEditText signUpEditTextNewPassword;
    @BindView(R.id.signUpEditTextNewConfirmPassword)
    AppCompatEditText signUpEditTextNewConfirmPassword;

    @Inject
    Validator validator;

    @Override
    protected int createLayout() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChangePasswordView createView() {
        return this;
    }

    @Override
    protected void bindData() {

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }


    @OnClick(R.id.buttonUpdate)
    public void onViewClickedUpdate() {
        try {
            validator.submit(signUpEditTextOldPassword).checkEmpty().errorMessage(R.string.val_enter_old_password).check();
            validator.submit(signUpEditTextNewPassword).checkEmpty().errorMessage(R.string.val_enter_new_password).checkMinDigits(4).errorMessage(R.string.val_eneter_new_password_min_four).check();
            validator.submit(signUpEditTextNewConfirmPassword).checkEmpty().errorMessage(R.string.val_confirm_password).matchString("" + signUpEditTextNewPassword.getText().toString()).errorMessage(R.string.val_password_mismatch).check();
          //  showMessage(getActivity().getResources().getString(R.string.sucess));
//            goBack();
            presenter.changePassword(signUpEditTextOldPassword.getText().toString(),signUpEditTextNewPassword.getText().toString());
        } catch (ApplicationException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }

    }
}
