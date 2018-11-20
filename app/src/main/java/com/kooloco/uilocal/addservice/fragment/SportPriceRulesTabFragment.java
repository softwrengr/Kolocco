package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 27/11/17.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kooloco.R;
import com.kooloco.constant.Step;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.SportPriceRulesSport;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.SportPriceRulesPagerAdapter;
import com.kooloco.uilocal.addservice.presenter.SportPriceRulesTabPresenter;
import com.kooloco.uilocal.addservice.view.SportPriceRulesTabView;
import com.kooloco.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SportPriceRulesTabFragment extends BaseFragment<SportPriceRulesTabPresenter, SportPriceRulesTabView> implements SportPriceRulesTabView {

    @BindView(R.id.tabLayoutExpService)
    TabLayout tabLayoutExpService;
    @BindView(R.id.viewPagerSubService)
    ViewPager viewPagerSubService;

    SportPriceRulesPagerAdapter sportPriceRulesPagerAdapter;
    List<SportPriceRules> sportPriceRules;

    @BindView(R.id.textViewSkip)
    AppCompatTextView textViewSkip;

    @Inject
    Validator validator;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;

    private boolean isEdit = false;

    private boolean isLocalIncomplete = false;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_sport_price_rules_pager;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected SportPriceRulesTabView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        if (sportPriceRules == null) {
            sportPriceRules = new ArrayList<>();
            presenter.getData();
        }

        if (isEdit) {
            textViewSkip.setVisibility(View.INVISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_update));
        } else {
            textViewSkip.setVisibility(View.VISIBLE);
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
        }


        for (int i = 0; i < sportPriceRules.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null, false);
            TextView textViewTitle = (TextView) view.findViewById(R.id.customTextViewName);
            textViewTitle.setText(sportPriceRules.get(i).getName());
            tabLayoutExpService.addTab(tabLayoutExpService.newTab().setCustomView(view));
        }

        sportPriceRulesPagerAdapter = new SportPriceRulesPagerAdapter(getChildFragmentManager(), sportPriceRules, new SportPriceRulesPagerAdapter.CallBack() {
            @Override
            public void setData(int position, SportPriceRules sportPriceRule) {
                sportPriceRules.set(position, sportPriceRule);
            }
        });
        viewPagerSubService.setAdapter(sportPriceRulesPagerAdapter);

        viewPagerSubService.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutExpService));

        viewPagerSubService.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayoutExpService.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                try {
                    viewPagerSubService.setCurrentItem(tab.getPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @OnClick({R.id.imageViewBack, R.id.textViewSkip, R.id.buttonNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.textViewSkip:
                skipSignUpStep(Step.step8, new CallBackSignupStep() {
                    @Override
                    public void onSuccess() {
                        presenter.openChooseEquipment();
                    }
                });
                break;
            case R.id.buttonNext:

                boolean isValid = true;

                for (int i = 0; i < sportPriceRules.size(); i++) {
                    SportPriceRules sportPriceRule = sportPriceRules.get(i);

                    if (sportPriceRule.getPricePerHour().isEmpty()) {
                        showMessage(getActivity().getResources().getString(R.string.val_price_per_hour) + " " + sportPriceRule.getName().toLowerCase());
                        viewPagerSubService.setCurrentItem(i);
                        isValid = false;
                        break;
                    }

                    if (sportPriceRule.getMinDuration().isEmpty()) {
                        showMessage(getActivity().getResources().getString(R.string.val_min_duration) + " " + sportPriceRule.getName().toLowerCase());
                        viewPagerSubService.setCurrentItem(i);
                        isValid = false;
                        break;

                    }

                    if (sportPriceRule.getIsGroupBooking().equalsIgnoreCase("1")) {

                        if (sportPriceRule.getAddPersonPer().isEmpty()) {
                            showMessage(getActivity().getResources().getString(R.string.val_per_new_part) + " " + sportPriceRule.getName().toLowerCase());
                            viewPagerSubService.setCurrentItem(i);
                            isValid = false;
                            break;

                        }
                        if (sportPriceRule.getAddPerson().isEmpty()) {
                            showMessage(getActivity().getResources().getString(R.string.val_part) + " " + sportPriceRule.getName().toLowerCase());
                            viewPagerSubService.setCurrentItem(i);
                            isValid = false;
                            break;
                        }
                    }

                    if (validateSport(sportPriceRule.getSports())) {
                        showMessage(getActivity().getResources().getString(R.string.val_select_service_new) + " " + sportPriceRule.getName().toLowerCase());
                        viewPagerSubService.setCurrentItem(i);
                        isValid = false;
                        break;
                    }

                }

                if (isValid) {
                    presenter.callWs(sportPriceRules, isEdit, isLocalIncomplete);
                }

                break;
        }
    }


    @Override
    public void setData(List<SportPriceRules> data) {
        sportPriceRules.addAll(data);
        if (sportPriceRulesPagerAdapter != null) {
            if (sportPriceRules.size() != 0) {
                for (int i = 0; i < sportPriceRules.size(); i++) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null, false);
                    TextView textViewTitle = (TextView) view.findViewById(R.id.customTextViewName);
                    textViewTitle.setText(sportPriceRules.get(i).getName());
                    tabLayoutExpService.addTab(tabLayoutExpService.newTab().setCustomView(view));
                }
            }
            sportPriceRulesPagerAdapter.notifyDataSetChanged();
            viewPagerSubService.setCurrentItem(0);
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setIsLocalInComplete(boolean isIncompleteLocal) {
        this.isLocalIncomplete = isIncompleteLocal;
    }

    private boolean validateSport(List<SportPriceRulesSport> sports) {
        boolean isValid = true;

        for (SportPriceRulesSport sportPriceRulesSport : sports) {

            if (sportPriceRulesSport.getIsPrice().equalsIgnoreCase("1")) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

}
