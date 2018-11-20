package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink44 on 6/10/17.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.profile.presenter.ProfileLocalPresenter;
import com.kooloco.uilocal.profile.view.ProfileLocalView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ProfileLocalFragment extends BaseFragment<ProfileLocalPresenter, ProfileLocalView> implements ProfileLocalView {

    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewEditProfile)
    AppCompatTextView customTextViewEditProfile;

    @Inject
    Session session;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewOrderHistory)
    AppCompatTextView customTextViewOrderHistory;
    @BindView(R.id.customTextViewBecomeAVisitor)
    AppCompatTextView customTextViewBecomeAVisitor;
    @BindView(R.id.customTextViewCurrency)
    AppCompatTextView customTextViewCurrency;
    @BindView(R.id.customTextViewCreateOrg)
    AppCompatTextView customTextViewCreateOrg;
    @BindView(R.id.customTextViewSetAvailabilitites)
    AppCompatTextView customTextViewSetAvailabilitites;
    @BindView(R.id.customTextViewLanguages)
    AppCompatTextView customTextViewLanguages;
    @BindView(R.id.customTextViewKoolocoHelp)
    AppCompatTextView customTextViewKoolocoHelp;
    @BindView(R.id.customTextViewSettings)
    AppCompatTextView customTextViewSettings;
    @BindView(R.id.linearLayoutCreOrg)
    LinearLayout linearLayoutCreOrg;
    @BindView(R.id.segmented_progressbar)
    SegmentedProgressBar segmentedProgressbar;
    @BindView(R.id.textViewStepCount)
    AppCompatTextView textViewStepCount;

    Dialog dialog;
    @BindView(R.id.linearLayoutOpenEditProfile)
    LinearLayout linearLayoutOpenEditProfile;
    @BindView(R.id.textViewAdminApprove)
    AppCompatTextView textViewAdminApprove;
    @BindView(R.id.textViewDetails)
    AppCompatTextView textViewDetails;
    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;

    @Override
    protected int createLayout() {
        return R.layout.fragment_profile_local;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ProfileLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setData();
        presenter.getData();

        setDot(imageViewCount);

    }

    @OnClick({R.id.imageViewProfile, R.id.customTextViewOrderHistory, R.id.customTextViewExp, R.id.customTextViewNotification, R.id.customTextViewEditProfile, R.id.customTextViewBecomeAVisitor, R.id.customTextViewCurrency, R.id.customTextViewCreateOrg, R.id.customTextViewSetAvailabilitites, R.id.customTextViewLanguages, R.id.customTextViewKoolocoHelp, R.id.customTextViewSettings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewProfile:
                presenter.openViewProfile();
                break;
            case R.id.customTextViewOrderHistory:
                break;
            case R.id.customTextViewBecomeAVisitor:
                presenter.openBecomeVisitor();
                break;
            case R.id.customTextViewCurrency:
                break;
            case R.id.customTextViewCreateOrg:

                //As per clint comment org flow change
/*
                if (session.getUser().getIsOrgaization() != null) {
                    if (session.getUser().getIsOrgaization().equalsIgnoreCase("1")) {
                        //presenter.openCreateOrganization();
                        presenter.openEditOrganization();
                    } else {
                        presenter.openCreateOrganization();
                    }
                }
*/
                presenter.openOrgStatus();
                break;
            case R.id.customTextViewSetAvailabilitites:
                presenter.openSetAvailabilities();
                break;
            case R.id.customTextViewNotification:
                presenter.openNotification();
                break;
            case R.id.customTextViewLanguages:
                presenter.openChooseLanguage();
                break;
            case R.id.customTextViewKoolocoHelp:
                presenter.openKoolocoHelp();
                break;
            case R.id.customTextViewEditProfile:
/*
                presenter.openEditProfile();

*/
                presenter.openViewProfile();
                break;
            case R.id.customTextViewSettings:
                presenter.openSettings();
                break;
            case R.id.customTextViewExp:
                if (!session.getUser().getIsBank().equalsIgnoreCase("1")) {
                    showDialogWithOkButton(getString(R.string.message_add_bank_details), getString(R.string.ok_new), new CallBackDeleteAlert() {
                        @Override
                        public void onSuccess(boolean isSuccess) {
                            presenter.openAddBank();
                        }
                    });
                    break;
                }
                presenter.openExp();
                break;
        }
    }


    @Override
    public void onShow() {
        super.onShow();
        setData();
        presenter.getData();
    }

    private void setData() {
        if (!session.getUser().getProfileImage().isEmpty()) {
            if (imageViewProfile != null)
                Picasso.with(getActivity()).load(session.getUser().getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        if (customTextViewLocalName != null) {
            customTextViewLocalName.setText(session.getUser().getFirstname() + " " + session.getUser().getLastname());
        }

        //As per clint comment org flow change

        if (customTextViewCreateOrg != null) {
            customTextViewCreateOrg.setEnabled(session.getUser().getIsAdminApprove().equalsIgnoreCase("1"));
            customTextViewCreateOrg.setAlpha(session.getUser().getIsAdminApprove().equalsIgnoreCase("1") ? 1f : 0.60f);
        }

        if (session.getUser().getIsAffilated() != null) {
            if (linearLayoutCreOrg != null)
                linearLayoutCreOrg.setVisibility(session.getUser().getIsAffilated().equalsIgnoreCase("1") && session.getUser().getIsOrgaization().equalsIgnoreCase("0") ? View.GONE : View.VISIBLE);
        }

        if (customTextViewRatingValue != null) {
            customTextViewRatingValue.setText(session.getUser().getRate());
        }

        if (linearLayoutOpenEditProfile != null) {
            linearLayoutOpenEditProfile.setVisibility(session.getUser().getIsWantToCompelte().equalsIgnoreCase("1") ? View.GONE : View.VISIBLE);
            if (textViewAdminApprove != null) {
                textViewAdminApprove.setVisibility((session.getUser().getIsWantToCompelte().equalsIgnoreCase("1") && session.getUser().getIsAdminApprove().equalsIgnoreCase("0")) ? View.VISIBLE : View.GONE);
            }
            presenter.getStepData();
        }


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
        if (action == EventBusAction.UPDATELIST) {

            //As per clint comment org flow change

            /*
            if (customTextViewCreateOrg != null) {
                if (session.getUser().getIsOrgaization() != null) {
                    customTextViewCreateOrg.setText(session.getUser().getIsOrgaization().equalsIgnoreCase("1") ? getString(R.string.profile_edit_your_own_organization) : getString(R.string.profile_create_your_own_organization));
                }
            }
*/

           /* if (linearLayoutCreOrg != null) {
                if (session.getUser().getIsAffilated() != null) {
                    linearLayoutCreOrg.setVisibility(session.getUser().getIsAffilated().equalsIgnoreCase("1") ? View.GONE : View.VISIBLE);
                }
            }*/
        } else if (action == EventBusAction.UPDATELISTLOCAL) {
            presenter.getData();
        }

        if (action == EventBusAction.NOTIFICATIONCOUNTUILOCAL) {
            setDot(imageViewCount);
        }
    }

    @Override
    public void updateData() {
        setData();
    }

    @Override
    public void setStepCountData(int pendingCount, int stepTootalCount) {

        if (segmentedProgressbar != null) {
            if (stepTootalCount != 0) {
                segmentedProgressbar.setSegmentCount(stepTootalCount);
            }
            segmentedProgressbar.setCompletedSegments(stepTootalCount - pendingCount);
        }
        if (textViewStepCount != null) {
            textViewStepCount.setText((pendingCount) + " " + getResources().getString(R.string.steps_left));
        }
    }

    @OnClick(R.id.linearLayoutOpenEditProfile)
    public void onClickEditProfile() {
        showDialog();
    }


    public void showDialog() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_un_complete_profile, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.callWsDontCompleteProfile();
            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.openLocalInCompleteView();
            }
        });

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialog.setContentView(view);

        dialog.show();


    }


}
