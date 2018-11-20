package com.kooloco.ui.expereince.fragment;
/**
 * Created by hlink on 18/4/18.
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.data.URLFactory;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.AddImages;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpFavDataBase;
import com.kooloco.model.LocalImage;
import com.kooloco.model.Medium;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.adapter.ExpAnotherFieldsAdapter;
import com.kooloco.ui.expereince.adapter.ExperienceCancellationPolicyAdapter;
import com.kooloco.ui.expereince.presenter.ExperienceDetailsPresenter;
import com.kooloco.ui.expereince.view.ExperienceDetailsView;
import com.kooloco.ui.visitor.dashboard.adapter.DashboardSlider;
import com.kooloco.ui.visitor.dashboard.adapter.DialogImageSlider;
import com.kooloco.ui.visitor.dashboard.adapter.RateReviewAdapter;
import com.kooloco.util.AppBarStateChangeListener;
import com.kooloco.util.DayDisableDecorator;
import com.kooloco.util.DayEnableDecorator;
import com.kooloco.util.DayMaximumDecorator;
import com.kooloco.util.DayOutRangeDecorator;
import com.kooloco.util.FlowLayout;
import com.kooloco.util.StaticMap;
import com.kooloco.util.picaso.CircleTransform;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceDetailsFragment extends BaseFragment<ExperienceDetailsPresenter, ExperienceDetailsView> implements ExperienceDetailsView {

    @BindView(R.id.recyclerViewImageSlide)
    RecyclerView recyclerViewImageSlide;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.imageViewShare)
    ImageView imageViewShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.customTextViewExpName)
    AppCompatTextView customTextViewExpName;
    @BindView(R.id.ratingBarExp)
    AppCompatRatingBar ratingBarExp;
    @BindView(R.id.textViewRateCount)
    AppCompatTextView textViewRateCount;
    @BindView(R.id.customTextViewExpLocation)
    AppCompatTextView customTextViewExpLocation;
    @BindView(R.id.textViewExpPrice)
    AppCompatTextView textViewExpPrice;
    @BindView(R.id.customTextViewExpDuration)
    AppCompatTextView customTextViewExpDuration;
    @BindView(R.id.customTextViewExpPart)
    AppCompatTextView customTextViewExpPart;
    @BindView(R.id.customTextViewExpDesc)
    AppCompatTextView customTextViewExpDesc;
    @BindView(R.id.imageViewSportService)
    ImageView imageViewSportService;
    @BindView(R.id.customTextViewExpSportName)
    AppCompatTextView customTextViewExpSportName;
    @BindView(R.id.linearLayoutSportRow)
    LinearLayout linearLayoutSportRow;
    @BindView(R.id.imageViewExpService)
    ImageView imageViewExpService;
    @BindView(R.id.customTextViewExpServiceName)
    AppCompatTextView customTextViewExpServiceName;
    @BindView(R.id.linearLayoutExpServiceRow)
    LinearLayout linearLayoutExpServiceRow;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.customTextViewExpMettingSpot)
    AppCompatTextView customTextViewExpMettingSpot;
    @BindView(R.id.customTextViewExpHighlights)
    AppCompatTextView customTextViewExpHighlights;
    @BindView(R.id.linearLayoutHighlights)
    LinearLayout linearLayoutHighlights;
    @BindView(R.id.customTextViewExpIncluding)
    AppCompatTextView customTextViewExpIncluding;
    @BindView(R.id.linearLayoutIncluding)
    LinearLayout linearLayoutIncluding;
    @BindView(R.id.calendarViewAvailability)
    MaterialCalendarView calendarViewAvailability;
    @BindView(R.id.textViewNote)
    AppCompatTextView textViewNote;
    @BindView(R.id.linearLayoutOtherFields)
    LinearLayout linearLayoutOtherFields;
    @BindView(R.id.customTextViewExpNotIncluding)
    AppCompatTextView customTextViewExpNotIncluding;
    @BindView(R.id.linearLayoutNotIncluding)
    LinearLayout linearLayoutNotIncluding;
    @BindView(R.id.textViewLocalName)
    AppCompatTextView textViewLocalName;
    @BindView(R.id.textViewLocalDesc)
    AppCompatTextView textViewLocalDesc;
    @BindView(R.id.textViewLocalLang)
    AppCompatTextView textViewLocalLang;
    @BindView(R.id.imageViewOrgImage)
    ImageView imageViewOrgImage;
    @BindView(R.id.linearLayoutLocal)
    LinearLayout linearLayoutLocal;
    @BindView(R.id.customTextViewCancelPolicyName)
    AppCompatTextView customTextViewCancelPolicyName;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.buttonResetFilter)
    AppCompatButton buttonResetFilter;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.textViewReadAll)
    AppCompatTextView textViewReadAll;
    @BindView(R.id.viewHide)
    View viewHide;
    @BindView(R.id.buttonBookNow)
    AppCompatButton buttonBookNow;
    @BindView(R.id.recyclerAnotherFields)
    RecyclerView recyclerAnotherFields;
    int tootalCount = 0;
    List<Review> reviews;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.customTextViewExpLevel)
    AppCompatTextView customTextViewExpLevel;
    @BindView(R.id.customTextViewExpPerfect)
    AppCompatTextView customTextViewExpPerfect;
    @BindView(R.id.checkboxFav)
    AppCompatCheckBox checkboxFav;
    @BindView(R.id.recyclerViewCancellation)
    RecyclerView recyclerViewCancellation;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;
    @BindView(R.id.textViewCurrencyType)
    AppCompatTextView textViewCurrencyType;

    private RateReviewAdapter rateReviewAdapter;
    boolean isCallApi = true;
    int pageRate = 1;

    boolean isLocalApp;
    private String expId = "";

    List<CalendarDay> calendarDays = new ArrayList<>();
    private ExpDetails expDetails;

    Dialog dialog;

    String year = "";

    boolean isRedirect = false;

    private VelocityTracker velocityTracker;
    private float velocity;

    AppBarStateChangeListener.State stateCustom;

    Calendar calendarMaximumDate;

    @BindView(R.id.textViewSeeAllDate)
    AppCompatTextView textViewSeeAllDate;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Override
    protected int createLayout() {
        return R.layout.exp_visitor_and_local_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceDetailsView createView() {
        return this;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void bindData() {

        textViewCurrencyType.setText(BaseActivity.currency);

        isRedirect = false;
        shimmerViewContainer.setVisibility(View.VISIBLE);
        shimmerViewContainer.setDuration(1000);
        shimmerViewContainer.setAngle(ShimmerFrameLayout.MaskAngle.CW_0);
        shimmerViewContainer.setDropoff(0.15f);

        shimmerViewContainer.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);

        shimmerViewContainer.startShimmerAnimation();

        calendarDays.clear();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        buttonBookNow.setVisibility(isLocalApp ? View.GONE : View.VISIBLE);
        textViewNote.setVisibility(View.GONE);

        calendarViewAvailability.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

        calendarViewAvailability.setSelectionMode(isLocalApp ? MaterialCalendarView.SELECTION_MODE_NONE : MaterialCalendarView.SELECTION_MODE_SINGLE);

//        calendarViewAvailability.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar calendarMinimumDate = Calendar.getInstance();

        year = new SimpleDateFormat("yyyy").format(calendarMinimumDate.getTime());

        calendarViewAvailability.setMinimumDate(calendarMinimumDate);


        //87 days code
        /*if (!isLocalApp) {
            calendarMaximumDate = Calendar.getInstance();

            calendarMaximumDate.set(Calendar.DAY_OF_MONTH, 1);


            calendarMaximumDate.add(Calendar.MONTH, 3);

            calendarMaximumDate.add(Calendar.DATE, -1);

            calendarViewAvailability.setMaximumDataCustom(calendarMaximumDate);
        }*/

        if (!isLocalApp) {
            calendarMaximumDate = Calendar.getInstance();

            calendarMaximumDate.add(Calendar.DATE, 86);

            calendarViewAvailability.setMaximumDataCustom(calendarMaximumDate);
        }

        calendarViewAvailability.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (expDetails != null) {
                    if (isRedirect) {
                        presenter.openSelectExpDateForDate(expDetails, date.getDate());
                    }
                }
            }
        });

        calendarViewAvailability.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                if (!isLocalApp) {
                    if (date.getMonth() > (calendarMaximumDate.get(Calendar.MONTH))) {
                        Log.e("error", "Set Disable data");
                        setSeeAllButton(true);
                    } else {
                        Log.e("error", "Set enable data");
                        setSeeAllButton(false);
                    }
                } else {

                    //Comment 87 days

                    String newYear = new SimpleDateFormat("yyyy").format(date.getDate().getTime());
                    if (!year.equalsIgnoreCase(newYear)) {
                        year = new SimpleDateFormat("yyyy").format(date.getDate().getTime());
                        presenter.getDataYear(expId, year);
                    }
                }

            }
        });


        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                stateCustom = state;

                if (state == State.COLLAPSED) {
                    imageViewShare.setColorFilter(getActivity().getResources().getColor(R.color.black));
                    imageViewBack.setColorFilter(getActivity().getResources().getColor(R.color.black));

                    if (!checkboxFav.isChecked()) {
                        checkboxFav.setSupportButtonTintList(ContextCompat.getColorStateList(getActivity(), R.color.black));
                    }

                } else if (state == State.EXPANDED) {
                    imageViewBack.setColorFilter(getActivity().getResources().getColor(R.color.white));

                    imageViewShare.setColorFilter(getActivity().getResources().getColor(R.color.white));

                    checkboxFav.setSupportButtonTintList(null);
                }
            }
        });


        checkboxFav.setVisibility(isLocalApp ? View.INVISIBLE : View.VISIBLE);

        checkboxFav.setOnClickListener(view -> {
            checkboxFav.setScaleX(0.5f);
            checkboxFav.setScaleY(0.5f);
            animIcon(checkboxFav, DynamicAnimation.SCALE_X, 1f);
            animIcon(checkboxFav, DynamicAnimation.SCALE_Y, 1f);

            if (stateCustom != null) {
                if (stateCustom == AppBarStateChangeListener.State.COLLAPSED) {

                    imageViewBack.setColorFilter(getActivity().getResources().getColor(R.color.black));
                    imageViewShare.setColorFilter(getActivity().getResources().getColor(R.color.black));

                    if (!checkboxFav.isChecked()) {
                        checkboxFav.setSupportButtonTintList(ContextCompat.getColorStateList(getActivity(), R.color.black));
                    } else {
                        checkboxFav.setSupportButtonTintList(null);
                    }
                }
            }

            if (expDetails != null) {
                setFavExp(expDetails.getId(), status -> {

                });
            }
        });
        reviews = new ArrayList<>();

        rateReviewAdapter = new RateReviewAdapter(getActivity(), reviews);

        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerReviews.setAdapter(rateReviewAdapter);
        recyclerReviews.setNestedScrollingEnabled(false);

        recyclerAnotherFields.setNestedScrollingEnabled(false);
        recyclerViewImageSlide.setNestedScrollingEnabled(false);

        if (expId.isEmpty()) {
            presenter.getData();

        } else {
            //  shimmerViewContainer.startShimmerAnimation();
            presenter.getData(expId, isLocalApp);

        }


    }


    private void setTagData(List<String> tags) {
        flowLayout.removeAllViews();
        for (String tagDe : tags) {
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_time, null);
            ((AppCompatTextView) view1.findViewById(R.id.customTextViewTag)).setText(tagDe);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewD) {

                }
            });
            flowLayout.addView(view1);
        }
    }


    public void showDialog(List<LocalImage> localImages, int position) {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_slide_image, null, false);

        final AppCompatTextView textViewCurrent = view.findViewById(R.id.textViewCurrent);

        AppCompatTextView textViewTotal = view.findViewById(R.id.textViewTotal);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerImageSlider);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new DialogImageSlider(getActivity(), localImages));
        recyclerView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (textViewCurrent != null) {
                    firstVisibleItemPosition = firstVisibleItemPosition + 1;
                    textViewCurrent.setText("" + firstVisibleItemPosition);
                }
            }
        });


        recyclerView.smoothScrollToPosition(position);

        textViewTotal.setText("/" + localImages.size());

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setContentView(view);

        dialog.show();


    }


    @OnClick({R.id.imageViewBack, R.id.imageViewShare, R.id.buttonBookNow, R.id.textViewSeeAllDate, R.id.textViewReadAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageViewShare:
                if (expDetails != null) {
                    shareExperienceData(expDetails.getId(), expDetails.getTitle(), expDetails.getDesc(), expDetails.getImages().get(0).getLocalImage(), true);
                }
                break;
            case R.id.textViewReadAll:
                callApi(false);
                break;
            case R.id.buttonBookNow:
            case R.id.textViewSeeAllDate:
                if (expDetails != null) {
                    presenter.openSelectExpDate(expDetails);
                }
                break;
        }
    }

    @Override
    public void setData(ExpDetails expDetails) {

        if (getActivity() == null) {
            return;
        }

        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.stopShimmerAnimation();

        this.expDetails = expDetails;

        setTagData(expDetails.getTags());

        pageIndicatorView.setCount(expDetails.getImages().size());
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setStrokeWidth(2);

        DashboardSlider dashboardSlider = new DashboardSlider(getActivity(), expDetails.getImages(), new DashboardSlider.CallBack() {
            @Override
            public void onClick(int position) {
                showDialog(expDetails.getImages(), position);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImageSlide.setLayoutManager(layoutManager);
        recyclerViewImageSlide.setAdapter(dashboardSlider);
        recyclerViewImageSlide.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewImageSlide);

        recyclerViewImageSlide.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                pageIndicatorView.setSelection(firstVisibleItemPosition);
            }
        });

        customTextViewExpName.setText(expDetails.getTitle());

        float rating = 0.0f;
        try {
            rating = Float.parseFloat(expDetails.getRating());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ratingBarExp.setRating(rating);
        customTextViewRatingCount.setText(expDetails.getRatingCount());

        textViewRateCount.setText("(" + expDetails.getRatingCount() + ")");
//Set is FAv

        ExpFavDataBase dataFavExp = databaseCacheDataSource.getDataFavExp(expDetails.getId(), expDetails.getIsFavorite());

        if (dataFavExp != null) {
            checkboxFav.setChecked(dataFavExp.getIsFav().equalsIgnoreCase("1"));
        }

        String per = getActivity().getResources().getString(R.string.capecity) + " " + expDetails.getCapacity() + " " + getActivity().getResources().getString(R.string.participants);
        customTextViewExpPart.setText(per);

        customTextViewExpLocation.setText((expDetails.getCity().isEmpty() ? expDetails.getCountry() : expDetails.getCity() + ", " + expDetails.getCountry()));

        customTextViewExpDuration.setText(getString(R.string.exp_duration) + " " + expDetails.getDuration());
        customTextViewExpDesc.setText(expDetails.getDesc());
        textViewExpPrice.setText(expDetails.getPrice());

        customTextViewExpLevel.setText(getString(R.string.exp_level) + " " + expDetails.getExpLevel());

        customTextViewExpPerfect.setText(expDetails.getExpPerfect());


        if (expDetails.getSportActivity() != null) {
            customTextViewExpSportName.setText(expDetails.getSportActivity().getName());

            if (expId.isEmpty()) {
                Picasso.with(getActivity()).load("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/Climbing_3x.png").placeholder(R.drawable.place).into(imageViewSportService);
            } else {
                Picasso.with(getActivity()).load(expDetails.getSportActivity().getServiceImage()).placeholder(R.drawable.place).into(imageViewSportService);
            }

        }

        if (expDetails.getActivities() != null) {
            customTextViewExpServiceName.setText(expDetails.getActivities().getName());

            if (expId.isEmpty()) {
                Picasso.with(getActivity()).load("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/experience_icon/Dashboard_session3x.png").placeholder(R.drawable.place).into(imageViewExpService);
            } else {
                Picasso.with(getActivity()).load(expDetails.getActivities().getImageUrlTwo()).placeholder(R.drawable.place).into(imageViewExpService);
            }

        }

        customTextViewExpMettingSpot.setText(expDetails.getMettingAddress());

        String staticMapUrl = StaticMap.getUrl(getActivity(), expDetails.getMeetingLet(), expDetails.getMeetingLng(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);

        customTextViewExpHighlights.setText(expDetails.getHeighlight());
        customTextViewExpIncluding.setText(expDetails.getInclude());
        customTextViewExpNotIncluding.setText(expDetails.getNotInclude());

        calendarDays.addAll(expDetails.getCalendarDays());

        setCalData(calendarDays);

       /* calendarViewAvailability.addDecorator(new DayEnableDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addDecorator(new DayDisableDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addDecorator(new DayOutRangeDecorator(calendarDays, getActivity()));

        calendarViewAvailability.addAllDisableDays(calendarDays);
*/

        //  presenter.getDataYear(expId, year);

        textViewLocalName.setText(expDetails.getLocalName());
        textViewLocalDesc.setText(expDetails.getLocalDesc());
        textViewLocalLang.setText(expDetails.getLocalLanguage());

        Picasso.with(getActivity()).load(expDetails.getLocalImage()).placeholder(R.drawable.place).transform(new CircleTransform()).into(imageViewOrgImage);


        Observable.fromIterable(expDetails.getOtherDetailsAnotherFields()).map(otherDetailsAnotherFields1 -> {
            List<AddImages> tempImages = new ArrayList<>();

            for (Medium medium : otherDetailsAnotherFields1.getMedia()) {
                AddImages addImage = new AddImages();

                addImage.setImageUrl(medium.getImage());
                addImage.setSetId(medium.getId());
                addImage.setFileName(medium.getFile());
                addImage.setVideo(false);
                tempImages.add(addImage);
            }

            otherDetailsAnotherFields1.setAddImages(tempImages);
            return otherDetailsAnotherFields1;

        }).subscribe(lists -> {
            recyclerAnotherFields.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerAnotherFields.setAdapter(new ExpAnotherFieldsAdapter(getActivity(), expDetails.getOtherDetailsAnotherFields(), new ExpAnotherFieldsAdapter.CallBack() {
                @Override
                public void onClickItem(String imageUrl) {
                    imageOpenZoom(imageUrl);
                }
            }));
        });

        isRedirect = true;

        //Temp name cancellation policy
        customTextViewCancelPolicyName.setText(getActivity().getString(R.string.dashboard_cancellation_policy) + " (" + expDetails.getCancelletionPolicyName() + ") ");
        //    customTextViewCancelPolicyDesc.setText(expDetails.getCancelletionPolicyDesc());

//        customTextViewCancelPolicyName.setText(expDetails.getCancelletionPolicyName());

        recyclerViewCancellation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        recyclerViewCancellation.setAdapter(new ExperienceCancellationPolicyAdapter(getActivity(), expDetails.getCancelletionPolicyList(), new ExperienceCancellationPolicyAdapter.CallBack() {
            @Override
            public void onClickListener() {

            }
        }));


        setTagData(expDetails.getTags());

        ratingBar.setRating(rating);

        isCallApi = true;
        callApi(true);

        //   shimmerLayout.stopShimmerAnimation();


    }

    @Override
    public void setDataRating(List<Review> list, int pageRate, int count) {
        if (pageRate == 1) {
            this.tootalCount = count;
        }

        if (list.size() != 0) {
            isCallApi = true;
        }

        reviews.addAll(list);

        if (linearLayoutNoData != null)
            linearLayoutNoData.setVisibility((reviews.size() == 0) ? View.VISIBLE : View.GONE);

        if (rateReviewAdapter != null) {
            rateReviewAdapter.notifyDataSetChanged();
        }

        setRatingData();
    }

    private void callApi(boolean isFirst) {
        if (isCallApi) {
            if (isFirst) {
                pageRate = 1;
                reviews.clear();
            } else {
                pageRate = pageRate + 1;
            }
            presenter.getRating(pageRate, expDetails.getId());
            isCallApi = false;
        }
    }

    private void setRatingData() {

        if (customTextViewRatingCount != null) {
            customTextViewRatingCount.setText("(" + tootalCount + ")");
        }

        if (textViewReadAll != null) {
            textViewReadAll.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.INVISIBLE : View.VISIBLE);
            if (viewHide != null)
                viewHide.setVisibility(((tootalCount - reviews.size()) <= 0) ? View.INVISIBLE : View.INVISIBLE);

            textViewReadAll.setText(getString(R.string.read_all) + " " + (tootalCount - reviews.size()) + " " + getString(R.string.reviews));
        }

    }

    @Override
    public void setIsLocalApp(boolean isLocalApp) {
        this.isLocalApp = isLocalApp;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setDataCal(ExpDetails data) {
        try {

            calendarDays.addAll(data.getCalendarDays());

            calendarViewAvailability.addDecorator(new DayEnableDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addDecorator(new DayDisableDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addDecorator(new DayOutRangeDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addAllDisableDays(calendarDays);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setCalData(List<CalendarDay> data) {
        try {

            calendarDays.addAll(data);

            calendarViewAvailability.addDecorator(new DayEnableDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addDecorator(new DayDisableDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addDecorator(new DayOutRangeDecorator(calendarDays, getActivity()));

            calendarViewAvailability.addAllDisableDays(calendarDays);

            if (calendarMaximumDate != null) {
                calendarViewAvailability.addDecorator(new DayMaximumDecorator(calendarMaximumDate, getActivity()));
            }

            //  shimmerViewContainer.startShimmerAnimation();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        if (expDetails != null) {
            presenter.openMapScreen(expDetails);
        }
    }

    @OnClick(R.id.linearLayoutLocal)
    public void onClickLinerLayout() {
        presenter.openLocalDetails(expDetails, isLocalApp);
    }


    private void animIcon(View view, DynamicAnimation.ViewProperty viewProperty, float fromPosition) {
        velocityTracker = VelocityTracker.obtain();
        SpringAnimation anim = new SpringAnimation(view, viewProperty, fromPosition);
        anim.getSpring().setStiffness(STIFFNESS_LOW);
        anim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        velocityTracker.computeCurrentVelocity(2000);
        velocity = velocityTracker.getYVelocity();
        anim.setStartVelocity(velocity);
        anim.start();
    }

    private void setSeeAllButton(boolean isShow) {
        if (textViewSeeAllDate != null) {
            textViewSeeAllDate.setVisibility(View.GONE);
            // textViewSeeAllDate.setVisibility((isShow && !isLocalApp) ? View.VISIBLE : View.GONE);
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
        if (action == EventBusAction.FAVREFRESE) {
            setFavData();
        }
    }


    private void setFavData() {

        ExpFavDataBase dataFavExp = databaseCacheDataSource.getDataFavExp(expDetails.getId(), expDetails.getIsFavorite());
        if (dataFavExp != null) {
            if (checkboxFav != null) {
                checkboxFav.setChecked(dataFavExp.getIsFav().equalsIgnoreCase("1"));
            }
        }
    }
}
