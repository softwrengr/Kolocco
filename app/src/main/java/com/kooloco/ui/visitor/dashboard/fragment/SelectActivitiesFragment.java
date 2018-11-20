package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 25/8/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Activities;
import com.kooloco.model.SelectActivites;
import com.kooloco.model.Service;
import com.kooloco.model.SubService;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesServiceAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesSportServiceAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesSportServiceSubAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.SelectActivitiesPresenter;
import com.kooloco.ui.visitor.dashboard.view.SelectActivitiesView;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectActivitiesFragment extends BaseFragment<SelectActivitiesPresenter, SelectActivitiesView> implements SelectActivitiesView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewServiceType)
    AppCompatTextView customTextViewServiceType;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.customTextViewServiceHour)
    AppCompatTextView customTextViewServiceHour;
    @BindView(R.id.customTextViewSelectActivities)
    AppCompatEditText customTextViewSelectActivities;
    @BindView(R.id.recyclerServiceActivities)
    RecyclerView recyclerServiceActivities;
    @BindView(R.id.customTextViewSelectSportActivity)
    AppCompatEditText customTextViewSelectSportActivity;
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    @BindView(R.id.recyclerSportTypeSub)
    RecyclerView recyclerSportTypeSub;
    @BindView(R.id.linearLayoutSportType)
    LinearLayout linearLayoutSportType;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    String selectExperince = "";
    String selectService = "";
    List<Activities> activities;
    List<Service> services;
    SelectActivitiesServiceAdapter selectActivitiesServiceAdapter;
    SelectActivitiesSportServiceAdapter selectActivitiesSportServiceAdapter;

    private VisitorBooking visitorBooking = null;

    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_select_activities;
    }


    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SelectActivitiesView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setSelactionActvities(0);
        setSelactionSportActivites(1);

        if (!visitorBooking.getLocalImage().isEmpty()) {
            Picasso.with(getActivity()).load(visitorBooking.getLocalImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }
        customTextViewName.setText(visitorBooking.getLocalName());

        if (activities == null && services == null) {

            activities = new ArrayList<>();
            services = new ArrayList<>();

            presenter.getData(visitorBooking.getLocalId());
        }

        selectActivitiesServiceAdapter = new SelectActivitiesServiceAdapter(getActivity(), activities, new SelectActivitiesServiceAdapter.CallBack() {

            @Override
            public void onClickPosition(int position, Activities activities) {
                selectExperince = activities.getId();
                setExperienceData(false, activities);
                selectService = "";

                services.clear();
                services.addAll(activities.getServices());


                if (selectActivitiesSportServiceAdapter != null) {
                    selectActivitiesSportServiceAdapter.setSelectPosition(-1);
                    selectActivitiesSportServiceAdapter.notifyDataSetChanged();


                    if (services.size() != 0) {
                        setSubserviceData(new ArrayList<SubService>(), services.get(0));
                        //setSubserviceData(services.get(0).getSubServices(), services.get(0));
                    }
                }

            }
        });

        recyclerServiceActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerServiceActivities.setAdapter(selectActivitiesServiceAdapter);

        selectActivitiesSportServiceAdapter = new SelectActivitiesSportServiceAdapter(getActivity(), services, new SelectActivitiesSportServiceAdapter.CallBack() {
            @Override
            public void onClickItem(int position, Service service) {
                if (service.getIsExpandable().equalsIgnoreCase("0")) {
                    selectService = service.getId();
                    setSportData(service.getId(), service.getName(), service.getServiceImage(), service.getName(), "");
                } else {
                    selectService = "";
                    setSportData("", "", "", "", "");
                }
                setSubserviceData(service.getSubServices(), service);
            }
        });

        recyclerSportType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerSportType.setAdapter(selectActivitiesSportServiceAdapter);

        setValue();
    }

    @OnClick({R.id.customTextViewSelectActivities, R.id.customTextViewSelectSportActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewSelectActivities:
                setSelactionActvities((recyclerServiceActivities.getVisibility() == View.VISIBLE) ? 1 : 0);
                break;
            case R.id.customTextViewSelectSportActivity:
                setSelactionSportActivites((linearLayoutSportType.getVisibility() == View.VISIBLE) ? 1 : 0);
                break;
        }
    }


    private void setSelactionActvities(int position) {
        customTextViewSelectActivities.setSelected(position == 0);
        recyclerServiceActivities.setVisibility((position == 0) ? View.VISIBLE : View.GONE);
    }

    private void setSelactionSportActivites(int position) {
        customTextViewSelectSportActivity.setSelected(position == 0);
        linearLayoutSportType.setVisibility((position == 0) ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.buttonNext)
    public void onViewClicked() {
        if (selectService.isEmpty()) {
            showMessage("Please select sport activities");
            return;
        }
        presenter.openSelectDate(visitorBooking);
    }

    @Override
    public void setData(SelectActivites data) {
        setSelactionActvities(0);
        setSelactionSportActivites(1);


        activities.clear();
        services.clear();
        activities.addAll(data.getActivities());
        services.addAll(data.getActivities().get(0).getServices());

        if (selectActivitiesServiceAdapter != null) {
            selectActivitiesServiceAdapter.notifyDataSetChanged();
        }

        if (selectActivitiesSportServiceAdapter != null) {
            selectActivitiesSportServiceAdapter.notifyDataSetChanged();
        }

        if (services.size() != 0) {
            //setSubserviceData(services.get(0).getSubServices(), services.get(0));
        }

        setValue();
    }

    @Override
    public void setVisitorBooking(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }


    private void setSubserviceData(List<SubService> subServices, Service service) {
        recyclerSportTypeSub.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerSportTypeSub.setAdapter(new SelectActivitiesSportServiceSubAdapter(getActivity(), subServices, service, new SelectActivitiesSportServiceSubAdapter.CallBack() {
            @Override
            public void onClickItem(SubService subService, Service service) {
                selectService = subService.getId();
                setSportData(subService.getId(), subService.getName() + " " + service.getName(), service.getServiceImage(), service.getName(), subService.getName());
            }
        }));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarIsolatedAppointment(this.getClass().getSimpleName());
    }

    private void setValue() {
        selectExperince = "";
        setExperienceData(true, null);

        selectService = "";
        setSportData("", "", "", "", "");

        if (activities.size() != 0) {
            selectExperince = activities.get(0).getId();
            setExperienceData(false, activities.get(0));
        }
    }

    private void setExperienceData(boolean isClear, Activities activities) {
        if (isClear) {
            visitorBooking.setExperienceId("");
            visitorBooking.setExperienceTitle("");
            visitorBooking.setExperienceDesc("");

            visitorBooking.setDurationExperience("");
            visitorBooking.setPriceExperience("");
            visitorBooking.setIsGroupBookingExperience("0");
            visitorBooking.setParticipantPerExperience("");
            visitorBooking.setParticipantExperience("");
        } else {
            visitorBooking.setExperienceId(activities.getId());
            visitorBooking.setExperienceTitle(activities.getName());
            visitorBooking.setExperienceDesc(activities.getDesc());
            visitorBooking.setDurationExperience(activities.getMinimumDuration());
            visitorBooking.setPriceExperience(activities.getPricePerHour());
            visitorBooking.setIsGroupBookingExperience(activities.getIsMultipaleBooking());
            visitorBooking.setParticipantPerExperience(activities.getParticipantPer());
            visitorBooking.setParticipantExperience(activities.getNumberOfPerticipant());
        }
    }

    private void setSportData(String id, String name, String icon, String mainName, String subName) {
        visitorBooking.setSportId(id);
        visitorBooking.setSportName(name);
        visitorBooking.setSportImage(icon);
        visitorBooking.setSportMainName(mainName);
        visitorBooking.setSportSubName(subName);
    }

    @OnClick({R.id.imageViewBack, R.id.imageHelp})
    public void onViewClickedTool(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageHelp:
                presenter.openKoolocoHelp();
                break;
        }
    }
}
