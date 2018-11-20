package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 3/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.Step;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Activities;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.SelectExpServiceAdapter;
import com.kooloco.uilocal.addservice.presenter.CreateYourExperiencePresenter;
import com.kooloco.uilocal.addservice.view.CreateYourExperienceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateYourExperienceFragment extends BaseFragment<CreateYourExperiencePresenter, CreateYourExperienceView> implements CreateYourExperienceView {

    @BindView(R.id.recyclerServiceActivities)
    RecyclerView recyclerServiceActivities;
    List<Activities> activites;
    SelectExpServiceAdapter selectExpServiceAdapter;
    private boolean isEdit = false;

    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;
    private boolean isIncompleteLocal = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_create_your_experience;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CreateYourExperienceView createView() {
        return this;
    }


    @Override
    protected void bindData() {
        if (activites == null) {
            activites = new ArrayList<>();
            presenter.getData();
        }

        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);

        } else {
            textViewSkip.setVisibility(View.VISIBLE);
        }

        selectExpServiceAdapter = new SelectExpServiceAdapter(getActivity(), activites);

        recyclerServiceActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerServiceActivities.setAdapter(selectExpServiceAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.imageHelp, R.id.buttonNext, R.id.textViewSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.imageHelp:
                presenter.openKoolocoHelp();
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step8, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openChooseSportEquipments();
                    }
                });
                break;
            case R.id.buttonNext:
                boolean isSelect = false;
                for (Activities activities : activites) {
                    if (activities.getIsSelected().equalsIgnoreCase("1")) {
                        isSelect = true;
                        break;
                    }
                }
                if (!isSelect) {
                    showMessage(getString(R.string.val_please_select_your_exp));
                    return;
                }
                presenter.callws(activites, isEdit,isIncompleteLocal);
                break;
        }
    }

    @Override
    public void setData(List<Activities> activitesD) {
        activites.addAll(activitesD);
        if (selectExpServiceAdapter != null) {
            selectExpServiceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setIsLocalInComplete(boolean isLocalIncomplete) {
        this.isIncompleteLocal = isLocalIncomplete;
    }
}
