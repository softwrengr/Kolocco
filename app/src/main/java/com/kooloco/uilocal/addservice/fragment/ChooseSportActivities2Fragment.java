package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 4/10/17.
 */

import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.ChooseSportActivityAdapter;
import com.kooloco.uilocal.addservice.presenter.ChooseSportActivities1Presenter;
import com.kooloco.uilocal.addservice.presenter.ChooseSportActivities2Presenter;
import com.kooloco.uilocal.addservice.view.ChooseSportActivities1View;
import com.kooloco.uilocal.addservice.view.ChooseSportActivities2View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSportActivities2Fragment extends BaseFragment<ChooseSportActivities2Presenter, ChooseSportActivities2View> implements ChooseSportActivities2View {

    @BindView(R.id.recyclerServiceActivities)
    RecyclerView recyclerServiceActivities;
    @BindView(R.id.customTextViewNote)
    AppCompatTextView customTextViewNote;
    ChooseSportActivityAdapter chooseSportActivityAdapter;
    List<SportActivity> sportActivities;

    @Override
    protected int createLayout() {
        return R.layout.fragment_choose_sport_activities2;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseSportActivities2View createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (sportActivities == null) {
            sportActivities = new ArrayList<>();
            presenter.getData();
        }

        String text = "" + getActivity().getResources().getString(R.string.appointment_summery_note).replace("###", "<font color='" + getActivity().getResources().getColor(R.color.red) + "'>* </font>");
        customTextViewNote.setText(Html.fromHtml(text));

        recyclerServiceActivities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        chooseSportActivityAdapter = new ChooseSportActivityAdapter(getActivity(), sportActivities, new ChooseSportActivityAdapter.CallBack() {
            @Override
            public void onClick(SportActivity sportActivity, boolean isShowDialog, int position) {
                hideKeyBoard();
                if (isShowDialog) {
                    showSetSportPriceRulesDialog(sportActivity, position, new CallBackPriceSportActivity() {
                        @Override
                        public void onCall(SportActivity sportActivity, int position) {

                            sportActivities.set(position, sportActivity);
                            if (chooseSportActivityAdapter != null) {
                                chooseSportActivityAdapter.notifyDataSetChanged();
                            }

                        }
                    });
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
                    showSetSportSubPriceRulesDialog(sportSubActivity, subPosition, callBackPriceSportSubActivity);
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
                presenter.openCheckEquipments();
                break;
            case R.id.textViewSkip:
                presenter.openCheckEquipments();
                break;
        }
    }

    @Override
    public void setData(List<SportActivity> data) {
        sportActivities.clear();
        sportActivities.addAll(data);

        if (chooseSportActivityAdapter != null) {
            chooseSportActivityAdapter.notifyDataSetChanged();
        }

    }
}
