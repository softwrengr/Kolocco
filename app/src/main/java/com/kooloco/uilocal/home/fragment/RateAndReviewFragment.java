package com.kooloco.uilocal.home.fragment;
/**
 * Created by hlink44 on 16/10/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.kooloco.R;
import com.kooloco.core.Common;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Order;
import com.kooloco.model.RateSelectOption;
import com.kooloco.model.RatingOption;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.home.presenter.RateAndReviewPresenter;
import com.kooloco.uilocal.home.view.RateAndReviewView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.kooloco.trimmer.AndroidUtilities.context;

public class RateAndReviewFragment extends BaseFragment<RateAndReviewPresenter, RateAndReviewView> implements RateAndReviewView {

    @BindView(R.id.ratingBarOnTime)
    AppCompatRatingBar ratingBarOnTime;
    @BindView(R.id.ratingBarFriendly)
    AppCompatRatingBar ratingBarFriendly;
    @BindView(R.id.ratingBarRespectful)
    AppCompatRatingBar ratingBarRespectful;
    @BindView(R.id.ratingBarEnjoyable)
    AppCompatRatingBar ratingBarEnjoyable;
    @BindView(R.id.buttonSubmit)
    AppCompatButton buttonSubmit;

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTexteditTextWriteNow)
    FirstCapEditText customTexteditTextWriteNow;
    @BindView(R.id.textView1)
    AppCompatTextView textView1;
    @BindView(R.id.textView2)
    AppCompatTextView textView2;
    @BindView(R.id.textView3)
    AppCompatTextView textView3;
    @BindView(R.id.textView4)
    AppCompatTextView textView4;

    Order order;

    boolean isBackPress = false;


    @Override
    protected int createLayout() {
        return R.layout.fragment_local_rate_and_review;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected RateAndReviewView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        presenter.getData();

        if (order != null) {
            Picasso.with(context).load(order.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);

            customTextViewLocalName.setText(order.getFirstname() + " " + order.getLastname());
        }

        ratingBarOnTime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v <= 1) {
                    ratingBar.setRating(1);
                }
            }
        });
        ratingBarEnjoyable.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v <= 1) {
                    ratingBar.setRating(1);
                }
            }
        });
        ratingBarFriendly.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v <= 1) {
                    ratingBar.setRating(1);
                }
            }
        });
        ratingBarRespectful.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v <= 1) {
                    ratingBar.setRating(1);
                }
            }
        });

    }


    @OnClick(R.id.buttonSubmit)
    public void onViewClicked() {
        if (customTexteditTextWriteNow.getText().toString().isEmpty()) {
            showMessage(getString(R.string.please_write_your_review));
            return;
        }

        if (order == null) {
            return;
        }
        List<RateSelectOption> rateSelectOptions = new ArrayList<>();


        RateSelectOption rateSelectOption1 = new RateSelectOption();

        rateSelectOption1.setOptionId((String) textView1.getTag());
        rateSelectOption1.setRate("" + ratingBarOnTime.getRating());


        RateSelectOption rateSelectOption2 = new RateSelectOption();

        rateSelectOption2.setOptionId((String) textView2.getTag());
        rateSelectOption2.setRate("" + ratingBarFriendly.getRating());


        RateSelectOption rateSelectOption3 = new RateSelectOption();

        rateSelectOption3.setOptionId((String) textView3.getTag());
        rateSelectOption3.setRate("" + ratingBarRespectful.getRating());

        RateSelectOption rateSelectOption4 = new RateSelectOption();

        rateSelectOption4.setOptionId((String) textView4.getTag());
        rateSelectOption4.setRate("" + ratingBarEnjoyable.getRating());


        rateSelectOptions.add(rateSelectOption1);
        rateSelectOptions.add(rateSelectOption2);
        rateSelectOptions.add(rateSelectOption3);
        rateSelectOptions.add(rateSelectOption4);

        presenter.sendData(order.getId(), customTexteditTextWriteNow.getText().toString(), rateSelectOptions);

    }

    @Override
    public void setData(Order data) {
        this.order = data;
    }

    @Override
    public void setDataValue(List<RatingOption> data) {
        for (int i = 0; i < data.size(); i++) {
            setValue(i, data.get(i));
        }
    }

    @Override
    public void goBackScreen() {
        isBackPress = true;
        goBack();
    }

    private void setValue(int pos, RatingOption ratingOption) {

        AppCompatTextView appCompatTextView = null;

        switch (pos) {
            case 0:
                if (textView1 != null)
                    appCompatTextView = textView1;
                break;
            case 1:
                if (textView2 != null)
                    appCompatTextView = textView2;

                break;
            case 2:
                if (textView3 != null)
                    appCompatTextView = textView3;
                break;
            case 3:
                if (textView4 != null)
                    appCompatTextView = textView4;
                break;
        }

        if (appCompatTextView != null) {

            String textOption = ratingOption.getOptionText();

            switch (textOption) {
                case Common.VisitorRate.ON_TIME:
                    textOption = getString(R.string.customer_review_on_time);
                    break;
                case Common.VisitorRate.FRIENDLY:
                    textOption = getString(R.string.customer_review_friendly);
                    break;
                case Common.VisitorRate.RESPECTFUL:
                    textOption = getString(R.string.customer_review_respectful);
                    break;
                case Common.VisitorRate.ENJOYABLE:
                    textOption = getString(R.string.customer_review_enjoyable);
                    break;
            }

            appCompatTextView.setText(textOption);

            appCompatTextView.setTag(ratingOption.getId());
        }
    }

    @Override
    protected boolean onBackActionPerform() {
        return isBackPress;
    }

}
