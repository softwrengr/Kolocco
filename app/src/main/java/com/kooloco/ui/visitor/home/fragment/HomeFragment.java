package com.kooloco.ui.visitor.home.fragment;
/**
 * Created by hlink44 on 14/9/17.
 */

import android.os.SystemClock;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.FilterRequest;
import com.kooloco.ui.alllocal.fragment.AllLocalMapNewFragment;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.home.adapter.HomeAdapter;
import com.kooloco.ui.visitor.home.presenter.HomePresenter;
import com.kooloco.ui.visitor.home.view.HomeView;
import com.kooloco.util.AnimationFactory;
import com.kooloco.util.FlipAnimation;
import com.kooloco.util.LocationManager;
import com.kooloco.util.TimeConvertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {
    @BindView(R.id.recyclerViewHome)
    RecyclerView recyclerViewHome;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.buttonResetFilter)
    AppCompatButton buttonResetFilter;
    Unbinder unbinder;
    @BindView(R.id.imageViewMap)
    ImageView imageViewMap;
    @BindView(R.id.linearLayoutMain)
    LinearLayout linearLayoutMain;
    @BindView(R.id.fragmentMap)
    FrameLayout fragmentMap;
    @BindView(R.id.imageVieList)
    ImageView imageVieList;
    @BindView(R.id.viewFliper)
    ViewFlipper viewFliper;

    private List<DashboardDetails> data;

    HomeAdapter homeAdapter;

    FilterRequest filterRequest = new FilterRequest();

    long mLastClickTime = 0;

    //Pagination

    boolean isApplyFilter = false;

    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;

    @BindView(R.id.customTextViewFilter)
    AppCompatTextView customTextViewFilter;


    boolean isOpenFilter = true;

    AllLocalMapNewFragment allLocalMapFragment;
    FlipAnimation flipAnimation;

    @Override
    protected int createLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HomeView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        TransitionManager.beginDelayedTransition(root, new Slide());


        data = new ArrayList<>();
        allLocalMapFragment = new AllLocalMapNewFragment();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentMap, allLocalMapFragment, "DETAILFRAGMENT_TAG")
                .commit();

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewHome.setLayoutManager(mLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(), data, getChildFragmentManager(), new HomeAdapter.CallBack() {
            @Override
            public void onClickItem(int position, DashboardDetails dashboardDetails) {
                presenter.openDashBoard(dashboardDetails);
            }

            @Override
            public void onClickImage(int position, DashboardDetails dashboardDetails) {
                imageOpenZoom(dashboardDetails.getImageUrl());
            }
        });

        recyclerViewHome.setAdapter(homeAdapter);

        //It is used to get local data in database
        presenter.getDataLocal();

        recyclerViewHome.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + 3 + pastVisiblesItems) >= totalItemCount) {
                   /* if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        if (isApplyFilter) {
                            presenter.getDataFilter(page, false, filterRequest);
                        } else {
                            presenter.getData(page, false);
                        }
                    }*/
                }

            }
        });

        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;

                if (presenter != null) {
                    page = 1;
                    if (isApplyFilter) {
                        presenter.getDataFilter(page, false, filterRequest);

                    } else {
                        presenter.getData(page, false);
                    }
                }
            }

            @Override
            public void onFail(Status status) {
                Log.e("Err", status.toString());
            }
        });
        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (presenter != null) {
                page = 1;
                if (isApplyFilter) {
                    presenter.getDataFilter(page, false, filterRequest);

                } else {
                    presenter.getData(page, false);
                }
            }
        });


        buttonResetFilter.setOnClickListener(view -> {
            isApplyFilter = false;
            filterRequest = new FilterRequest();

            customTextViewFilter.setText(getActivity().getResources().getString(R.string.sport_date_time_location_activity_language));
            presenter.getData(page, true);
        });
    }

    @Override
    public void setData(List<DashboardDetails> dashData, int page, boolean isFromFilter) {

        if (page == 1) {
            data.clear();
        }

        data.addAll(dashData);

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        if (getActivity() != null) {
            if (homeAdapter != null) {
                homeAdapter.notifyDataSetChanged();
            }
        }


        if (isFromFilter) {
            if (linearLayoutNoData != null) {
                linearLayoutNoData.setVisibility((data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
            }
        } else {
            if (linearLayoutNoData != null) {
                if (linearLayoutNoData.getVisibility() == View.VISIBLE) {
                    linearLayoutNoData.setVisibility(View.INVISIBLE);
                }
            }
        }

        isLoading = !dashData.isEmpty();

        if (isLoading) {
            page = page + 1;
            isLoading = false;
            if (isApplyFilter) {
                if (presenter != null) {
                    presenter.getDataFilter(page, false, filterRequest);
                }


            } else {
                if (presenter != null) {
                    presenter.getData(page, false);
                }

            }
        }
    }

    @OnClick({R.id.imageViewNotification, R.id.imageViewFilter, R.id.imageViewMap, R.id.imageVieList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewNotification:
                presenter.openNotification();
                break;
            case R.id.imageViewFilter:
                //  openFilter();
                break;
            case R.id.imageViewMap:
                //((MainActivity) getActivity()).openMapFragment();
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    break;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (fragmentMap.getVisibility() == View.GONE) {

                    imageViewMap.setVisibility(View.GONE);
                    imageVieList.setVisibility(View.VISIBLE);

/*
                    fragmentMap.setVisibility(View.VISIBLE);
                    linearLayoutMain.setVisibility(View.GONE);
*/


                    allLocalMapFragment.setDataDashboard(data);


                    double let = BaseFragment.latitude;
                    double lng = BaseFragment.longitude;

                    if (isApplyFilter) {
                        if (filterRequest != null) {
                            if (!filterRequest.getAddres().isEmpty()) {
                                try {
                                    let = Double.parseDouble(filterRequest.getLatitude());
                                    lng = Double.parseDouble(filterRequest.getLongitude());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    allLocalMapFragment.setIsCameraFocus(isApplyFilter, let, lng);

                    AnimationFactory.flipTransition(viewFliper, AnimationFactory.FlipDirection.LEFT_RIGHT);

/*
                    viewFliper.showNext();
*/
                }
                break;
            case R.id.imageVieList:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    break;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (linearLayoutMain.getVisibility() == View.GONE) {
                    imageViewMap.setVisibility(View.VISIBLE);
                    imageVieList.setVisibility(View.GONE);

/*
                    fragmentMap.setVisibility(View.GONE);
                    linearLayoutMain.setVisibility(View.VISIBLE);
*/
                    viewFliper.showPrevious();
                }
                break;
        }
    }

    @OnClick(R.id.imageViewSearch)
    public void onViewClicked() {
        presenter.openBlogList();
    }

    @OnClick(R.id.customTextViewFilter)
    public void onClickFilter() {

        filterData((isApply, filterRequest) -> {
            isApplyFilter = isApply;
            this.filterRequest = filterRequest;

            page = 1;
            if (isApplyFilter) {
                presenter.getDataFilter(page, true, filterRequest);

                String textGenrate = "";

                if (!filterRequest.getAddres().isEmpty()) {
                    textGenrate = textGenrate + "" + filterRequest.getAddres();
                }/* else {
                    textGenrate = textGenrate + " - location";
                }*/

                if (!filterRequest.getSportName().isEmpty()) {

                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + filterRequest.getSportName();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getSportName();
                    }

                }/* else {
                    textGenrate = textGenrate + "Sport";
                }*/

                if (!filterRequest.getDate().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + TimeConvertUtils.dateTimeConvertLocalToLocal(filterRequest.getDate(), "yyyy-MM-dd", "dd MMMM, yyyy");

                    } else {
                        textGenrate = textGenrate + " - " + TimeConvertUtils.dateTimeConvertLocalToLocal(filterRequest.getDate(), "yyyy-MM-dd", "dd MMMM, yyyy");
                    }
                }/* else {
                    textGenrate = textGenrate + " - date";
                }*/

                if (!filterRequest.getStartTime().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + TimeConvertUtils.dateTimeConvertLocalToLocal(filterRequest.getStartTime(), "HH:mm:ss", "hh:mm a");

                    } else {
                        textGenrate = textGenrate + " - " + TimeConvertUtils.dateTimeConvertLocalToLocal(filterRequest.getStartTime(), "HH:mm:ss", "hh:mm a");

                    }
                }/* else {
                    textGenrate = textGenrate + " - time";
                }*/

                if (!filterRequest.getActivityName().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getActivityName();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getActivityName();

                    }
                } /*else {
                    textGenrate = textGenrate + " - activity";
                }*/

                if (!filterRequest.getLanguageName().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getLanguageName();

                    } else {

                        textGenrate = textGenrate + " - " + filterRequest.getLanguageName();
                    }
                } /*else {
                    textGenrate = textGenrate + " - language";
                }*/

                //Price

                if (!(filterRequest.getPriceMin().equalsIgnoreCase("0") && filterRequest.getPriceMax().equalsIgnoreCase("500"))) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "$" + filterRequest.getPriceMin() + " to " + "$" + filterRequest.getPriceMax() + "";

                    } else {
                        textGenrate = textGenrate + " - " + "$" + filterRequest.getPriceMin() + " to " + "$" + filterRequest.getPriceMax() + "";
                    }
                } /*else {
                    textGenrate = textGenrate + " - language";
                }*/

                //Rating

                if (!(filterRequest.getRateMin().equalsIgnoreCase("0") && filterRequest.getRateMax().equalsIgnoreCase("5"))) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getRateMin() + " to " + "" + filterRequest.getRateMax() + "";

                    } else {

                        textGenrate = textGenrate + " - " + "" + filterRequest.getRateMin() + " to " + "" + filterRequest.getRateMax() + "";
                    }
                } /*else {
                    textGenrate = textGenrate + " - language";
                }*/


                customTextViewFilter.setText(textGenrate);

            } else {
                customTextViewFilter.setText(getActivity().getResources().getString(R.string.sport_date_time_location_activity_language));
                presenter.getData(page, true);
            }

        }, !isApplyFilter, false);
    }

    @Override
    public void onShow() {
        super.onShow();
/*
        if (isSelectMap) {
            new android.os.Handler().postDelayed(() -> {
                ((MainActivity) getActivity()).openMapFragment();
            }, 01);
        }
*/
    }

}
