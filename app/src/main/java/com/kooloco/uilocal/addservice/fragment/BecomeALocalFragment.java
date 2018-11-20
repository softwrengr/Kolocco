package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 24/8/17.
 */


import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Currency;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.CurrencySelectAdapter;
import com.kooloco.uilocal.addservice.adapter.ChooseLanguageNewAdapter;
import com.kooloco.uilocal.addservice.presenter.BecomeALocalPresenter;
import com.kooloco.uilocal.addservice.view.BecomeALocalView;
import com.kooloco.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class BecomeALocalFragment extends BaseFragment<BecomeALocalPresenter, BecomeALocalView> implements BecomeALocalView {


    @BindView(R.id.becomALocalEditTextName)
    AppCompatEditText becomALocalEditTextName;
    @BindView(R.id.becomALocalEditTextAdress)
    AppCompatEditText becomALocalEditTextAdress;
    @BindView(R.id.becomALocalEditTextTelphone)
    AppCompatEditText becomALocalEditTextTelphone;
    @BindView(R.id.becomeALocalIntroduce)
    AppCompatEditText becomeALocalIntroduce;
    @BindView(R.id.signupTermsAndConditions)

    AppCompatCheckBox signupTermsAndConditions;
    @BindView(R.id.chooseLanguageSelectLocalLanguage)
    AppCompatEditText chooseLanguageSelectLocalLanguage;
    @BindView(R.id.recyclerLanguage)
    RecyclerView recyclerLanguage;
    @BindView(R.id.linearLayoutLocalSpeakLanguage)
    LinearLayout linearLayoutLocalSpeakLanguage;

    @Inject
    Validator validator;

    @Inject
    Session session;

    List<Language> speakLanguages;
    @BindView(R.id.chooseCurrency)
    AppCompatEditText chooseCurrency;
    @BindView(R.id.recyclerCurrency)
    RecyclerView recyclerCurrency;
    @BindView(R.id.linearLayoutCurrency)
    LinearLayout linearLayoutCurrency;
    @BindView(R.id.editTextCountryCode)
    AppCompatEditText editTextCountryCode;

    private ChooseLanguageNewAdapter chooseLanguageSpeakNewAdapter;

    List<Currency> currency;

    CurrencySelectAdapter currencyAdapter;
    public Currency selectCurrency;

    @Override
    protected int createLayout() {
        return R.layout.fragment_become_a_local;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected BecomeALocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        becomALocalEditTextName.setText(session.getUser().getFirstname() + " " + session.getUser().getLastname());
        signupTermsAndConditions.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));

        if (speakLanguages == null) {

            speakLanguages = new ArrayList<>();

            presenter.getLanguages();
        }

        // chooseLanguageSelectLocalLanguage.setSelected(true);

        setAdapter();


    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }


    @OnClick(R.id.buttonNext)
    public void onViewClickedNext() {
        try {
            validator.submit(becomALocalEditTextName).checkEmpty().errorMessage(R.string.val_enter_name).check();
            //  validator.submit(becomALocalEditTextAdress).checkEmpty().errorMessage(R.string.val_enter_address).check();
            validator.submit(becomALocalEditTextTelphone).checkEmpty().errorMessage(R.string.cal_enter_tel_mob).check();

            if (isSelectLanguageApp(speakLanguages)) {
                showMessage(getString(R.string.val_during_lang_speak));
                return;
            }

            validator.submit(chooseCurrency).checkEmpty().errorMessage(R.string.val_select_currency).check();

            if (!signupTermsAndConditions.isChecked()) {
                ApplicationException applicationException = new ApplicationException(getString(R.string.val_agress_terms_conditions));
                throw applicationException;
            }


            presenter.callWs(becomALocalEditTextAdress.getText().toString().trim(),editTextCountryCode.getText().toString().trim(),becomALocalEditTextTelphone.getText().toString().trim(), becomeALocalIntroduce.getText().toString().trim(), speakLanguages, selectCurrency.getId());

        } catch (ApplicationException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

    @OnClick(R.id.chooseLanguageSelectLocalLanguage)
    public void onViewClickedSelectLanguage() {
        chooseLanguageSelectLocalLanguage.setSelected(!chooseLanguageSelectLocalLanguage.isSelected());
        linearLayoutLocalSpeakLanguage.setVisibility(chooseLanguageSelectLocalLanguage.isSelected() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.chooseCurrency)
    public void onViewClickedCurrency() {
        chooseCurrency.setSelected(!chooseCurrency.isSelected());
        linearLayoutCurrency.setVisibility(chooseCurrency.isSelected() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setLanguage(LanguageResponse data) {


        speakLanguages.addAll(data.getSpeakingLanguages());

        if (chooseLanguageSpeakNewAdapter != null) {
            chooseLanguageSpeakNewAdapter.notifyDataSetChanged();
        }
        setSpeakLanguage();
    }

    @Override
    public void setCurrencyData(List<Currency> data) {
        currency.clear();
        currency.addAll(data);
        if (currencyAdapter != null) {
            currencyAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapter() {

        if (currency == null) {
            currency = new ArrayList<>();
        }
        recyclerCurrency.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        currencyAdapter = new CurrencySelectAdapter(getActivity(), currency, true, new CurrencySelectAdapter.CallBack() {

            @Override
            public void onClickItem(Currency currency) {
                selectCurrency = currency;

                chooseCurrency.setText(currency.getCurrency());

                chooseCurrency.setSelected(!chooseCurrency.isSelected());
                linearLayoutCurrency.setVisibility(chooseCurrency.isSelected() ? View.VISIBLE : View.GONE);

            }

        });
        recyclerCurrency.setAdapter(currencyAdapter);

        chooseLanguageSpeakNewAdapter = new ChooseLanguageNewAdapter(getActivity(), speakLanguages, false, new ChooseLanguageNewAdapter.CallBack() {
            @Override
            public void onClickItem() {
                String lan = "";
                for (Language language : speakLanguages) {
                    if (language.isSelect()) {
                        String s = language.getName();
                        if (lan.isEmpty()) {
                            lan = lan + s;
                        } else {
                            lan = lan + ", " + s;
                        }
                    }
                }
                chooseLanguageSelectLocalLanguage.setText(lan);
     /*           chooseLanguageSelectLocalLanguage.setSelected(!chooseLanguageSelectLocalLanguage.isSelected());
                linearLayoutLocalSpeakLanguage.setVisibility(chooseLanguageSelectLocalLanguage.isSelected() ? View.VISIBLE : View.GONE);*/
            }
        });
        recyclerLanguage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerLanguage.setAdapter(chooseLanguageSpeakNewAdapter);

        // chooseLanguageSelectLocalLanguage.setSelected(true);
    }


    private void setSpeakLanguage() {
        String lan = "";
        for (Language language : speakLanguages) {
            if (language.isSelect()) {
                String s = language.getName();
                if (lan.isEmpty()) {
                    lan = lan + s;
                } else {
                    lan = lan + ", " + s;
                }
            }
        }
        chooseLanguageSelectLocalLanguage.setText(lan);
    }


    private boolean isSelectLanguageApp(List<Language> languages) {

        for (Language language : languages) {
            if (language.isSelect()) {
                return false;
            }
        }

        return true;
    }

    @OnClick(R.id.editTextCountryCode)
    public void onClick() {
        showDialogCountryCode(text -> {
            if (editTextCountryCode!=null){
                editTextCountryCode.setText(text);
            }
        });


    }
}
