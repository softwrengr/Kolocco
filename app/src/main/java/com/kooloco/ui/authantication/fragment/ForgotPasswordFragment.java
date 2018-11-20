package com.kooloco.ui.authantication.fragment;
/**
 * Created by hlink44 on 27/9/17.
 */

import android.support.v7.widget.AppCompatEditText;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.ui.authantication.presenter.ForgotPasswordPresenter;
import com.kooloco.ui.authantication.view.ForgotPasswordView;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.util.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordFragment extends BaseFragment<ForgotPasswordPresenter, ForgotPasswordView> implements ForgotPasswordView {


    @Inject
    Validator validator;
    @BindView(R.id.signUpEditTextEmail)
    AppCompatEditText signUpEditTextEmail;

    @Override
    protected int createLayout() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ForgotPasswordView createView() {
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
            validator.submit(signUpEditTextEmail).checkEmpty().errorMessage(R.string.val_enter_email).checkValidEmail().errorMessage(R.string.val_email_valid).check();

            //  showMessage(getActivity().getResources().getString(R.string.sucess));
//            goBack();
            presenter.forgotPassword(signUpEditTextEmail.getText().toString());
        } catch (ApplicationException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }

    }
}
