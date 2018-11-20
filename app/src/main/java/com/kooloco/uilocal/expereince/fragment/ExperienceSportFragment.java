package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 13/4/18.
 */

import android.os.Handler;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesServiceNewAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesSportServiceSubNewAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperienceSportPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSportView;
import com.kooloco.util.StartSnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceSportFragment extends BaseFragment<ExperienceSportPresenter, ExperienceSportView> implements ExperienceSportView {

    @BindView(R.id.appCompatTextView4)
    AppCompatTextView appCompatTextView4;
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    @BindView(R.id.recyclerSportTypeSub)
    RecyclerView recyclerSportTypeSub;
    @BindView(R.id.linearLayoutSportType)
    LinearLayout linearLayoutSportType;
    @BindView(R.id.imageViewSelection)
    ImageView imageViewSelection;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.linearLayoutButtonAction)
    LinearLayout linearLayoutButtonAction;
    @BindView(R.id.root)
    LinearLayout root;
    private boolean isEdit;
    LinearLayoutManager linearLayoutManager;

    int selectMainService = -1;
    Service serviceSelect = null;

    int selectSubService = -1;
    SubService subServiceSelect = null;

    SelectActivitiesServiceNewAdapter selectActivitiesServiceNewAdapter;

    List<Service> services;
    private String expId = "";

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_sport;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSportView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        buttonNext.setText(getActivity().getResources().getString(R.string.button_next));

        services = new ArrayList<>();


        presenter.getData(expId);

        setDataBottom();


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSportType.setLayoutManager(linearLayoutManager);


        selectActivitiesServiceNewAdapter = new SelectActivitiesServiceNewAdapter(getActivity(), services, selectMainService, new SelectActivitiesServiceNewAdapter.CallBack() {
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


    @OnClick({R.id.imageViewBack, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                if (subServiceSelect != null) {
                    presenter.callWs(expId, subServiceSelect.getId(), isEdit);
                } else {
                    presenter.callWs(expId, serviceSelect.getId(), isEdit);
                }
                break;
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setData(List<Service> data, int selectMainServiceData, int selectSubServiceData) {

        services.addAll(data);

        //Temp start
        if (isEdit) {
            this.selectMainService = selectMainServiceData;
            selectActivitiesServiceNewAdapter.setSelectPosition(this.selectMainService);
            serviceSelect = services.get(this.selectMainService);

            if (linearLayoutManager != null) {
                linearLayoutManager.scrollToPositionWithOffset(selectMainService, 20);
            }

            this.selectSubService = selectSubServiceData;
            if (selectSubService != -1) {
                subServiceSelect = services.get(this.selectMainService).getSubServices().get(selectSubService);
            }
            setDataBottom();
        }

        //Temp end

        if (selectActivitiesServiceNewAdapter != null) {
            selectActivitiesServiceNewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
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

    private void setDataBottom() {

        if (linearLayoutButtonAction == null) {
            return;
        }

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

}
