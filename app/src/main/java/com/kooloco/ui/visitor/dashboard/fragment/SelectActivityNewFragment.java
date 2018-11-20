package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink on 14/2/18.
 */

import android.support.v7.widget.AppCompatButton;
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
import com.kooloco.model.Activities;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.SelectActivitiesNewAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.SelectActivityNewPresenter;
import com.kooloco.ui.visitor.dashboard.view.SelectActivityNewView;
import com.kooloco.util.StartSnapHelper;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class SelectActivityNewFragment extends BaseFragment<SelectActivityNewPresenter, SelectActivityNewView> implements SelectActivityNewView {
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;

    List<Activities> activities;
    SelectActivitiesNewAdapter selectActivitiesNewAdapter;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageHelp)
    ImageView imageHelp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewSportValue)
    AppCompatTextView customTextViewSportValue;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
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
    private VisitorBooking visitorBooking;

    int selectActivity = -1;
    Activities activitiesSelect = null;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_select_activities_new;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SelectActivityNewView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (activities == null) {
            activities = new ArrayList<>();

            presenter.getData(visitorBooking);
        }

        setDataBottom();

        if (!visitorBooking.getLocalImage().isEmpty()) {
            Picasso.with(getActivity()).load(visitorBooking.getLocalImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }
        customTextViewName.setText(visitorBooking.getLocalName());

        customTextViewSportValue.setText(visitorBooking.getSportName() + " with");


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSportType.setLayoutManager(linearLayoutManager);

        selectActivitiesNewAdapter = new SelectActivitiesNewAdapter(getActivity(), activities, selectActivity, (position, activities1) -> {
            linearLayoutManager.scrollToPositionWithOffset(position, 20);

            selectActivity = position;
            activitiesSelect = activities1;
            setDataBottom();
        });

        recyclerSportType.setAdapter(selectActivitiesNewAdapter);


        recyclerSportType.setOnFlingListener(null);

        StartSnapHelper snapHelper = new StartSnapHelper();

        snapHelper.attachToRecyclerView(recyclerSportType);


        if (selectActivity == -1) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(selectActivity, 0);
        }

        recyclerSportType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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

    @Override
    public void setVisitorBooking(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

    @Override
    public void setData(List<Activities> data) {
        activities.addAll(data);

        if (selectActivitiesNewAdapter != null) {
            selectActivitiesNewAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.imageViewBack)
    public void onClickBack() {
        goBack();
    }

    private void setDataBottom() {
        linearLayoutButtonAction.setVisibility((activitiesSelect != null) ? View.VISIBLE : View.GONE);
        if (activitiesSelect != null) {
            customTextViewServiceTypeValue.setText(activitiesSelect.getDesc());
        }
    }

    @OnClick(R.id.buttonNext)
    public void onClick() {
        setExperienceData(false, activitiesSelect);
        presenter.openSelectDate(visitorBooking);
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
}
