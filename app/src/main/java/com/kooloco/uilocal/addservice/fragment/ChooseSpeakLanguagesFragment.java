package com.kooloco.uilocal.addservice.fragment;
/**
 * Created by hlink44 on 3/10/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.addservice.adapter.ChooseLanguageNewAdapter;
import com.kooloco.uilocal.addservice.presenter.ChooseSpeakLanguagesPresenter;
import com.kooloco.uilocal.addservice.view.ChooseSpeakLanguagesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSpeakLanguagesFragment extends BaseFragment<ChooseSpeakLanguagesPresenter, ChooseSpeakLanguagesView> implements ChooseSpeakLanguagesView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chooseLanguageSelectLocalLanguage)
    AppCompatEditText chooseLanguageSelectLocalLanguage;
    @BindView(R.id.recyclerLanguage)
    RecyclerView recyclerLanguage;
    @BindView(R.id.linearLayoutLocalSpeakLanguage)
    LinearLayout linearLayoutLocalSpeakLanguage;

    private boolean isEdit = false;

    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;

    List<Language> speakLanguages;

    @Override
    protected int createLayout() {
        return R.layout.fragment_choose_languages_speak;
    }

    ChooseLanguageNewAdapter chooseLanguageSpeakNewAdapter;

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseSpeakLanguagesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        buttonNext.setText(getActivity().getResources().getString(R.string.button_done));

        if (speakLanguages == null) {

            speakLanguages = new ArrayList<>();

            presenter.getLanguages();
        }

        setAdapter();
    }

    @OnClick({R.id.imageViewBack, R.id.chooseLanguageSelectLocalLanguage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.chooseLanguageSelectLocalLanguage:
                chooseLanguageSelectLocalLanguage.setSelected(!chooseLanguageSelectLocalLanguage.isSelected());
                linearLayoutLocalSpeakLanguage.setVisibility(chooseLanguageSelectLocalLanguage.isSelected() ? View.VISIBLE : View.GONE);
                break;
        }
    }


    @OnClick(R.id.buttonNext)
    public void onViewClicked() {

        if (isSelectLanguageApp(speakLanguages)) {
            showMessage(getString(R.string.val_during_lang_speak));
            return;
        }

        presenter.callWs(speakLanguages, isEdit);
    }

    private boolean isSelectLanguageApp(List<Language> languages) {

        for (Language language : languages) {
            if (language.isSelect()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setLanguage(LanguageResponse data) {


        speakLanguages.addAll(data.getSpeakingLanguages());

        if (chooseLanguageSpeakNewAdapter != null) {
            chooseLanguageSpeakNewAdapter.notifyDataSetChanged();
        }
        setSpeakLanguage();
    }

    private void setAdapter() {

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
            }
        });
        recyclerLanguage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerLanguage.setAdapter(chooseLanguageSpeakNewAdapter);

        chooseLanguageSelectLocalLanguage.setSelected(true);
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
}
