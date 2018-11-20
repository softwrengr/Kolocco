package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink on 8/2/18.
 */

import android.os.Handler;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesServiceNewAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesSportServiceSubNewAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.SelectServiceNewPresenter;
import com.kooloco.ui.visitor.dashboard.view.SelectServiceNewView;
import com.kooloco.util.StartSnapHelper;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class SelectServiceNewFragment extends BaseFragment<SelectServiceNewPresenter, SelectServiceNewView> implements SelectServiceNewView {

    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    @BindView(R.id.recyclerSportTypeSub)
    RecyclerView recyclerSportTypeSub;
    @BindView(R.id.linearLayoutSportType)
    LinearLayout linearLayoutSportType;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageHelp)
    ImageView imageHelp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.imageViewSelection)
    ImageView imageViewSelection;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.linearLayoutButtonAction)
    LinearLayout linearLayoutButtonAction;
    private VisitorBooking visitorBooking;
    private DashboardDetails dashboardDetails;

    int selectMainService = -1;
    Service serviceSelect = null;

    int selectSubService = -1;
    SubService subServiceSelect = null;

    @Override
    protected int createLayout() {
        return R.layout.fragment_select_service_new;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SelectServiceNewView createView() {
        return this;
    }

    @Override
    protected void bindData() {


        if (!visitorBooking.getLocalImage().isEmpty()) {
            Picasso.with(getActivity()).load(visitorBooking.getLocalImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }
        customTextViewName.setText(visitorBooking.getLocalName());

        setDataBottom();

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSportType.setLayoutManager(linearLayoutManager);


        SelectActivitiesServiceNewAdapter selectActivitiesServiceNewAdapter;
        selectActivitiesServiceNewAdapter = new SelectActivitiesServiceNewAdapter(getActivity(), dashboardDetails.getServices(), selectMainService, new SelectActivitiesServiceNewAdapter.CallBack() {
            @Override
            public void onClickPosition(int position, Service service) {
                linearLayoutManager.scrollToPositionWithOffset(position, 20);

                if (selectMainService != position) {
                    selectMainService = position;
                    serviceSelect = service;
                    selectSubService = -1;
                    subServiceSelect = null;
                }

                if (service.getIsExpandable().equalsIgnoreCase("1")) {
                    setDataBottom();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSubServiceData();
                        }

                    }, 500);
                } else {
                    setDataBottom();
                }

            }
        });
        recyclerSportType.setAdapter(selectActivitiesServiceNewAdapter);

        recyclerSportType.setOnFlingListener(null);

        StartSnapHelper snapHelper = new StartSnapHelper();

        snapHelper.attachToRecyclerView(recyclerSportType);

        if (selectMainService == -1) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(selectMainService, 0);
        }

        recyclerSportType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                linearLayoutSportType.setVisibility(View.GONE);
                setDataBottom();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(":::State", "" + newState);

                if (newState == 0) {

                }
            }
        });

    }

    private void setSubServiceData() {

        linearLayoutButtonAction.setVisibility(View.GONE);
        recyclerSportTypeSub.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSportTypeSub.setAdapter(new SelectActivitiesSportServiceSubNewAdapter(getActivity(), serviceSelect.getSubServices(), serviceSelect, selectSubService, new SelectActivitiesSportServiceSubNewAdapter.CallBack() {
            @Override
            public void onClickItem(int position, SubService subService, Service service) {
                if (selectSubService != position) {
                    selectSubService = position;
                    subServiceSelect = subService;
                }
                linearLayoutSportType.setVisibility(View.GONE);
                setDataBottom();
            }
        }));

        TransitionManager.beginDelayedTransition(root, new Fade());
        linearLayoutSportType.setVisibility(View.VISIBLE);
    }

    @Override
    public void setVisitorBooking(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void setDashBoardDetails(DashboardDetails dashboardDetails) {
        this.dashboardDetails = dashboardDetails;
    }

    @OnClick(R.id.buttonNext)
    public void onClick() {

        if (serviceSelect == null) {
            showMessage("Please select sport activities");
            return;
        } else {
            if (serviceSelect.getIsExpandable().equalsIgnoreCase("1")) {
                if (subServiceSelect == null) {
                    showMessage("Please select sport activities");
                    return;
                }
            }
        }


        if (serviceSelect.getIsExpandable().equalsIgnoreCase("1")) {
            visitorBooking.setSportId(subServiceSelect.getId());
            visitorBooking.setSportName(subServiceSelect.getName() + " " + serviceSelect.getName());
            visitorBooking.setSportImage(serviceSelect.getServiceImage());
            visitorBooking.setSportMainName(serviceSelect.getName());
            visitorBooking.setSportSubName(subServiceSelect.getName());
        } else {
            visitorBooking.setSportId(serviceSelect.getId());
            visitorBooking.setSportName(serviceSelect.getName());
            visitorBooking.setSportImage(serviceSelect.getServiceImage());
            visitorBooking.setSportMainName(serviceSelect.getName());
            visitorBooking.setSportSubName("");
        }

        presenter.openSelectActivity(visitorBooking);

    }


    private void setDataBottom() {
        linearLayoutButtonAction.setVisibility((serviceSelect != null) ? View.VISIBLE : View.GONE);
        if (serviceSelect != null) {
            if (serviceSelect.getIsExpandable().equalsIgnoreCase("1")) {
                if (subServiceSelect != null) {
                    customTextViewServiceTypeValue.setText(subServiceSelect.getName() + " " + serviceSelect.getName());
                } else {
                    linearLayoutButtonAction.setVisibility(View.GONE);
                }
            } else {
                customTextViewServiceTypeValue.setText(serviceSelect.getName());
            }
        }
    }

    @OnClick(R.id.imageViewBack)
    public void onClickBack() {
        goBack();
    }
}
