package com.kooloco.ui.visitor.organizationDetails.fragment;
/**
 * Created by hlink44 on 19/9/17.
 */

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.LocalImage;
import com.kooloco.model.OrgImage;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.DialogImageSlider;
import com.kooloco.ui.visitor.organizationDetails.adapter.LocalNewAdapter;
import com.kooloco.ui.visitor.organizationDetails.adapter.OrgImageSlider;
import com.kooloco.ui.visitor.organizationDetails.presenter.OrganizationDetailsPresenter;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationDetailsView;
import com.kooloco.util.LocationManager;
import com.kooloco.util.StaticMap;
import com.kooloco.util.picaso.CircleTransform;
import com.nex3z.flowlayout.FlowLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class OrganizationDetailsFragment extends BaseFragment<OrganizationDetailsPresenter, OrganizationDetailsView> implements OrganizationDetailsView {


    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageViewRightMenu)
    ImageView imageViewRightMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewIntroduction)
    AppCompatTextView customTextViewIntroduction;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.customTextViewActivityTypes)
    AppCompatTextView customTextViewActivityTypes;
    @BindView(R.id.recyclerViewLocal)
    RecyclerView recyclerViewLocal;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.flowLayoutLocal)
    FlowLayout flowLayoutLocal;
    @BindView(R.id.linearLayoutExitOrg)
    LinearLayout linearLayoutExitOrg;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.linearPrivew)
    LinearLayout linearPrivew;
    @BindView(R.id.textViewNoLocalFound)
    AppCompatTextView textViewNoLocalFound;
    @BindView(R.id.linearLayoutDeleteOrg)
    LinearLayout linearLayoutDeleteOrg;
    @BindView(R.id.customTextViewPaymentRuleText)
    AppCompatTextView customTextViewPaymentRuleText;
    @BindView(R.id.linearLayoutPaymentRule)
    LinearLayout linearLayoutPaymentRule;
    private OrganizationVisitor organizationVisitor;
    LinearLayoutManager layoutManager;
    List<Service> servicesSelect;
    Dialog dialog;

    boolean isLocalProfile = false;

    List<OrgLocal> orgLocals, orgLocalsTemp;
    LocalNewAdapter localNewAdapter;
    private boolean isPreview = false;

    OrganizationDetails organizationDetails;

    @Inject
    Session session;
    private boolean isDeleteOrg = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_organization_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OrganizationDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        toolbarTitle.setText(isPreview ? getActivity().getResources().getString(R.string.organization_details_preivew) : getActivity().getResources().getString(R.string.organization_details));


        linearLayoutPaymentRule.setVisibility(View.GONE);

        linearLayoutDeleteOrg.setVisibility((isPreview && isDeleteOrg) ? View.VISIBLE : View.GONE);

        linearLayoutExitOrg.setVisibility(isLocalProfile ? View.VISIBLE : View.GONE);

        if (isLocalProfile) {
            linearLayoutPaymentRule.setVisibility(View.VISIBLE);
        }

        linearPrivew.setVisibility(isPreview ? View.VISIBLE : View.GONE);

        if (isDeleteOrg) {
            linearPrivew.setVisibility(View.GONE);
            linearLayoutExitOrg.setVisibility(View.GONE);
        }

        servicesSelect = new ArrayList<>();

        orgLocals = new ArrayList<>();
        orgLocalsTemp = new ArrayList<>();

        localNewAdapter = new LocalNewAdapter(getActivity(), orgLocals, new LocalNewAdapter.CallBack() {
            @Override
            public void onClickRow(int position, OrgLocal orgLocal) {
                if (!isLocalProfile) {
                    if (session.getUser().getRole().equalsIgnoreCase("L")) {
                        isPreview = true;
                    }

                    ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
                        @Override
                        public void onLocationAvailable(LatLng latLng) {
                            BaseFragment.latitude = latLng.latitude;
                            BaseFragment.longitude = latLng.longitude;

                            presenter.openDashboardDetails(orgLocal, isPreview);

                        }

                        @Override
                        public void onFail(Status status) {
                            Log.e("Err", status.toString());
                            presenter.openDashboardDetails(orgLocal, isPreview);
                        }
                    });

                }
            }
        }, isPreview);

        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewLocal.setAdapter(localNewAdapter);

        presenter.getData(organizationVisitor, isPreview);
    }

    @OnClick({R.id.imageViewBack, R.id.imageViewRightMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                presenter.back();
                break;
            case R.id.imageViewRightMenu:
                break;
        }
    }

    @Override
    public void setData(OrganizationDetails data) {
        //Set profile data
        organizationDetails = data;
        Picasso.with(getActivity()).load(data.getImageUrl()).placeholder(R.drawable.user_round).transform(new CircleTransform()).into(imageViewProfile);

        imageViewProfile.setOnClickListener(view -> imageOpenZoom(data.getImageUrl()));

        customTextViewName.setText(data.getOrgName());
        customTextViewIntroduction.setText(data.getOrgDescripation());
        customTextViewActivityTypes.setText(presenter.getActivity(data.getActivities()));
        customTextViewLocation.setText(data.getOrgLocation());

        String staticMapUrl = StaticMap.getUrl(getActivity(), data.getOrgLatitude(), data.getOrgLongitude(), URLFactory.LOCAL);
        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);

        //Set local data
        orgLocals.addAll(data.getOrgLocals());
        orgLocalsTemp.addAll(data.getOrgLocals());

        if (localNewAdapter != null) {
            localNewAdapter.notifyDataSetChanged();
        }

        if (textViewNoLocalFound != null) {
            textViewNoLocalFound.setVisibility((orgLocals.size() == 0) ? View.VISIBLE : View.GONE);
        }

        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setStrokeWidth(2);
        pageIndicatorView.setCount(data.getOrgImage().size());

        OrgImageSlider dashboardSlider = new OrgImageSlider(getActivity(), data.getOrgImage(), new OrgImageSlider.CallBack() {
            @Override
            public void onClick(int position) {
                List<LocalImage> localImages = new ArrayList<>();

                for (OrgImage orgImage : data.getOrgImage()) {
                    LocalImage localImage = new LocalImage();

                    localImage.setLocalImage(orgImage.getImageUrl());
                    localImage.setId(orgImage.getId());
                    localImage.setLocalImageUrl(orgImage.getImageUrl());
                    localImage.setDescription("");
                    localImage.setTitle(data.getOrgName());

                    localImages.add(localImage);
                }

                showDialog(localImages);
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
                pageIndicatorView.setSelection(firstVisibleItemPosition);
            }
        });


        localSport(data.getServicesNew());

        if (isLocalProfile) {
            presenter.isCheckPaymentRules();
        }
    }

    @Override
    public void setDataOrgVisitor(OrganizationVisitor organizationVisitor) {
        this.organizationVisitor = organizationVisitor;
    }

    @Override
    public void setIsLocal(boolean isTrue) {
        isLocalProfile = isTrue;
    }

    @Override
    public void setNewSearch(List<OrgLocal> orgLocalsTempDemo) {
        orgLocals.clear();
        orgLocals.addAll(orgLocalsTempDemo);

        if (localNewAdapter != null) {
            localNewAdapter.notifyDataSetChanged();
        }

        if (textViewNoLocalFound != null) {
            textViewNoLocalFound.setVisibility((orgLocals.size() == 0) ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }


    @Override
    public void setIsDeleteOrg(boolean isDeleteOrg) {
        this.isDeleteOrg = isDeleteOrg;
    }

    @Override
    public void goBackData() {
        getActivity().finish();
    }

    @Override
    public void setTextPaymentRules(String isPaymentText) {
        if (customTextViewPaymentRuleText != null) {
            customTextViewPaymentRuleText.setText(isPaymentText.isEmpty() ? getActivity().getResources().getString(R.string.org_not_assign_payment_rul) : isPaymentText);
        }
    }

    @OnClick(R.id.imageViewRightMenu)
    public void onViewClicked() {
        presenter.openOrganzationReport();
    }

    private void setLocalSelect() {
        flowLayoutLocal.removeAllViews();

        for (Service service : servicesSelect) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_time, null);

            AppCompatTextView appCompatTextView = view.findViewById(R.id.customTextViewTag);

            appCompatTextView.setText(service.getName());
            appCompatTextView.setTag(service);
            appCompatTextView.setSelected(false);

            ((AppCompatTextView) view.findViewById(R.id.customTextViewTag)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    Service service1 = (Service) view.getTag();
                    servicesSelect.remove(service1);
                    setLocalSelect();
*/

                }
            });

            flowLayoutLocal.addView(view);
        }

    }

    private void localSport(List<Service> services) {

        flowLayout.removeAllViews();

        for (Service service : services) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_time, null);

            AppCompatTextView appCompatTextView = view.findViewById(R.id.customTextViewTag);

            appCompatTextView.setText(service.getName());
            appCompatTextView.setTag(service);
            appCompatTextView.setSelected(false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appCompatTextView.setSelected(!appCompatTextView.isSelected());
                    Service service1 = (Service) appCompatTextView.getTag();
                    if (appCompatTextView.isSelected()) {
                        servicesSelect.add(service1);
                    } else {
                        servicesSelect.remove(service1);
                    }
                    setLocalSelect();
                    presenter.applyFilter(orgLocalsTemp, servicesSelect);
                }
            });

            flowLayout.addView(view);
        }
    }

    @OnClick(R.id.buttonCancel)
    public void onClick() {
        presenter.callExitOrg(organizationVisitor.getAffilatedId());
    }

    @OnClick(R.id.buttonConfirm)
    public void onClickConfirm() {
        goBack();
    }

    public void showDialog(List<LocalImage> localImages) {

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


        textViewTotal.setText("/" + localImages.size());

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setContentView(view);

        dialog.show();


    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(organizationDetails);
    }

    @OnClick(R.id.buttonDeleteOrg)
    public void onClickDeleteOrg() {
        if (organizationDetails != null) {
            showDialogDeleteWithAnimation(getString(R.string.delete_org_message), isSuccess -> {
                if (isSuccess) {
                    presenter.deleteOrganization(organizationDetails.getId());
                }
            });
        }
    }
}
