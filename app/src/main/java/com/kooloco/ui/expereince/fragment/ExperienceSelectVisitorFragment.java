package com.kooloco.ui.expereince.fragment;
/**
 * Created by hlink on 21/4/18.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.adapter.ExperienceSelectVisitorAdapter;
import com.kooloco.ui.expereince.presenter.ExperienceSelectVisitorPresenter;
import com.kooloco.ui.expereince.view.ExperienceSelectVisitorView;
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

public class ExperienceSelectVisitorFragment extends BaseFragment<ExperienceSelectVisitorPresenter, ExperienceSelectVisitorView> implements ExperienceSelectVisitorView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewExpTitle)
    AppCompatTextView customTextViewExpTitle;
    @BindView(R.id.recyclerExpSelect)
    RecyclerView recyclerExpSelect;
    ExperienceSelectVisitorAdapter experienceSelectVisitorAdapter;

    ExpereinceNew expereinceNew;
    int selectExp = -1;

    List<ExpereinceNew> expereinceNews;
    private ExperienceBookingFlow experienceBookingFlow;

    @Override
    protected int createLayout() {
        return R.layout.exp_select_visitor_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperienceSelectVisitorView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        expereinceNews = new ArrayList<>();

        expereinceNews.addAll(experienceBookingFlow.getExpereinceNews());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerExpSelect.setLayoutManager(linearLayoutManager);
        experienceSelectVisitorAdapter = new ExperienceSelectVisitorAdapter(getActivity(), expereinceNews, selectExp, (expereinceNew, pos) -> {
            linearLayoutManager.scrollToPositionWithOffset(pos, getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_7));
            this.expereinceNew = expereinceNew;
            selectExp = pos;
        });
        recyclerExpSelect.setAdapter(experienceSelectVisitorAdapter);
        recyclerExpSelect.setOnFlingListener(null);
        StartSnapHelper snapHelperTrendyExp = new StartSnapHelper();
        snapHelperTrendyExp.attachToRecyclerView(recyclerExpSelect);

        setHeadData();
    }

    private void setHeadData() {
        customTextViewLocalName.setText(experienceBookingFlow.getLocalName());
        Picasso.with(getActivity()).load(experienceBookingFlow.getLocalProfile()).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(imageViewProfile);
        customTextViewExpTitle.setVisibility(View.GONE);
    }


    @OnClick({R.id.imageViewBack, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                if (expereinceNew == null) {
                    showMessage(getString(R.string.val_select_exp_first));
                    return;
                }
                if(expereinceNew!=null){
                    experienceBookingFlow.setExpereinceNew(expereinceNew);
                    presenter.openExpSelectDate(experienceBookingFlow);
                }
                break;
        }
    }

    @Override
    public void setData(List<ExpereinceNew> listExpNew) {
     /*   expereinceNews.addAll(listExpNew);

        if (experienceSelectVisitorAdapter != null) {
            experienceSelectVisitorAdapter.notifyDataSetChanged();
        }*/

    }

    @Override
    public void setExpBookingData(ExperienceBookingFlow experienceBookingFlow) {
        this.experienceBookingFlow = experienceBookingFlow;
    }
}
