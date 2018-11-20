package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 22/9/17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.ProfilePresenter;
import com.kooloco.ui.profile.view.ProfileView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ProfileFragment extends BaseFragment<ProfilePresenter, ProfileView> implements ProfileView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewEditProfile)
    AppCompatTextView customTextViewEditProfile;
    @BindView(R.id.customTextViewBecomeALocal)
    AppCompatTextView customTextViewBecomeALocal;
    @BindView(R.id.customTextViewCurrency)
    AppCompatTextView customTextViewCurrency;
    @BindView(R.id.customTextViewLanguages)
    AppCompatTextView customTextViewLanguages;
    @BindView(R.id.customTextViewInviteFriends)
    AppCompatTextView customTextViewInviteFriends;
    @BindView(R.id.customTextViewFavorites)
    AppCompatTextView customTextViewFavorites;
    @BindView(R.id.customTextViewSettings)
    AppCompatTextView customTextViewSettings;
    @BindView(R.id.customTextViewKoolocoHelp)
    AppCompatTextView customTextViewKoolocoHelp;

    @Inject
    Session session;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;

    @Override
    protected int createLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ProfileView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setData();
        presenter.getData();
        setDot(imageViewCount);

    }

    private void setData() {
        if (!session.getUser().getProfileImage().isEmpty()) {
            if (imageViewProfile != null)
                Picasso.with(getActivity()).load(session.getUser().getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        if (customTextViewLocalName != null)
            customTextViewLocalName.setText(session.getUser().getFirstname() + " " + session.getUser().getLastname());

        if (customTextViewRatingValue != null) {
            customTextViewRatingValue.setText(session.getUser().getVisitorRate());
        }
    }

    @OnClick(R.id.imageViewProfile)
    public void onViewClicked() {
        presenter.openViewProfile();
    }


    @OnClick({R.id.customTextViewLocalName, R.id.customTextViewEditProfile, R.id.customTextViewBecomeALocal, R.id.customTextViewNotification, R.id.customTextViewCurrency, R.id.customTextViewLanguages, R.id.customTextViewInviteFriends, R.id.customTextViewFavorites, R.id.customTextViewOrderHistory, R.id.customTextViewSettings, R.id.customTextViewKoolocoHelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewLocalName:
                break;
            case R.id.customTextViewEditProfile:
                presenter.openViewProfile();
                break;
            case R.id.customTextViewBecomeALocal:
                presenter.openLocalApp();
                break;
            case R.id.customTextViewCurrency:
                presenter.openCurrency();
                break;
            case R.id.customTextViewNotification:
                presenter.openNotification();
                break;
            case R.id.customTextViewLanguages:
                presenter.openChooseLanguage();
                break;
            case R.id.customTextViewInviteFriends:
                presenter.openInviteFriends();
                break;
            case R.id.customTextViewFavorites:
                presenter.openFav();
                break;
            case R.id.customTextViewSettings:
                presenter.openSetting();
                break;
            case R.id.customTextViewKoolocoHelp:
//                presenter.tempOpen();
                presenter.openKoolocoHelp();
                break;
            case R.id.customTextViewOrderHistory:
                presenter.openOrderHistory();
                break;
        }
    }


    @Override
    public void onShow() {
        super.onShow();
        setData();

        presenter.getData();
    }

    @Override
    public void showCustomMessage(String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getActivity().getResources().getString(R.string.ok_new), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...
                        presenter.openBecomeLocal();
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void updateData() {
        setData();
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

        if (action == EventBusAction.NOTIFICATIONCOUNTUIVISITOR) {
            setDot(imageViewCount);
        }

    }

}
