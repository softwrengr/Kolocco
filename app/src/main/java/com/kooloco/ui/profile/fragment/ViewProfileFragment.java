package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.core.Common;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.RatingOption;
import com.kooloco.model.Review;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.ViewProfilePresenter;
import com.kooloco.ui.profile.view.ViewProfileView;
import com.kooloco.ui.visitor.dashboard.adapter.RateReviewAdapter;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewProfileFragment extends BaseFragment<ViewProfilePresenter, ViewProfileView> implements ViewProfileView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewEditProfile)
    AppCompatTextView customTextViewEditProfile;
    @BindView(R.id.customTextViewCurrency)
    AppCompatTextView customTextViewCurrency;
    @BindView(R.id.customTextViewLanguages)
    AppCompatTextView customTextViewLanguages;
    @BindView(R.id.customTextViewAppointmentValue)
    AppCompatTextView customTextViewAppointmentValue;
    @BindView(R.id.customTextViewOrderHistory)
    AppCompatTextView customTextViewOrderHistory;
    @BindView(R.id.customTextViewNTMY)
    AppCompatTextView customTextViewNTMY;
    @BindView(R.id.customTextViewMyRatingValue)
    AppCompatTextView customTextViewMyRatingValue;
    @BindView(R.id.ratingBarCommunication)
    AppCompatRatingBar ratingBarCommunication;
    @BindView(R.id.ratingBarSportSkills)
    AppCompatRatingBar ratingBarSportSkills;
    @BindView(R.id.ratingBarService)
    AppCompatRatingBar ratingBarService;
    @BindView(R.id.ratingBarQualityPrice)
    AppCompatRatingBar ratingBarQualityPrice;
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.textViewReadAll)
    AppCompatTextView textViewReadAll;
    @BindView(R.id.textView1)
    AppCompatTextView textView1;
    @BindView(R.id.textView2)
    AppCompatTextView textView2;
    @BindView(R.id.textView3)
    AppCompatTextView textView3;
    @BindView(R.id.textView4)
    AppCompatTextView textView4;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    private User data;

    int pageRate = 1;
    int tootalCount = 0;
    boolean isCallApi = true;

    @Inject
    Session session;

    RateReviewAdapter rateReviewAdapter;

    List<Review> reviews;

    @BindView(R.id.viewNTYM)
    View viewNTYM;

    @Override
    protected int createLayout() {
        return R.layout.fragment_view_profile;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ViewProfileView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        //It is used to set review data
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        rateReviewAdapter = new RateReviewAdapter(getActivity(), reviews);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerReviews.setAdapter(rateReviewAdapter);

        presenter.getData();
    }

    @Override
    public void setData(User data) {
        this.data = data;


        if (!session.getUser().getProfileImage().isEmpty()) {
            Picasso.with(getActivity()).load(data.getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        customTextViewLocalName.setText(data.getFirstname() + " " + data.getLastname());


        customTextViewMyRatingValue.setText(data.getVisitorRate());
        customTextViewRatingValue.setText(data.getVisitorRate());

        customTextViewCurrency.setText(data.getCurrency());
        customTextViewLanguages.setText(data.getLanguage());

        customTextViewAppointmentValue.setText(data.getAppointmentCount());

        customTextViewNTMY.setText(data.getIntroYourSelf());

        if (data.getIntroYourSelf() == null) {
            data.setIntroYourSelf("");
        }

        if (customTextViewNTMY != null) {
            customTextViewNTMY.setVisibility((data.getIntroYourSelf().isEmpty()) ? View.GONE : View.VISIBLE);
        }

        if (viewNTYM != null) {
            viewNTYM.setVisibility((data.getIntroYourSelf().isEmpty()) ? View.GONE : View.VISIBLE);
        }


        customTextViewMyRatingValue.setText(data.getVisitorRate());

        setDataValue(data.getRatingOptions());

        isCallApi = true;
        callApi(true);

    }


    public void setDataValue(List<RatingOption> data) {
        for (int i = 0; i < data.size(); i++) {
            setValue(i, data.get(i));
        }
    }

    private void setValue(int pos, RatingOption ratingOption) {

        AppCompatTextView appCompatTextView = null;

        float rate = 0.0f;

        if (ratingOption.getRate() != null) {
            try {
                rate = Float.parseFloat(ratingOption.getRate());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                rate = 0.0f;
            }

        }
        switch (pos) {
            case 0:
                if (textView1 != null)
                    appCompatTextView = textView1;

                if (ratingBarCommunication != null)
                    ratingBarCommunication.setRating(rate);
                break;
            case 1:
                if (textView2 != null)
                    appCompatTextView = textView2;

                if (ratingBarSportSkills != null)
                    ratingBarSportSkills.setRating(rate);

                break;
            case 2:
                if (textView3 != null)
                    appCompatTextView = textView3;

                if (ratingBarService != null)
                    ratingBarService.setRating(rate);

                break;
            case 3:
                if (textView4 != null)
                    appCompatTextView = textView4;

                if (ratingBarQualityPrice != null)
                    ratingBarQualityPrice.setRating(rate);

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

    @OnClick({R.id.textViewReadAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewReadAll:
//                readData();
                callApi(false);
                break;
        }
    }

    private void callApi(boolean isFirst) {
        if (isCallApi) {
            if (isFirst) {
                pageRate = 1;
                reviews.clear();
            } else {
                pageRate = pageRate + 1;
            }
            presenter.getRating(pageRate);
            isCallApi = false;
        }
    }

    @Override
    public void setDataRating(List<Review> data, int pg, int tootalCount) {
        if (pg == 1) {
            this.tootalCount = tootalCount;
        }

        if (data.size() != 0) {
            isCallApi = true;
        }

        reviews.addAll(data);

        if (linearLayoutNoData != null)
            linearLayoutNoData.setVisibility((reviews.size() == 0) ? View.VISIBLE : View.GONE);

        if (rateReviewAdapter != null) {
            rateReviewAdapter.notifyDataSetChanged();
        }

        setRatingData();
    }


    private void setRatingData() {

        try {
            customTextViewRatingCount.setText("(" + tootalCount + ")");

            if (textViewReadAll != null) {
                textViewReadAll.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.INVISIBLE : View.VISIBLE);
                textViewReadAll.setText(getString(R.string.review_new)+" " + (tootalCount - reviews.size()) +" "+ getString(R.string.reviews_new));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        presenter.onBack();
    }

    @OnClick(R.id.customTextViewOrderHistory)
    public void onViewClicked() {
        presenter.openOrderHistory();
    }

    @OnClick(R.id.customTextViewEditProfile)
    public void onViewClickedEditProfile() {
        presenter.openEditProfile();
    }


    @OnClick(R.id.textViewLanguages)
    public void onViewClickedEditLanguages() {
        presenter.openSpeakLanguages();
    }

}
