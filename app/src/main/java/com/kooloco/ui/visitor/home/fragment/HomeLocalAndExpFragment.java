package com.kooloco.ui.visitor.home.fragment;
/**
 * Created by hlink on 21/4/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.FilterRequest;
import com.kooloco.model.LocalNew;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.home.adapter.HomeLocalAndExpExperienceWithScrollAdapter;
import com.kooloco.ui.visitor.home.adapter.HomeLocalAndExpLocalWithScrollAdapter;
import com.kooloco.ui.visitor.home.presenter.HomeLocalAndExpPresenter;
import com.kooloco.ui.visitor.home.view.HomeLocalAndExpView;
import com.kooloco.util.LocationManager;
import com.kooloco.util.TimeConvertUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hlink on 8/1/18.
 */

public class HomeLocalAndExpFragment extends BaseFragment<HomeLocalAndExpPresenter, HomeLocalAndExpView> implements HomeLocalAndExpView {

    @BindView(R.id.customTextViewFilter)
    AppCompatTextView customTextViewFilter;
    @BindView(R.id.recyclerViewLocal)
    RecyclerView recyclerViewLocal;
    @BindView(R.id.buttonResetFilter)
    AppCompatButton buttonResetFilter;
    @BindView(R.id.linearLayoutNoData)
    LinearLayout linearLayoutNoData;
    @BindView(R.id.swipeRefreshLocal)
    SwipeRefreshLayout swipeRefreshLocal;
    @BindView(R.id.linearLayoutLocal)
    LinearLayout linearLayoutLocal;
    @BindView(R.id.recyclerViewExp)
    RecyclerView recyclerViewExp;
    @BindView(R.id.buttonResetFilterExp)
    AppCompatButton buttonResetFilterExp;
    @BindView(R.id.linearLayoutNoDataExp)
    LinearLayout linearLayoutNoDataExp;
    @BindView(R.id.swipeRefreshExp)
    SwipeRefreshLayout swipeRefreshExp;
    @BindView(R.id.linearLayoutExp)
    LinearLayout linearLayoutExp;
    @BindView(R.id.viewFliper)
    ViewFlipper viewFliper;

    List<ExpereinceNew> dataExp;
    List<LocalNew> dataLocal;
    HomeLocalAndExpExperienceWithScrollAdapter homeExperienceAdapter;
    HomeLocalAndExpLocalWithScrollAdapter homeLocalAdapter;

    boolean isApplyFilterExp;

    boolean isExpSelect = false;
    @BindView(R.id.radioButtonLocal)
    AppCompatRadioButton radioButtonLocal;
    @BindView(R.id.radioButtonExp)
    AppCompatRadioButton radioButtonExp;

    int pageLocal = 1;
    int pageExp = 1;
    LinearLayoutManager linearLayoutManagerLocal;
    LinearLayoutManager linearLayoutManagerExp;

    int pastVisiblesItemsLocal, visibleItemCountLocal, totalItemCountLocal;
    int pastVisiblesItemsExp, visibleItemCountExp, totalItemCountExp;

    boolean isLoadingLocal = false;
    boolean isLoadingExp = false;
    FilterRequest filterRequest = new FilterRequest();

    boolean isHome = false;

    @BindView(R.id.imageViewCount)
    ImageView imageViewCount;
    @BindView(R.id.textViewSetDataNo)
    AppCompatTextView textViewSetDataNo;

    @Inject
    Session session;
    @BindView(R.id.textViewSetDataNo_new)
    AppCompatTextView textViewSetDataNoNew;
    @BindView(R.id.textViewSetDataNoMainTitle)
    AppCompatTextView textViewSetDataNoMainTitle;
    @BindView(R.id.textViewSetDataNoMainTitleNew)
    AppCompatTextView textViewSetDataNoMainTitleNew;

    @Override
    protected int createLayout() {
        return R.layout.home_local_and_exp_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HomeLocalAndExpView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        setDot(imageViewCount);

        textViewSetDataNo.setText(getString(R.string.choose_less_filters_to_get_more_changes_to_find_local_around_you, "Robinson"));

        textViewSetDataNoNew.setText(getString(R.string.choose_less_filters_to_get_more_changes_to_find_local_around_you, "Robinson"));


        String s1 = getString(R.string.your_made_it) + " " + getString(R.string.text_no_exp_found);

        Spannable wordtoSpan1 = new SpannableString(s1);

        int startIndex1=s1.indexOf(getString(R.string.text_no_exp_found));

        wordtoSpan1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greyText)), startIndex1, startIndex1+getString(R.string.text_no_exp_found).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewSetDataNoMainTitleNew.setText(wordtoSpan1);


        String s = getString(R.string.oops) + " " + getString(R.string.text_no_local_found);

        Spannable wordtoSpan = new SpannableString(s);

        int startIndex=s.indexOf(getString(R.string.text_no_local_found));

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greyText)), startIndex, startIndex+getString(R.string.text_no_local_found).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewSetDataNoMainTitle.setText(wordtoSpan);



        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in); // load an animation
        viewFliper.setInAnimation(in); // set in Animation for ViewFlipper

        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out); // load an animation
        viewFliper.setOutAnimation(out); // set out Animation for ViewSwitcher

        swipeRefreshLocal.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefreshLocal.setOnRefreshListener(() -> {

            ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
                @Override
                public void onLocationAvailable(LatLng latLng) {
                    BaseFragment.latitude = latLng.latitude;
                    BaseFragment.longitude = latLng.longitude;

                    if (presenter != null) {
                        pageLocal = 1;

                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageLocal, false, filterRequest, "2");
                        } else {
                            presenter.getlocalData(pageLocal);
                        }

                    }

                }

                @Override
                public void onFail(Status status) {
                    Log.e("Err", status.toString());

                    if (presenter != null) {
                        pageLocal = 1;

                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageLocal, false, filterRequest, "2");
                        } else {
                            presenter.getlocalData(pageLocal);
                        }

                    }
                }
            });
        });
        swipeRefreshExp.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefreshExp.setOnRefreshListener(() -> {


            ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
                @Override
                public void onLocationAvailable(LatLng latLng) {
                    BaseFragment.latitude = latLng.latitude;
                    BaseFragment.longitude = latLng.longitude;

                    if (presenter != null) {
                        pageExp = 1;
                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageExp, false, filterRequest, "3");
                        } else {
                            presenter.getExpData(pageExp);
                        }

                    }


                }

                @Override
                public void onFail(Status status) {
                    Log.e("Err", status.toString());

                    if (presenter != null) {
                        pageExp = 1;
                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageExp, false, filterRequest, "3");
                        } else {
                            presenter.getExpData(pageExp);
                        }

                    }

                }
            });

        });


        dataLocal = new ArrayList<>();
        dataExp = new ArrayList<>();

        homeLocalAdapter = new HomeLocalAndExpLocalWithScrollAdapter(getActivity(), dataLocal, (localNews, pos) -> {
            Log.e(":::::", "Click local");
            presenter.openLocalDetails(localNews);
        });

        linearLayoutManagerLocal = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewLocal.setLayoutManager(linearLayoutManagerLocal);
        recyclerViewLocal.setAdapter(homeLocalAdapter);


        homeExperienceAdapter = new HomeLocalAndExpExperienceWithScrollAdapter(getActivity(), dataExp, new HomeLocalAndExpExperienceWithScrollAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {
                presenter.openExpDetails(expereinceNew);
            }

            @Override
            public void onClickFav(ExpereinceNew expereinceNew, int pos) {
                setFavExp(expereinceNew.getId(), new CallBackMainActivity() {
                    @Override
                    public void onSuccess(boolean status) {
                        //notifyDataChange();
                    }
                });
            }
        });

        linearLayoutManagerExp = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerViewExp.setLayoutManager(linearLayoutManagerExp);
        recyclerViewExp.setAdapter(homeExperienceAdapter);


        recyclerViewLocal.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCountLocal = linearLayoutManagerLocal.getChildCount();
                totalItemCountLocal = linearLayoutManagerLocal.getItemCount();
                pastVisiblesItemsLocal = linearLayoutManagerLocal.findFirstVisibleItemPosition();
                if ((visibleItemCountLocal + 1 + pastVisiblesItemsLocal) >= totalItemCountLocal) {
                    if (isLoadingLocal) {
                        pageLocal = pageLocal + 1;
                        isLoadingLocal = false;

                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageLocal, false, filterRequest, "2");
                        } else {
                            presenter.getlocalData(pageLocal);
                        }

                    }
                }

            }
        });


        recyclerViewExp.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCountExp = linearLayoutManagerExp.getChildCount();
                totalItemCountExp = linearLayoutManagerExp.getItemCount();
                pastVisiblesItemsExp = linearLayoutManagerExp.findFirstVisibleItemPosition();
                if ((visibleItemCountExp + 1 + pastVisiblesItemsExp) >= totalItemCountExp) {
                    if (isLoadingExp) {

                        pageExp = pageExp + 1;
                        isLoadingExp = false;
                        if (isApplyFilterExp) {
                            presenter.getDataFilter(pageExp, false, filterRequest, "3");
                        } else {
                            presenter.getExpData(pageExp);
                        }
                    }
                }

            }
        });

        presenter.getDataLocal();

        ((BaseActivity) getActivity()).getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;

                if (recyclerViewLocal != null) {
                    recyclerViewLocal.postDelayed(() -> {
                        if (presenter != null) {
                            pageLocal = 1;
                            pageExp = 1;

                            int tempPage = 1;
                            if (isApplyFilterExp) {
                                presenter.getDataFilter(tempPage, false, filterRequest, "1");
                            } else {
                                presenter.getData(false);
                            }

                        }

                    }, 500);
                }

            }

            @Override
            public void onFail(Status status) {
                Log.e("Err", status.toString());
                BaseFragment.latitude = 0.0;
                BaseFragment.longitude = 0.0;

                if (recyclerViewLocal != null) {
                    recyclerViewLocal.postDelayed(() -> {
                        if (presenter != null) {
                            pageLocal = 1;
                            pageExp = 1;

                            int tempPage = 1;
                            if (isApplyFilterExp) {
                                presenter.getDataFilter(tempPage, false, filterRequest, "1");
                            } else {
                                presenter.getData(false);
                            }

                        }

                    }, 500);
                }
            }
        });


    }

    @OnClick({R.id.radioButtonLocal, R.id.radioButtonExp, R.id.buttonResetFilter, R.id.buttonResetFilterExp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioButtonLocal:
                if (isExpSelect) {
                    isExpSelect = false;
                    viewFliper.showPrevious();
                }
                break;
            case R.id.radioButtonExp:
                if (!isExpSelect) {
                    isExpSelect = true;
                    viewFliper.showNext();
                }
                break;
            case R.id.buttonResetFilterExp:
            case R.id.buttonResetFilter:
                isApplyFilterExp = false;
                pageExp = 1;
                pageLocal = 1;
                filterRequest = new FilterRequest();
                BaseFragment.filterRequestData = new FilterRequest();

                customTextViewFilter.setText(getActivity().getResources().getString(R.string.where_when_where));
                presenter.getData(true);
                break;
        }
    }

    @Override
    public void setExpData(List<ExpereinceNew> listExpNew, int page) {
        if (page == 1) {
            dataExp.clear();
        }

        if (swipeRefreshExp != null) {
            if (swipeRefreshExp.isRefreshing()) {
                swipeRefreshExp.setRefreshing(false);
            }
        }

        dataExp.addAll(listExpNew);

        isLoadingExp = !listExpNew.isEmpty();

        if (homeExperienceAdapter != null) {
            homeExperienceAdapter.notifyDataSetChanged();

            if (page == 1) {
                if (!dataExp.isEmpty()) {
                    if (recyclerViewExp != null) {
                        recyclerViewExp.scrollToPosition(0);
                    }
                }
            }

        }

        if (isApplyFilterExp) {
            if (linearLayoutNoData != null) {
                linearLayoutNoData.setVisibility((dataExp.size() == 0) ? View.VISIBLE : View.INVISIBLE);
            }
        } else {
            if (linearLayoutNoData != null) {
                if (linearLayoutNoData.getVisibility() == View.VISIBLE) {
                    linearLayoutNoData.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void setLocalData(List<LocalNew> listLocalNew, int page) {
        if (page == 1) {
            dataLocal.clear();
        }

        dataLocal.addAll(listLocalNew);

        isLoadingLocal = !listLocalNew.isEmpty();

        if (swipeRefreshLocal != null) {
            if (swipeRefreshLocal.isRefreshing()) {
                swipeRefreshLocal.setRefreshing(false);
            }
        }

        if (homeLocalAdapter != null) {
            homeLocalAdapter.notifyDataSetChanged();
            if (page == 1) {
                if (!dataLocal.isEmpty()) {
                    if (recyclerViewLocal != null) {
                        recyclerViewLocal.scrollToPosition(0);
                    }
                }
            }
        }

        if (isApplyFilterExp) {
            if (linearLayoutNoDataExp != null) {
                linearLayoutNoDataExp.setVisibility((dataLocal.size() == 0) ? View.VISIBLE : View.INVISIBLE);
            }
        } else {
            if (linearLayoutNoDataExp != null) {
                if (linearLayoutNoDataExp.getVisibility() == View.VISIBLE) {
                    linearLayoutNoDataExp.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @OnClick(R.id.imageViewNotification)
    public void onClick() {
        presenter.openNotification();
    }

    @OnClick(R.id.customTextViewFilter)
    public void onClickFilter() {
        filterData((isApply, filterRequest) -> {
            isApplyFilterExp = isApply;
            this.filterRequest = filterRequest;

            pageLocal = 1;
            pageExp = 1;

            int page = 1;
            if (isApplyFilterExp) {

                if (viewFliper != null) {
                    if (!isExpSelect) {
                        isExpSelect = true;
                        radioButtonExp.setChecked(isExpSelect);
                        viewFliper.showNext();
                    }
                }

                presenter.getDataFilter(page, true, filterRequest, "1");

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


                if (!filterRequest.getDurationText().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getDurationText();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getDurationText();

                    }
                }

                if (!filterRequest.getActivityName().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getActivityName();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getActivityName();

                    }
                } /*else {
                    textGenrate = textGenrate + " - activity";
                }*/

                if (!filterRequest.getRecomanText().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getRecomanText();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getRecomanText();

                    }
                }

                if (!filterRequest.getPerfectForText().isEmpty()) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + "" + filterRequest.getPerfectForText();

                    } else {
                        textGenrate = textGenrate + " - " + filterRequest.getPerfectForText();

                    }
                }

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

                if (!(filterRequest.getPriceMin().equalsIgnoreCase("0") && filterRequest.getPriceMax().equalsIgnoreCase("5000"))) {
                    if (textGenrate.isEmpty()) {
                        textGenrate = textGenrate + BaseActivity.currency + filterRequest.getPriceMin() + " to " + BaseActivity.currency + filterRequest.getPriceMax() + "";

                    } else {
                        textGenrate = textGenrate + " - " + BaseActivity.currency + filterRequest.getPriceMin() + " to " + BaseActivity.currency + filterRequest.getPriceMax() + "";
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

                customTextViewFilter.setText(getActivity().getResources().getString(R.string.where_when_where));
                presenter.getData(true);
            }

        }, !isApplyFilterExp, isHome);
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
        if (action == EventBusAction.VISITORSELECTSECONDTAB) {
            isApplyFilterExp = true;

            if (BaseFragment.filterRequestData != null) {

                isApplyFilterExp = true;

                pageLocal = 1;
                pageExp = 1;

                int page = 1;

                isHome = true;

                filterRequest = BaseFragment.filterRequestData;

                if (isApplyFilterExp) {


                    presenter.getDataFilter(page, true, filterRequest, "1");

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


                    if (!filterRequest.getDurationText().isEmpty()) {
                        if (textGenrate.isEmpty()) {
                            textGenrate = textGenrate + "" + filterRequest.getDurationText();

                        } else {
                            textGenrate = textGenrate + " - " + filterRequest.getDurationText();

                        }
                    }

                    if (!filterRequest.getActivityName().isEmpty()) {
                        if (textGenrate.isEmpty()) {
                            textGenrate = textGenrate + "" + filterRequest.getActivityName();

                        } else {
                            textGenrate = textGenrate + " - " + filterRequest.getActivityName();

                        }
                    } /*else {
                    textGenrate = textGenrate + " - activity";
                }*/

                    if (!filterRequest.getRecomanText().isEmpty()) {
                        if (textGenrate.isEmpty()) {
                            textGenrate = textGenrate + "" + filterRequest.getRecomanText();

                        } else {
                            textGenrate = textGenrate + " - " + filterRequest.getRecomanText();

                        }
                    }

                    if (!filterRequest.getPerfectForText().isEmpty()) {
                        if (textGenrate.isEmpty()) {
                            textGenrate = textGenrate + "" + filterRequest.getPerfectForText();

                        } else {
                            textGenrate = textGenrate + " - " + filterRequest.getPerfectForText();

                        }
                    }

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

                    if (!(filterRequest.getPriceMin().equalsIgnoreCase("0") && filterRequest.getPriceMax().equalsIgnoreCase("5000"))) {
                        if (textGenrate.isEmpty()) {
                            textGenrate = textGenrate + BaseActivity.currency + filterRequest.getPriceMin() + " to " + BaseActivity.currency + filterRequest.getPriceMax() + "";

                        } else {
                            textGenrate = textGenrate + " - " + BaseActivity.currency + filterRequest.getPriceMin() + " to " + BaseActivity.currency + filterRequest.getPriceMax() + "";
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

                }
            }

            if (viewFliper != null) {
                if (!isExpSelect) {
                    isExpSelect = true;
                    radioButtonExp.setChecked(isExpSelect);
                    viewFliper.showNext();
                }
            }
        }

        if (action == EventBusAction.NOTIFICATIONCOUNTUIVISITOR) {
            setDot(imageViewCount);
        }

        if (action == EventBusAction.VISITORSELECTSECONDTABFILTER) {
            if (viewFliper != null) {
                if (!isExpSelect) {
                    isExpSelect = true;
                    radioButtonExp.setChecked(isExpSelect);
                    viewFliper.showNext();
                }
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (customTextViewFilter != null) {
                        customTextViewFilter.performClick();
                    }
                }
            });
        }

        if (action == EventBusAction.FAVREFRESE) {
            notifyDataChangeFav();
        }
    }

    private void notifyDataChangeFav() {
        if (homeExperienceAdapter != null) {
            homeExperienceAdapter.notifyDataSetChanged();
        }
    }


}
