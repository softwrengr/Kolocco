package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 13/4/18.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.presenter.EditExperiencePresenter;
import com.kooloco.uilocal.expereince.view.EditExperienceView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class EditExperienceFragment extends BaseFragment<EditExperiencePresenter, EditExperienceView> implements EditExperienceView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    PorterShapeImageView imageView;
    @BindView(R.id.customTextViewTitle)
    AppCompatTextView customTextViewTitle;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.textViewRateCount)
    AppCompatTextView textViewRateCount;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.imageViewExp)
    ImageView imageViewExp;
    @BindView(R.id.textViewExpPrice)
    AppCompatTextView textViewExpPrice;
    @BindView(R.id.textViewStepCount)
    AppCompatTextView textViewStepCount;
    @BindView(R.id.segmented_progressbar)
    SegmentedProgressBar segmentedProgressbar;
    @BindView(R.id.textViewDetails)
    AppCompatTextView textViewDetails;
    @BindView(R.id.linearLayoutInCompleteStep)
    LinearLayout linearLayoutInCompleteStep;
    @BindView(R.id.textViewAdminApprove)
    AppCompatTextView textViewAdminApprove;
    @BindView(R.id.textViewExpDetails)
    AppCompatTextView textViewExpDetails;
    @BindView(R.id.textViewExpCategory)
    AppCompatTextView textViewExpCategory;
    @BindView(R.id.textViewExpSport)
    AppCompatTextView textViewExpSport;
    @BindView(R.id.textViewExpSchedulePrice)
    AppCompatTextView textViewExpSchedulePrice;
    @BindView(R.id.textViewExpOtherDetails)
    AppCompatTextView textViewExpOtherDetails;
    @BindView(R.id.textViewExpMeetingSpot)
    AppCompatTextView textViewExpMeetingSpot;
    @BindView(R.id.textViewExpSetSpecialAvailabbilities)
    AppCompatTextView textViewExpSetSpecialAvailabbilities;
    @BindView(R.id.textViewExpDisableTheExperience)
    AppCompatTextView textViewExpDisableTheExperience;
    @BindView(R.id.textViewExpEquipmentChecklist)
    AppCompatTextView textViewExpEquipmentChecklist;
    @BindView(R.id.textViewExpCancellationPolicy)
    AppCompatTextView textViewExpCancellationPolicy;
    @BindView(R.id.linearLayoutExpRoot)
    LinearLayout linearLayoutExpRoot;

    @BindView(R.id.textExpCurrency)
    AppCompatTextView textExpCurrency;

    Dialog dialog;
    private String expId = "";
    private ExpereinceNew expereinceNew;

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_edit;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected EditExperienceView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        hideLoader();
        linearLayoutExpRoot.setClickable(false);
        if (expereinceNew != null) {
            presenter.getStepCount(expereinceNew);
            setDataExperence(expereinceNew);
            presenter.getDataExp(expereinceNew, false);
        }

        new Handler().postDelayed(() -> {
            hideLoderNew();
        }, 1000);
    }

    private void setDataExperence(ExpereinceNew dataExperence) {

        try {
            imageView.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(dataExperence.getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageView);

            customTextViewTitle.setText(dataExperence.getTitle());

            customTextViewTitle.setMinLines(1);

            float rate = 0.0f;

            try {
                rate = Float.parseFloat(dataExperence.getRate());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            ratingBar.setRating(rate);

            textViewRateCount.setText("(" + dataExperence.getRateCount() + ")");


            if (dataExperence.getCity().isEmpty() && dataExperence.getCountry().isEmpty()) {
                customTextViewLocation.setText(R.string.set_meeting_spot);
            } else {
                customTextViewLocation.setText(dataExperence.getLocation());
            }

            textExpCurrency.setText(BaseActivity.currency);


            textViewExpPrice.setText(dataExperence.getPrice());

            if (dataExperence.getExperience_url().isEmpty()) {
                imageViewExp.setVisibility(View.GONE);
            } else {
                imageViewExp.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(dataExperence.getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(imageViewExp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.imageViewBack, R.id.textViewExpDetails, R.id.textViewExpOtherDetailsAnotherFields, R.id.textViewExpCategory, R.id.textViewExpSport, R.id.textViewExpSchedulePrice, R.id.textViewExpOtherDetails, R.id.textViewExpMeetingSpot, R.id.textViewExpSetSpecialAvailabbilities, R.id.textViewExpDisableTheExperience, R.id.textViewExpEquipmentChecklist, R.id.textViewExpCancellationPolicy})
    public void onClick(View view) {
        if (expereinceNew == null) {
            return;
        }
        presenter.setExpNew(expereinceNew);
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewExpDetails:
                presenter.openAddDetails();
                break;
            case R.id.textViewExpCategory:
                presenter.openCategory();
                break;
            case R.id.textViewExpSport:
                presenter.openSport();
                break;
            case R.id.textViewExpSchedulePrice:
                presenter.openSchedulePrice();
                break;
            case R.id.textViewExpOtherDetails:
                presenter.openOtherDetails();
                break;
            case R.id.textViewExpMeetingSpot:
                presenter.openMeetingSpot();
                break;
            case R.id.textViewExpSetSpecialAvailabbilities:
                presenter.openExpSetSpecialAvilabilities();
                break;
            case R.id.textViewExpDisableTheExperience:
                presenter.openExpDisableSpot();
                break;
            case R.id.textViewExpEquipmentChecklist:
                if (expereinceNew.getExperience_url().isEmpty()) {
                    showMessage(getString(R.string.val_first_select_sport_category));
                    break;
                }
                presenter.openExpSportEquipments();
                break;
            case R.id.textViewExpCancellationPolicy:
                presenter.openCancellationPolice();
                break;
            case R.id.textViewExpOtherDetailsAnotherFields:
                presenter.openOtherAddAnotherFieldsDetails();
                break;
        }
    }


    @OnClick(R.id.linearLayoutInCompleteStep)
    public void onClick() {
        presenter.openInCompleteProfile(expereinceNew);
        /*
        showDialogWithOkButton(getString(R.string.let_s_complete_your_local_exp), getString(R.string.var_ok), new CallBackDeleteAlert() {
            @Override
            public void onSuccess(boolean isSuccess) {
                presenter.openInCompleteProfile(expereinceNew);
            }
        });
*/
    }


    public void showDialog() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_un_complete_exp, null, false);

        AppCompatButton appCompatButtonNo = view.findViewById(R.id.buttonNo);
        AppCompatButton appCompatButtonYes = view.findViewById(R.id.buttonYes);

        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        appCompatButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                presenter.openInCompleteProfile(expereinceNew);
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

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setExpNew(ExpereinceNew expereinceNew) {
        this.expereinceNew = expereinceNew;
    }

    @Override
    public void setData(ExpereinceNew data) {
        expereinceNew = data;
        setDataExperence(expereinceNew);
    }

    @Override
    public void setStepCountData(int stepPendingCount, List<ProfileStatus> progress) {
    /*    if (stepPendingCount == 0) {
            linearLayoutInCompleteStep.setVisibility(View.GONE);
        }*/

        int stepTootalCount = progress.size();
        if (segmentedProgressbar != null) {
            if (stepTootalCount != 0) {
                segmentedProgressbar.setSegmentCount(stepTootalCount);
            }
            segmentedProgressbar.setCompletedSegments(stepTootalCount - stepPendingCount);
        }
        if (textViewStepCount != null) {
            textViewStepCount.setText((stepPendingCount) + " " + getResources().getString(R.string.steps_left));
        }

        if (linearLayoutInCompleteStep != null) {
            linearLayoutInCompleteStep.setVisibility((stepPendingCount == 0) ? View.GONE : View.VISIBLE);

            if (textViewAdminApprove != null) {
                textViewAdminApprove.setVisibility(((stepPendingCount == 0) && expereinceNew.getIsAdminApprove().equalsIgnoreCase("0")) ? View.VISIBLE : View.GONE);
            }
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
        if (action == EventBusAction.NOTIFICATIONEXPAPPROVE) {
            presenter.getDataExp(expereinceNew, false);
        } else if (action == EventBusAction.NOTIFICATIONEXPREJEXT) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goBack();
                }
            });
        }
    }
}
