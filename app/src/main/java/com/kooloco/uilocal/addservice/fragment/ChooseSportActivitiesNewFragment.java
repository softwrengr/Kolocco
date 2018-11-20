package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 4/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kooloco.R;
import com.kooloco.constant.Step;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.ChooseSportActivityNewAdapter;
import com.kooloco.uilocal.addservice.presenter.ChooseSportActivitiesNewPresenter;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesNewView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSportActivitiesNewFragment extends BaseFragment<ChooseSportActivitiesNewPresenter, ChooseSportActivitiesNewView> implements ChooseSportActivitiesNewView {


    ChooseSportActivityNewAdapter chooseSportActivityAdapter;
    List<SportActivity> sportActivities;


    @BindView(R.id.recyclerServiceActivities)
    RecyclerView recyclerServiceActivities;

    boolean isEdit = false;

    private boolean isLocalIncomplete = false;

    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;


    @Override
    protected int createLayout() {
        return R.layout.fragment_choose_sport_activities_new;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseSportActivitiesNewView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (sportActivities == null) {
            sportActivities = new ArrayList<>();
            presenter.getData();
        }
        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);
        } else {
            textViewSkip.setVisibility(View.VISIBLE);
        }

        recyclerServiceActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        chooseSportActivityAdapter = new ChooseSportActivityNewAdapter(getActivity(), sportActivities, new ChooseSportActivityNewAdapter.CallBack() {
            @Override
            public void onClick(SportActivity sportActivity, boolean isShowDialog, int position) {
                hideKeyBoard();
                if (isShowDialog) {


                   /* showSetSportPriceRulesDialog(sportActivity, position, new CallBackPriceSportActivity() {
                        @Override
                        public void onCall(SportActivity sportActivity, int position) {

                            sportActivities.set(position, sportActivity);
                            if (chooseSportActivityAdapter != null) {
                                chooseSportActivityAdapter.notifyDataSetChanged();
                            }

                        }
                    });*/

                } else {
                    sportActivity.setSelect(false);
                    sportActivity.setGroup(false);
                    sportActivity.setPrice("");

                    List<SportSubActivity> sportSubActivities = sportActivity.getSportSubActivities();
                    int i = 0;

                    for (SportSubActivity sportSubActivity1 : sportSubActivities) {
                        sportSubActivity1.setSelect(false);
                        sportSubActivity1.setGroup(false);
                        sportSubActivity1.setPrice("");
                        sportActivity.setOpen(false);

                        sportSubActivities.set(i, sportSubActivity1);
                        i = i + 1;
                    }

                    sportActivity.setSportSubActivities(sportSubActivities);

                    sportActivities.set(position, sportActivity);

                    if (chooseSportActivityAdapter != null) {
                        chooseSportActivityAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onClickSub(SportActivity sportActivity, SportSubActivity sportSubActivity, boolean isShowDialog, int position, int subPosition, CallBackPriceSportSubActivity callBackPriceSportSubActivity) {
                hideKeyBoard();
                if (isShowDialog) {
                    //showSetSportSubPriceRulesDialog(sportSubActivity, subPosition, callBackPriceSportSubActivity);
                } else {
                    sportActivity.setSelect(false);
                    sportActivity.setGroup(false);
                    sportActivity.setPrice("");
                    sportActivity.setOpen(false);

                    List<SportSubActivity> sportSubActivities = sportActivity.getSportSubActivities();
                    int i = 0;

                    for (SportSubActivity sportSubActivity1 : sportSubActivities) {
                        sportSubActivity1.setSelect(false);
                        sportSubActivity1.setGroup(false);
                        sportSubActivity1.setPrice("");
                        sportSubActivities.set(i, sportSubActivity1);
                        i = i + 1;
                    }

                    sportActivity.setSportSubActivities(sportSubActivities);

                    sportActivities.set(position, sportActivity);

                    if (chooseSportActivityAdapter != null) {
                        chooseSportActivityAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        recyclerServiceActivities.setAdapter(chooseSportActivityAdapter);
    }

    @OnClick({R.id.imageViewBack, R.id.buttonNext, R.id.textViewSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                if (checkValidation(sportActivities)) {
                    showMessage(getString(R.string.val_select_service));
                    return;
                }
                presenter.callWs(sportActivities, isEdit,isLocalIncomplete);
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step9, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openSetSchdule();
                    }
                });
                break;
        }
    }

    @Override
    public void setData(final List<SportActivity> data) {
        sportActivities.clear();
        sportActivities.addAll(data);

        if (chooseSportActivityAdapter != null) {
            chooseSportActivityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setIsInComplete(boolean isInComplete) {
        this.isLocalIncomplete = isInComplete;
    }

    @OnClick(R.id.customTextViewAddCard)
    public void onViewClicked() {

    }

    private boolean checkValidation(List<SportActivity> sportActivities) {

        boolean isValid = true;

        for (SportActivity sportActivity : sportActivities) {

            if (sportActivity.isSelect()) {
                if (sportActivity.isExpand()) {
                    for (SportSubActivity sportSubActivity : sportActivity.getSportSubActivities()) {
                        if (sportSubActivity.isSelect()) {
                            isValid = false;
                            return isValid;
                        }
                    }
                } else {
                    isValid = false;
                    return isValid;
                }
            }
        }
        return isValid;
    }

}
