package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 13/4/18.
 */

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Activities;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.SelectExperienceAdapter;
import com.kooloco.uilocal.expereince.presenter.ExperienceSelectPresenter;
import com.kooloco.uilocal.expereince.view.ExperienceSelectView;
import com.kooloco.util.StartSnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperienceSelectFragment extends BaseFragment<ExperienceSelectPresenter, ExperienceSelectView> implements ExperienceSelectView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.imageHelp)
    ImageView imageHelp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appCompatTextView4)
    AppCompatTextView appCompatTextView4;
    @BindView(R.id.recyclerSportType)
    RecyclerView recyclerSportType;
    @BindView(R.id.imageViewSelection)
    ImageView imageViewSelection;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.linearLayoutButtonAction)
    LinearLayout linearLayoutButtonAction;
    @BindView(R.id.root)
    ConstraintLayout root;
    List<Activities> activities;
    SelectExperienceAdapter selectActivitiesNewAdapter;
    int selectActivity = 0;
    Activities activitiesSelect = null;
    LinearLayoutManager linearLayoutManager;
    private boolean isEdit;
    private String expId = "";
    private String sportId = "";


    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_exp_select;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSelectView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));

        if (activities == null) {
            activities = new ArrayList<>();
        }



        setDataBottom();

        presenter.getData(expId, sportId);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSportType.setLayoutManager(linearLayoutManager);

        selectActivitiesNewAdapter = new SelectExperienceAdapter(getActivity(), activities, selectActivity, (position, activities1) -> {
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

    }


    @Override
    public void setData(List<Activities> data, int selectIndex) {
        activities.clear();

        activities.addAll(data);

        selectActivity = selectIndex;

        selectActivitiesNewAdapter.setSelectPosition(selectActivity);
        activitiesSelect = activities.get(selectActivity);

        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(selectActivity, 20);
        }

        if (selectActivitiesNewAdapter != null) {
            selectActivitiesNewAdapter.notifyDataSetChanged();
        }
        setDataBottom();
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    private void setDataBottom() {
        imageViewSelection.setVisibility((activitiesSelect != null) ? View.VISIBLE : View.GONE);
        if (activitiesSelect != null) {
            customTextViewServiceTypeValue.setText(activitiesSelect.getDesc());
        }
    }

    @OnClick({R.id.imageViewBack, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                presenter.callWs(expId, activitiesSelect, sportId, isEdit);
                break;
        }
    }
}
