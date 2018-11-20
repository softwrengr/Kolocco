package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 27/9/17.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.SettingsPresenter;
import com.kooloco.ui.profile.view.SettingsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsFragment extends BaseFragment<SettingsPresenter, SettingsView> implements SettingsView {

    @BindView(R.id.checkBoxNotification)
    CheckBox checkBoxNotification;
    @BindView(R.id.checkBoxNotificationByEmail)
    CheckBox checkBoxNotificationByEmail;
    @BindView(R.id.customTextViewChangePassword)
    AppCompatEditText customTextViewChangePassword;

    @Inject
    Session session;

    @Override
    protected int createLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SettingsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (session.getUser().getSignupType().equalsIgnoreCase("S")) {
            customTextViewChangePassword.setVisibility(View.VISIBLE);
        } else {
            customTextViewChangePassword.setVisibility(View.GONE);
        }

        checkBoxNotification.setChecked(session.getUser().getAppNotification().equalsIgnoreCase("1"));
        checkBoxNotificationByEmail.setChecked(session.getUser().getMailNotification().equalsIgnoreCase("1"));

    }

    @OnClick({R.id.checkBoxNotification, R.id.editTextNotification, R.id.editTextNotificationByEmail, R.id.linearLayoutNotification, R.id.checkBoxNotificationByEmail, R.id.linearLayoutNotificationByEmail, R.id.customTextViewChangePassword, R.id.customTextViewAddPaymentDetails, R.id.customTextViewAddBankDetails, R.id.customTextViewTermsAndCondition, R.id.imageViewLogOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutNotification:
            case R.id.editTextNotification:
                checkBoxNotification.setChecked(!checkBoxNotification.isChecked());
                presenter.callWsNotification();
                break;

            case R.id.linearLayoutNotificationByEmail:
            case R.id.editTextNotificationByEmail:
                checkBoxNotificationByEmail.setChecked(!checkBoxNotificationByEmail.isChecked());
                presenter.callWsNotificationEmail();
                break;

            case R.id.checkBoxNotification:
                presenter.callWsNotification();
                break;

            case R.id.checkBoxNotificationByEmail:
                presenter.callWsNotificationEmail();
                break;

            case R.id.customTextViewChangePassword:
                presenter.openChangePassword();
                break;
            case R.id.customTextViewAddPaymentDetails:
                presenter.openCardDetails();
                break;
            case R.id.customTextViewAddBankDetails:
                presenter.openAddBankScreen();
                break;
            case R.id.customTextViewTermsAndCondition:
                presenter.openWebOpenView(getString(R.string.toolbat_terms_conditions));
                break;
            case R.id.imageViewLogOut:
                presenter.openAuth(getDatabase());
                break;
        }
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void logout() {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        presenter.openAuthActivity();
    }
}
