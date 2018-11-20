package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 4/10/17.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportSubActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.ChooseSportActivityAdapter;
import com.kooloco.uilocal.addservice.presenter.ChooseSportActivitiesPresenter;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChooseSportActivitiesFragment extends BaseFragment<ChooseSportActivitiesPresenter, ChooseSportActivitiesView> implements ChooseSportActivitiesView {


    @BindView(R.id.recyclerServiceActivities)
    RecyclerView recyclerServiceActivities;
    @BindView(R.id.customTextViewNote)
    AppCompatTextView customTextViewNote;
    ChooseSportActivityAdapter chooseSportActivityAdapter;
    List<SportActivity> sportActivities;

    Dialog dialog = null;

    @Override
    protected int createLayout() {
        return R.layout.fragment_choose_sport_activities;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseSportActivitiesView createView() {
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
                presenter.callWs(sportActivities);
/*
                presenter.openChooseEquipmentsView();
*/
                break;
            case R.id.textViewSkip:
                presenter.openChooseSportView();
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

    public void showSubDialog(final SportActivity sportActivity, final SportSubActivity sportSubActivity, final int position, final int subPosition) {


        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_price_rules, null, false);

        final CheckBox checkBoxSelectRules;
        final AppCompatEditText editTextValuePer;
        final AppCompatEditText editTextValuePart;
        final AppCompatEditText editTextValuePrice;

        checkBoxSelectRules = view.findViewById(R.id.checkBoxSelectRules);
        editTextValuePer = view.findViewById(R.id.editTextValuePer);
        editTextValuePart = view.findViewById(R.id.editTextValuePart);
        editTextValuePrice = view.findViewById(R.id.editTextValuePrice);

        AppCompatButton buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                sportSubActivity.setGroup(checkBoxSelectRules.isChecked());
                sportSubActivity.setPrice(editTextValuePrice.getText().toString());

                List<SportSubActivity> sportSubActivities = sportActivity.getSportSubActivities();
                sportSubActivities.set(subPosition, sportSubActivity);

                sportActivity.setSportSubActivities(sportSubActivities);

                sportActivity.setOpen(true);
                sportSubActivities.set(position, sportSubActivity);

              /*  if (chooseSportActivityAdapter != null) {
                    chooseSportActivityAdapter.notifyDataSetChanged();
                }*/
            }
        });


        editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
        editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

        editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
        editTextValuePer.setSelected(checkBoxSelectRules.isChecked());


        checkBoxSelectRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextValuePer.setEnabled(checkBoxSelectRules.isChecked());
                editTextValuePart.setEnabled(checkBoxSelectRules.isChecked());

                editTextValuePart.setSelected(checkBoxSelectRules.isChecked());
                editTextValuePer.setSelected(checkBoxSelectRules.isChecked());

                editTextValuePart.setText(!checkBoxSelectRules.isChecked() ? "" : "");
                editTextValuePer.setText(!checkBoxSelectRules.isChecked() ? "" : "");
            }
        });

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        dialog.setContentView(view);

        dialog.show();
    }
}
