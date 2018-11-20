package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 16/9/17.
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CertificateInfo;
import com.kooloco.model.CheckPaymentRules;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.LocalImage;
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.model.SubService;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.DashboardCertificatesAndAchivments;
import com.kooloco.ui.visitor.dashboard.adapter.DashboardSlider;
import com.kooloco.ui.visitor.dashboard.adapter.DialogImageSlider;
import com.kooloco.ui.visitor.dashboard.adapter.RateReviewAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.ViewPagerSlider;
import com.kooloco.ui.visitor.dashboard.presenter.DashboardPresenter;
import com.kooloco.ui.visitor.dashboard.view.DashboardView;
import com.kooloco.ui.visitor.home.adapter.HomeExperienceAdapter;
import com.kooloco.util.AppBarStateChangeListener;
import com.kooloco.util.StartSnapHelper;
import com.kooloco.util.StaticMap;
import com.kooloco.util.picaso.CircleTransform;
import com.like.LikeButton;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.relex.circleindicator.CircleIndicator;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

public class DashboardFragment extends BaseFragment<DashboardPresenter, DashboardView> implements DashboardView {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.recyclerViewImageSlide)
    RecyclerView recyclerViewImageSlide;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.imageSlider)
    FrameLayout imageSlider;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.checkboxFav)
    AppCompatCheckBox checkboxFav;
    @BindView(R.id.star_button)
    LikeButton starButton;
    @BindView(R.id.imageViewShare)
    ImageView imageViewShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.customTextViewNTMY)
    AppCompatTextView customTextViewNTMY;
    @BindView(R.id.customTextViewLanguage)
    AppCompatTextView customTextViewLanguage;
    @BindView(R.id.customTextViewDistance)
    AppCompatTextView customTextViewDistance;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.recyclerViewExp)
    RecyclerView recyclerViewExp;
    @BindView(R.id.linearLayoutCertification)
    LinearLayout linearLayoutCertification;
    @BindView(R.id.recyclerAchievements)
    RecyclerView recyclerAchievements;
    @BindView(R.id.linearLayoutAchievements)
    LinearLayout linearLayoutAchievements;
    @BindView(R.id.customTextViewOrgName)
    AppCompatTextView customTextViewOrgName;
    @BindView(R.id.customTextViewOrgDescWithLocation)
    AppCompatTextView customTextViewOrgDescWithLocation;
    @BindView(R.id.imageViewOrgImage)
    ImageView imageViewOrgImage;
    @BindView(R.id.linearLayoutOrganization)
    LinearLayout linearLayoutOrganization;
    @BindView(R.id.linearLayoutOrg)
    LinearLayout linearLayoutOrg;
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
    @BindView(R.id.textViewCertificates)
    AppCompatTextView textViewCertificates;
    @BindView(R.id.imageSlider1)
    FrameLayout imageSlider1;
    private DashboardDetails data;
    LinearLayoutManager layoutManager;
    private DashboardDetails dashboardDetails;

    private VelocityTracker velocityTracker;
    private float velocity;
    private boolean isPreview = false;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerViewContainer;


    int pageRate = 1;
    int tootalCount = 0;
    boolean isCallApi = true;

    RateReviewAdapter rateReviewAdapter;

    List<Review> reviews;

    AppBarStateChangeListener.State stateCustom;

    private boolean isOrgScreen = false;
    Dialog dialog;
    HomeExperienceAdapter homeExperienceAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected DashboardView createView() {
        return this;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void bindData() {
        //It is used to check local is assign organization
        if (dashboardDetails != null) {
            if (dashboardDetails.getId() != null) {
                presenter.callCheckLocalOrg(dashboardDetails.getId());
            }
        }


        shimmerViewContainer.setVisibility(View.VISIBLE);
        shimmerViewContainer.setDuration(1000);
        shimmerViewContainer.setAngle(ShimmerFrameLayout.MaskAngle.CW_0);
        shimmerViewContainer.setDropoff(0.15f);

        shimmerViewContainer.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);

        shimmerViewContainer.startShimmerAnimation();


        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                stateCustom = state;

                if (state == State.COLLAPSED) {

                    imageViewBack.setColorFilter(getActivity().getResources().getColor(R.color.black));
                    imageViewShare.setColorFilter(getActivity().getResources().getColor(R.color.black));

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

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //It is used to set review data
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        rateReviewAdapter = new RateReviewAdapter(getActivity(), reviews);

        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerReviews.setAdapter(rateReviewAdapter);
        recyclerReviews.setNestedScrollingEnabled(false);

        presenter.getData(dashboardDetails.getId());

        checkboxFav.setVisibility(View.INVISIBLE);

        buttonBookNow.setVisibility(isPreview ? View.INVISIBLE : View.VISIBLE);

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
        });


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

    @Override
    public void setData(final DashboardDetails data) {
        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.stopShimmerAnimation();

        this.dashboardDetails = data;
        this.data = data;
        //Set image slide data

        // getAddressLetLong(Double.parseDouble(data.getLatitude()), Double.parseDouble(data.getLongitude()), "");

        if (getActivity() == null) {
//            goBack();
            return;
        }

        customTextViewLocalName.setText(data.getLocalName());

        viewPager.setAdapter(new ViewPagerSlider(getChildFragmentManager(), data.getLocalImages()));
        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setStrokeWidth(2);

        DashboardSlider dashboardSlider = new DashboardSlider(getActivity(), data.getLocalImages(), new DashboardSlider.CallBack() {
            @Override
            public void onClick(int position) {
                showDialog(data.getLocalImages(), position);
            }
        });

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


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
                viewPager.setCurrentItem(firstVisibleItemPosition);
            }
        });

        //Set profile data
        Picasso.with(getActivity()).load(data.getImageUrl()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);

        imageViewProfile.setOnClickListener(view -> imageOpenZoom(data.getImageUrl()));
        customTextViewRatingValue.setText(data.getLocalRating());
        customTextViewNTMY.setText(data.getLocalNTMY());

        String staticMapUrl = StaticMap.getUrl(getActivity(), data.getLatitude(), data.getLongitude(), URLFactory.LOCAL);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);


        // Set language data and distance
        customTextViewLanguage.setText(data.getLocalLanguages());
        customTextViewDistance.setText(data.getLocalDistance());

        //Set location data
        customTextViewLocation.setText(data.getLocalLocation());


        //Set achievements data

        linearLayoutAchievements.setVisibility((data.getAchivmentsInfo() == null || data.getAchivmentsInfo().size() == 0) ? View.GONE : View.VISIBLE);

        recyclerAchievements.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerAchievements.setAdapter(new DashboardCertificatesAndAchivments(getActivity(), data.getAchivmentsInfo(), new DashboardCertificatesAndAchivments.CallBack() {
            @Override
            public void onClickItem(int position, CertificateInfo certificateInfo) {
                openInfoCertficates(certificateInfo);
            }
        }));

        if (data.getIsAffilated().equalsIgnoreCase("1")) {
            linearLayoutOrg.setVisibility(View.VISIBLE);
            customTextViewOrgName.setText(data.getOrganizationVisitor().getOrganisationName());
            customTextViewOrgDescWithLocation.setText(data.getOrganizationVisitor().getOrganisationDescription() + "\n" + data.getOrganizationVisitor().getOrganisationAddress());
            Picasso.with(getActivity()).load(data.getOrganizationVisitor().getOrganisationLogo()).placeholder(R.drawable.user_round).transform(new CircleTransform()).into(imageViewOrgImage);
        } else {
            linearLayoutOrg.setVisibility(View.GONE);
        }

        //Set certification

        linearLayoutCertification.setVisibility(data.getCertificateInfos().size() == 0 ? View.GONE : View.VISIBLE);

        String certificatesInfoText = "";

        for (CertificateInfo certificateInfo : data.getCertificateInfos()) {
            if (certificatesInfoText.isEmpty()) {
                certificatesInfoText = certificateInfo.getName();
            } else {
                certificatesInfoText = certificatesInfoText + ", " + certificateInfo.getName();
            }
        }
        textViewCertificates.setText(getString(R.string.certification_details_certificates) + " " + certificatesInfoText);
        //Set Review data

        ratingBar.setRating(Float.parseFloat(data.getLocalRating()));

        isCallApi = true;
        callApi(true);


        //   Recommended exp section start
        recyclerViewExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        homeExperienceAdapter = new HomeExperienceAdapter(getActivity(), data.getExpereinceNews(), new HomeExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew, isPreview);
            }

            @Override
            public void onClickFav(ExpereinceNew expereinceNew, int pos) {
                setFavExp(expereinceNew.getId(), new CallBackMainActivity() {
                    @Override
                    public void onSuccess(boolean status) {
                        if (status) {
                            //notifyAdapter();
                        }
                    }
                });
            }
        });

        recyclerViewExp.setAdapter(homeExperienceAdapter);

        recyclerViewExp.setOnFlingListener(null);
        StartSnapHelper snapHelperExp = new StartSnapHelper();
        snapHelperExp.attachToRecyclerView(recyclerViewExp);
        //   Recommended exp section end

    }


    private void callApi(boolean isFirst) {
        if (isCallApi) {
            if (isFirst) {
                pageRate = 1;
                reviews.clear();
            } else {
                pageRate = pageRate + 1;
            }
            presenter.getRating(pageRate, dashboardDetails.getId());
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

    @Override
    public void setIsOrg(boolean isOrgScreen) {
        this.isOrgScreen = isOrgScreen;
    }

    @Override
    public void setOrgData(Response<CheckPaymentRules> checkPaymentRulesResponse) {

        if (checkPaymentRulesResponse.getData().getIsAssign().equalsIgnoreCase("1")) {
            if (linearLayoutOrg != null) {
                if (linearLayoutOrg.getVisibility() == View.VISIBLE) {
                    linearLayoutOrg.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (linearLayoutOrg != null) {
                linearLayoutOrg.setVisibility(View.GONE);
            }
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
    public void setDataNew(DashboardDetails dashboardDetails) {
        this.dashboardDetails = dashboardDetails;
    }

    @Override
    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @OnClick({R.id.textViewReadAll, R.id.buttonBookNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewReadAll:
                callApi(false);
                break;
            case R.id.buttonBookNow:
                break;
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

    @OnClick(R.id.linearLayoutOrganization)
    public void onViewClicked() {
        if (!isOrgScreen) {
            presenter.openOrganizationDetails(dashboardDetails.getOrganizationVisitor());
        } else {
            goBack();
        }
    }


    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        if (data != null) {
            presenter.openMapScreen(data);
        }
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        presenter.onBack();
    }

    @OnClick(R.id.buttonBookNow)
    public void onViewClickedBookNow() {

        if (data != null) {
            presenter.openExpSelectScreen(data);
        }
    }


    private String getSubService(List<SubService> subServices) {
        String s = "";
        for (SubService subService : subServices) {
            if (s.isEmpty()) {
                s = s + subService.getName();
            } else {
                s = s + ", " + subService.getName();

            }
        }
        return s;
    }

    private void getAddressLetLong(double lat, double longitude, String name) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(lat, longitude, 1);
            if (addresses != null) {
                if (addresses.size() > 0) {

                    String address = "" + addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String address2 = "" + addresses.get(0).getAddressLine(1);
                    String city = "" + addresses.get(0).getLocality();
                    String state = "" + addresses.get(0).getAdminArea();
                    String country = "" + addresses.get(0).getCountryName();
                    String postalCode = "" + addresses.get(0).getPostalCode();
                    String knownName = "" + addresses.get(0); //
                    String setAddressAutoCompletTextview = "";

                    if (!address.trim().isEmpty() && !address.equals("null")) {
                        setAddressAutoCompletTextview = setAddressAutoCompletTextview + address;
                    }
                    if (!city.trim().isEmpty() && !city.equals("null")) {
                        setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + city;
                    }
                    if (!state.trim().isEmpty() && !state.equals("null")) {
                        setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + state;
                    }
                    if (!country.trim().isEmpty() && !country.equals("null")) {
                        setAddressAutoCompletTextview = setAddressAutoCompletTextview + ", " + country;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            if (homeExperienceAdapter != null) {
                homeExperienceAdapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.imageViewShare)
    public void onClick() {
        if (dashboardDetails != null) {
            shareExperienceData(dashboardDetails.getId(), dashboardDetails.getFirstname()+" "+dashboardDetails.getLastname(), dashboardDetails.getLocalNTMY(), dashboardDetails.getLocalImages().get(0).getLocalImage(),false);
        }

    }
}
