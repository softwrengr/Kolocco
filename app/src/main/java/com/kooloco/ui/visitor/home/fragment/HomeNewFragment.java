package com.kooloco.ui.visitor.home.fragment;
/**
 * Created by hlink on 19/4/18.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.FilterRequest;
import com.kooloco.model.HomeNewResponse;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.home.adapter.HomeExperienceAdapter;
import com.kooloco.ui.visitor.home.adapter.HomeLocalAdapter;
import com.kooloco.ui.visitor.home.adapter.HomeLocationAdapter;
import com.kooloco.ui.visitor.home.adapter.HomeSportAdapter;
import com.kooloco.ui.visitor.home.presenter.HomeNewPresenter;
import com.kooloco.ui.visitor.home.view.HomeNewView;
import com.kooloco.util.LocationManager;
import com.kooloco.util.StartSnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class HomeNewFragment extends BaseFragment<HomeNewPresenter, HomeNewView> implements HomeNewView {

    @BindView(R.id.customTextViewFilter)
    AppCompatTextView customTextViewFilter;
    @BindView(R.id.recyclerRecommendedExp)
    RecyclerView recyclerRecommendedExp;
    @BindView(R.id.recyclerSport)
    RecyclerView recyclerSport;
    @BindView(R.id.recyclerNearLocalExp)
    RecyclerView recyclerNearLocalExp;
    @BindView(R.id.recyclerTrendyExp)
    RecyclerView recyclerTrendyExp;
    @BindView(R.id.recyclerTopSportLocation)
    RecyclerView recyclerTopSportLocation;
    @BindView(R.id.recyclerNewExp)
    RecyclerView recyclerNewExp;
    @BindView(R.id.recyclerStaffPicks)
    RecyclerView recyclerStaffPicks;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    boolean isApplyFilter;
    @BindView(R.id.linearLayoutRecommededExp)
    LinearLayout linearLayoutRecommededExp;
    @BindView(R.id.linearLayoutSport)
    LinearLayout linearLayoutSport;
    @BindView(R.id.linearLayoutNearLocal)
    LinearLayout linearLayoutNearLocal;
    @BindView(R.id.linearLayoutTrendyExp)
    LinearLayout linearLayoutTrendyExp;
    @BindView(R.id.linearLayoutSpotLocation)
    LinearLayout linearLayoutSpotLocation;
    @BindView(R.id.linearLayoutNewExp)
    LinearLayout linearLayoutNewExp;
    @BindView(R.id.linearLayoutStaffExp)
    LinearLayout linearLayoutStaffExp;

    HomeExperienceAdapter homeExperienceAdapterRecommendExp;
    HomeExperienceAdapter homeExperienceAdapterTrendyExp;
    HomeExperienceAdapter homeExperienceAdapterNewExp;
    HomeExperienceAdapter homeExperienceAdapterStaffPick;

    @Override
    protected int createLayout() {
        return R.layout.home_new_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HomeNewView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        presenter.getDataLocal();


        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;

                if (presenter != null) {
                    presenter.getData();
                }
            }

            @Override
            public void onFail(Status status) {
                Log.e("Err", status.toString());

                BaseFragment.latitude = 0.0;
                BaseFragment.longitude = 0.0;

                if (presenter != null) {
                    presenter.getData();
                }

            }
        });

        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {

            ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
                @Override
                public void onLocationAvailable(LatLng latLng) {
                    BaseFragment.latitude = latLng.latitude;
                    BaseFragment.longitude = latLng.longitude;

                    if (presenter != null) {
                        presenter.getData();
                    }
                }

                @Override
                public void onFail(Status status) {
                    if (presenter != null) {
                        presenter.getData();
                    }
                }
            });

        });


    }


    @Override
    public void setData(HomeNewResponse data) {

    /*    if(isResumed()){
            return;
        }*/

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        } else {
            return;
        }

        linearLayoutRecommededExp.setVisibility(data.getRecommended().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutSport.setVisibility(data.getSports().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutNearLocal.setVisibility(data.getNearestLocals().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutTrendyExp.setVisibility(data.getTrendyExperience().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutSpotLocation.setVisibility(data.getTopSportDestination().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutNewExp.setVisibility(data.getNewExperience().isEmpty() ? View.GONE : View.VISIBLE);
        linearLayoutStaffExp.setVisibility(data.getStaffPicks().isEmpty() ? View.GONE : View.VISIBLE);

        //   Recommended exp section start


        homeExperienceAdapterRecommendExp = new HomeExperienceAdapter(getActivity(), data.getRecommended(), new HomeExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);

            }

            @Override
            public void onClickFav(ExpereinceNew expereinceNew, int pos) {

                setFavExp(expereinceNew.getId(), new CallBackMainActivity() {
                    @Override
                    public void onSuccess(boolean status) {
                        // notifyAdapter();
                    }
                });

            }
        });

        recyclerRecommendedExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerRecommendedExp.setAdapter(homeExperienceAdapterRecommendExp);
        recyclerRecommendedExp.setOnFlingListener(null);
        StartSnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(recyclerRecommendedExp);
        //   Recommended exp section end


        //   Sport section start
        recyclerSport.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerSport.setAdapter(new HomeSportAdapter(getActivity(), data.getSports(), (service, pos) -> {
            Log.e(":::::", "Click Sport");
            openSecondTab();

            FilterRequest filterRequest;

            if (BaseFragment.filterRequestData == null) {
                filterRequest = new FilterRequest();

                filterRequest.setPriceMin("0");
                filterRequest.setPriceMax("5000");
                filterRequest.setRateMin("0");
                filterRequest.setRateMax("5");

            } else {
                filterRequest = BaseFragment.filterRequestData;

                if (filterRequest.getPriceMin().isEmpty()) {
                    filterRequest.setPriceMin("0");
                }

                if (filterRequest.getPriceMax().isEmpty()) {
                    filterRequest.setPriceMax("5000");
                }

                if (filterRequest.getRateMin().isEmpty()) {
                    filterRequest.setRateMin("0");
                }

                if (filterRequest.getRateMax().isEmpty()) {
                    filterRequest.setRateMax("5");
                }
            }


            if (service.getIsExpandable().equalsIgnoreCase("1")) {
                String serviceId = "";
                String serviceName = "";

                for (SubService subService : service.getSubServices()) {
                    if (serviceId.isEmpty()) {
                        serviceId = serviceId + "" + subService.getId();
                    } else {
                        serviceId = serviceId + "," + subService.getId();
                    }
                    if (serviceName.isEmpty()) {
                        serviceName = serviceName + "" + subService.getName();
                    } else {
                        serviceName = serviceName + ", " + subService.getName();
                    }
                }
                filterRequest.setSportId(serviceId);
                filterRequest.setSportName(service.getName() + " : " + serviceName);
            } else {

                filterRequest.setSportId(service.getId());
                filterRequest.setSportName(service.getName());
            }

            List<Service> servicesTemp = new ArrayList<>();

            for (Service service1 : data.getSports()) {
                if (service1.getId().equalsIgnoreCase(service.getId())) {
                    if (service.getIsExpandable().equalsIgnoreCase("1")) {
                        int i = 0;
                        for (SubService subService : service.getSubServices()) {
                            subService.setIsSelected("1");
                            subService.setSelect(true);
                            service.getSubServices().set(i, subService);
                            i = i + 1;
                        }
                        servicesTemp.add(service1);
                    } else {

                        servicesTemp.add(service1);
                    }

                } else {
                    servicesTemp.add(service1);
                }
            }


            filterRequest.setServices(servicesTemp);
            BaseFragment.filterRequestData = filterRequest;

        }));
        recyclerSport.setOnFlingListener(null);
        StartSnapHelper snapHelperSport = new StartSnapHelper();
        snapHelperSport.attachToRecyclerView(recyclerSport);
        //   Sport section end


        //   Near local start
        recyclerNearLocalExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearLocalExp.setAdapter(new HomeLocalAdapter(getActivity(), data.getNearestLocals(), (localNew, pos) -> {
            Log.e(":::::", "Click New local");
            presenter.openLocalDetails(localNew);
        }));
        recyclerNearLocalExp.setOnFlingListener(null);
        StartSnapHelper snapHelperNearLocal = new StartSnapHelper();
        snapHelperNearLocal.attachToRecyclerView(recyclerNearLocalExp);
        //   Near local end


        //   Trendy exp start

        homeExperienceAdapterTrendyExp = new HomeExperienceAdapter(getActivity(), data.getTrendyExperience(), new HomeExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);

            }

            @Override
            public void onClickFav(ExpereinceNew expereinceNew, int pos) {
                setFavExp(expereinceNew.getId(), new CallBackMainActivity() {
                    @Override
                    public void onSuccess(boolean status) {
                        if (status) {
                            // notifyAdapter();
                        }
                    }
                });


            }
        });

        recyclerTrendyExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerTrendyExp.setAdapter(homeExperienceAdapterTrendyExp);
        recyclerTrendyExp.setOnFlingListener(null);
        StartSnapHelper snapHelperTrendyExp = new StartSnapHelper();
        snapHelperTrendyExp.attachToRecyclerView(recyclerTrendyExp);
        //Trendy exp end


        // Top sports location start
        recyclerTopSportLocation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerTopSportLocation.setAdapter(new HomeLocationAdapter(getActivity(), data.getTopSportDestination(), (locationNew, pos) -> {
            Log.e(":::::", "Click Location");

            FilterRequest filterRequest;

            if (BaseFragment.filterRequestData == null) {
                filterRequest = new FilterRequest();

                filterRequest.setPriceMin("0");
                filterRequest.setPriceMax("5000");
                filterRequest.setRateMin("0");
                filterRequest.setRateMax("5");

            } else {
                filterRequest = BaseFragment.filterRequestData;

                if (filterRequest.getPriceMin().isEmpty()) {
                    filterRequest.setPriceMin("0");
                }

                if (filterRequest.getPriceMax().isEmpty()) {
                    filterRequest.setPriceMax("5000");
                }

                if (filterRequest.getRateMin().isEmpty()) {
                    filterRequest.setRateMin("0");
                }

                if (filterRequest.getRateMax().isEmpty()) {
                    filterRequest.setRateMax("5");
                }

            }


            filterRequest.setAddres(locationNew.getCity());
            filterRequest.setLatitude(locationNew.getLett());
            filterRequest.setLongitude(locationNew.getLngt());
            filterRequest.setCity(locationNew.getCity());
            filterRequest.setCountry("");

            BaseFragment.filterRequestData = filterRequest;


            openSecondTab();

        }));
        recyclerTopSportLocation.setOnFlingListener(null);
        StartSnapHelper snapHelperTopLocation = new StartSnapHelper();
        snapHelperTopLocation.attachToRecyclerView(recyclerTopSportLocation);
        // Top sports location end

        //   New exp start

        homeExperienceAdapterNewExp = new HomeExperienceAdapter(getActivity(), data.getNewExperience(), new HomeExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);

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
        recyclerNewExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNewExp.setAdapter(homeExperienceAdapterNewExp);

        recyclerNewExp.setOnFlingListener(null);
        StartSnapHelper snapHelperNewExp = new StartSnapHelper();
        snapHelperNewExp.attachToRecyclerView(recyclerNewExp);
        //New exp end


        //   Staff picks exp start
        homeExperienceAdapterStaffPick = new HomeExperienceAdapter(getActivity(), data.getStaffPicks(), new HomeExperienceAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);

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

        recyclerStaffPicks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerStaffPicks.setAdapter(homeExperienceAdapterStaffPick);

        recyclerStaffPicks.setOnFlingListener(null);
        StartSnapHelper snapHelperStaffPicks = new StartSnapHelper();
        snapHelperStaffPicks.attachToRecyclerView(recyclerStaffPicks);
        //Staff picks exp end


    }

    private void openSecondTab() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) (getActivity())).setSelectonTab(1);
            ((MainActivity) (getActivity())).setSelection(1);
            new Handler().postDelayed(() -> {
                EventBus.getDefault().post(EventBusAction.VISITORSELECTSECONDTAB);
            }, 300);
        }

    }

    @OnClick(R.id.customTextViewFilter)
    public void onClick() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) (getActivity())).setSelectonTab(1);
            ((MainActivity) (getActivity())).setSelection(1);
            new Handler().postDelayed(() -> {
                EventBus.getDefault().post(EventBusAction.VISITORSELECTSECONDTABFILTER);
            }, 300);
        }
    }

    private void notifyDataChangeFav() {

        if (homeExperienceAdapterRecommendExp != null) {
            homeExperienceAdapterRecommendExp.notifyDataSetChanged();
        }

        if (homeExperienceAdapterStaffPick != null) {
            homeExperienceAdapterStaffPick.notifyDataSetChanged();
        }

        if (homeExperienceAdapterNewExp != null) {
            homeExperienceAdapterNewExp.notifyDataSetChanged();
        }

        if (homeExperienceAdapterTrendyExp != null) {
            homeExperienceAdapterTrendyExp.notifyDataSetChanged();
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
            notifyDataChangeFav();
        }
    }


}
