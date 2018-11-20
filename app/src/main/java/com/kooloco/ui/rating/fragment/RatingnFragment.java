package com.kooloco.ui.rating.fragment;
/**
 * Created by hlink44 on 22/9/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.kooloco.R;
import com.kooloco.core.Common;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.RatingOption;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.rating.presenter.RatingnPresenter;
import com.kooloco.ui.rating.view.RatingnView;
import com.kooloco.util.FirstCapEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RatingnFragment extends BaseFragment<RatingnPresenter, RatingnView> implements RatingnView {

    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.customTextViewSetTitle)
    AppCompatTextView customTextViewSetTitle;
    @BindView(R.id.linearLayoutFacebook)
    LinearLayout linearLayoutFacebook;
    @BindView(R.id.linearLayoutEmail)
    LinearLayout linearLayoutEmail;
    @BindView(R.id.linearLayoutFullStart)
    LinearLayout linearLayoutFullStart;
    @BindView(R.id.customTextView1)
    AppCompatTextView customTextView1;
    @BindView(R.id.customTextView2)
    AppCompatTextView customTextView2;
    @BindView(R.id.customTextView3)
    AppCompatTextView customTextView3;
    @BindView(R.id.customTextView4)
    AppCompatTextView customTextView4;
    @BindView(R.id.customTextView5)
    AppCompatTextView customTextView5;
    @BindView(R.id.customTextView6)
    AppCompatTextView customTextView6;
    @BindView(R.id.customTextView7)
    AppCompatTextView customTextView7;
    @BindView(R.id.linearLayoutRating)
    LinearLayout linearLayoutRating;
    @BindView(R.id.buttonSubmit)
    AppCompatButton buttonSubmit;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;

    String orderId = "";
    String expId = "";


    boolean isNotification = false;
    private boolean isObjection = false;
    OrderDetails data;

    @Override
    protected int createLayout() {
        return R.layout.fragment_rating;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected RatingnView createView() {
        return this;
    }

    @Override
    protected void bindData() {


        presenter.getData();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v >= 4.5) {
                    customTextViewSetTitle.setText(getActivity().getResources().getString(R.string.rating_thank_you_for_your_feedback));
                    linearLayoutFullStart.setVisibility(View.VISIBLE);
                    linearLayoutRating.setVisibility(View.GONE);
                } else {
                    linearLayoutFullStart.setVisibility(View.GONE);
                    linearLayoutRating.setVisibility(View.VISIBLE);
                }
                if (v <= 0.5) {
                    ratingBar.setRating(1);
                }
                if (v <= 1) {
                    unSelectAll(0);
                    setSelcation(4);
                    customTextViewSetTitle.setText(getActivity().getResources().getString(R.string.rating_what_went_wrong));
                } else if (v <= 2) {
                    unSelectAll(0);
                    setSelcation(5);
                    customTextViewSetTitle.setText(getActivity().getResources().getString(R.string.rating_what_went_wrong));
                } else if (v <= 4) {
                    unSelectAll(0);
                    setSelcation(1);
                    customTextViewSetTitle.setText(getActivity().getResources().getString(R.string.rating_what_can_be_improved));
                }
            }
        });
    }

    @OnClick({R.id.customTextView1, R.id.customTextView2, R.id.customTextView3, R.id.customTextView4, R.id.customTextView5, R.id.customTextView6, R.id.customTextView7, R.id.buttonSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextView1:
                setSelcation(1);
                break;
            case R.id.customTextView2:
                setSelcation(2);
                break;
            case R.id.customTextView3:
                setSelcation(3);
                break;
            case R.id.customTextView4:
                setSelcation(4);
                break;
            case R.id.customTextView5:
                setSelcation(5);
                break;
            case R.id.customTextView6:
                setSelcation(6);
                break;
            case R.id.customTextView7:
                setSelcation(7);
                break;
            case R.id.buttonSubmit:
                break;
        }
    }

    private void setSelcation(int pos) {

        if (pos == 1)
            customTextView1.setSelected(!customTextView1.isSelected());
        if (pos == 2)
            customTextView2.setSelected(!customTextView2.isSelected());
        if (pos == 3)
            customTextView3.setSelected(!customTextView3.isSelected());
        if (pos == 4)
            customTextView4.setSelected(!customTextView4.isSelected());
        if (pos == 5)
            customTextView5.setSelected(!customTextView5.isSelected());
        if (pos == 6)
            customTextView6.setSelected(!customTextView6.isSelected());
        if (pos == 7)
            customTextView7.setSelected(!customTextView7.isSelected());

    }


    @OnClick({R.id.buttonSubmit, R.id.textViewBookAnotherDay})
    public void onViewClickedNew(View view) {
        if (customTexteditTextWriteNow.getText().toString().isEmpty()) {
            showMessage(getString(R.string.please_write_your_review));
            return;
        }

        if (orderId.isEmpty()) {
            return;
        }

        List<String> optionIds = new ArrayList<>();

        if (ratingBar.getRating() <= 4.0) {

            if (customTextView1.isSelected()) {
                optionIds.add((String) customTextView1.getTag());
            }

            if (customTextView2.isSelected()) {
                optionIds.add((String) customTextView2.getTag());
            }

            if (customTextView3.isSelected()) {
                optionIds.add((String) customTextView3.getTag());
            }

            if (customTextView4.isSelected()) {
                optionIds.add((String) customTextView4.getTag());
            }

            if (customTextView5.isSelected()) {
                optionIds.add((String) customTextView5.getTag());
            }

            if (customTextView6.isSelected()) {
                optionIds.add((String) customTextView6.getTag());
            }

            if (customTextView7.isSelected()) {
                optionIds.add((String) customTextView7.getTag());
            }
        }


        presenter.sendData(orderId, expId, "" + ratingBar.getRating(), customTexteditTextWriteNow.getText().toString(), optionIds, (view.getId() == R.id.textViewBookAnotherDay));
    }


    private void unSelectAll(int pos) {
        customTextView1.setSelected(pos == 1);
        customTextView2.setSelected(pos == 2);
        customTextView3.setSelected(pos == 3);
        customTextView4.setSelected(pos == 4);
        customTextView5.setSelected(pos == 5);
        customTextView6.setSelected(pos == 6);
        customTextView7.setSelected(pos == 7);
    }


    @Override
    public void setData(List<RatingOption> data) {
        for (int i = 0; i < data.size(); i++) {
            setValue(i, data.get(i));
        }
        presenter.getDataOrder(orderId);
    }

    private void setValue(int pos, RatingOption ratingOption) {

        AppCompatTextView appCompatTextView = null;

        switch (pos) {
            case 0:
                if (customTextView1 != null)
                    appCompatTextView = customTextView1;
                break;
            case 1:
                if (customTextView2 != null)
                    appCompatTextView = customTextView2;

                break;
            case 2:
                if (customTextView3 != null)
                    appCompatTextView = customTextView3;
                break;
            case 3:
                if (customTextView4 != null)
                    appCompatTextView = customTextView4;
                break;
            case 4:
                if (customTextView5 != null)
                    appCompatTextView = customTextView5;
                break;
            case 5:
                if (customTextView6 != null)
                    appCompatTextView = customTextView6;
                break;
            case 6:
                if (customTextView7 != null)
                    appCompatTextView = customTextView7;
                break;
        }

        if (appCompatTextView != null) {

            String textOption = ratingOption.getOptionText();

            switch (textOption) {
                case Common.LocalRate.QUALITY_PRICE:
                    textOption = getString(R.string.local_review_quality_price);
                    break;
                case Common.LocalRate.ARRIVAL_TIME:
                    textOption = getString(R.string.local_review_arrival_time);
                    break;
                case Common.LocalRate.PROFESSIONALISM:
                    textOption = getString(R.string.local_review_professionalism);
                    break;
                case Common.LocalRate.GUIDANCE:
                    textOption = getString(R.string.local_review_guidance);
                    break;
                case Common.LocalRate.MEETING_SPOT:
                    textOption = getString(R.string.local_review_meeting_point);
                    break;
                case Common.LocalRate.ACTIVITY_DURATION:
                    textOption = getString(R.string.local_review_activity_duration);
                    break;
                case Common.LocalRate.Others:
                    textOption = getString(R.string.local_review_other);
                    break;
            }

            appCompatTextView.setText(textOption);
            appCompatTextView.setTag(ratingOption.getId());
        }
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setNotification(boolean isNotification) {
        this.isNotification = isNotification;
    }

    @Override
    public void setOrderData(OrderDetails data) {
        if (customTextViewLocalName != null) {
            customTextViewLocalName.setText(data.getFirstname() + " " + data.getLastname());
        }

        expId = data.getExpereinceNew().getId();

        this.data = data;
    }

    @Override
    public void setIsObjection(boolean isObjection) {
        this.isObjection = isObjection;
    }

    @Override
    public void backToScreen() {
        if (isObjection) {
            //      presenter.openNotificationScreen();
            getActivity().finish();
        } else {
            getActivity().finish();
        }
    }

    @Override
    protected boolean onBackActionPerform() {
        return false;
    }

    @OnClick({R.id.linearLayoutFacebook, R.id.linearLayoutEmail})
    public void onClick(View view) {
        if (data != null){
            ExpereinceNew expereinceNew = data.getExpereinceNew();
            shareExperienceData(expereinceNew.getId(),getString(R.string.share_rating_exp),expereinceNew.getTitle(),expereinceNew.getImage_url(),true);
        }
    }
}
