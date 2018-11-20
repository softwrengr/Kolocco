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
import com.kooloco.uilocal.addservice.presenter.ChooseLanguagesPresenter;
import com.kooloco.uilocal.addservice.view.ChooseLanguagesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseLanguagesFragment extends BaseFragment<ChooseLanguagesPresenter, ChooseLanguagesView> implements ChooseLanguagesView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chooseLanguageSelectAppLanguage)
    AppCompatEditText chooseLanguageSelectAppLanguage;
    @BindView(R.id.recyclerAppLanguage)
    RecyclerView recyclerAppLanguage;
    @BindView(R.id.linearLayoutLocalAppLanguage)
    LinearLayout linearLayoutLocalAppLanguage;
    @BindView(R.id.chooseLanguageSelectLocalLanguage)
    AppCompatEditText chooseLanguageSelectLocalLanguage;
    @BindView(R.id.recyclerLanguage)
    RecyclerView recyclerLanguage;
    @BindView(R.id.linearLayoutLocalSpeakLanguage)
    LinearLayout linearLayoutLocalSpeakLanguage;
    private boolean isEdit = false;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;

    List<Language> appLanguages;
    List<Language> speakLanguages;

    @Override
    protected int createLayout() {
        return R.layout.fragment_choose_languages;
    }

    ChooseLanguageNewAdapter chooseLanguageAppNewAdapter;
    ChooseLanguageNewAdapter chooseLanguageSpeakNewAdapter;

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ChooseLanguagesView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        if (isEdit) {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_done));
        } else {
            buttonNext.setText(getActivity().getResources().getString(R.string.button_next));
        }

        if (appLanguages == null || speakLanguages == null) {

            appLanguages = new ArrayList<>();
            speakLanguages = new ArrayList<>();

            presenter.getLanguages();
        }

        setAdapter();
    }

    @OnClick({R.id.imageViewBack, R.id.chooseLanguageSelectAppLanguage, R.id.chooseLanguageSelectLocalLanguage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.chooseLanguageSelectAppLanguage:
                chooseLanguageSelectAppLanguage.setSelected(!chooseLanguageSelectAppLanguage.isSelected());
                linearLayoutLocalAppLanguage.setVisibility(chooseLanguageSelectAppLanguage.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.chooseLanguageSelectLocalLanguage:
                chooseLanguageSelectLocalLanguage.setSelected(!chooseLanguageSelectLocalLanguage.isSelected());
                linearLayoutLocalSpeakLanguage.setVisibility(chooseLanguageSelectLocalLanguage.isSelected() ? View.VISIBLE : View.GONE);
                break;
        }
    }


    @OnClick(R.id.buttonNext)
    public void onViewClicked() {

        if (isSelectLanguageApp(appLanguages)) {
            showMessage(getString(R.string.val_app_language));
            return;
        }

      /*  if (isSelectLanguageApp(speakLanguages)) {
            showMessage(getString(R.string.val_during_lang_speak));
            return;
        }*/

        presenter.callWs(appLanguages,speakLanguages,isEdit);
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

        appLanguages.addAll(data.getAppLanguage());

        speakLanguages.addAll(data.getSpeakingLanguages());

        if (chooseLanguageAppNewAdapter != null) {
            chooseLanguageAppNewAdapter.notifyDataSetChanged();
        }
        if (chooseLanguageSpeakNewAdapter != null) {
            chooseLanguageSpeakNewAdapter.notifyDataSetChanged();
        }
        setAppLanguage();
        setSpeakLanguage();
    }

    private void setAdapter() {
        recyclerAppLanguage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        chooseLanguageAppNewAdapter = new ChooseLanguageNewAdapter(getActivity(), appLanguages, true, new ChooseLanguageNewAdapter.CallBack() {
            @Override
            public void onClickItem() {
                String lan = "";

                for (Language language : appLanguages) {
                    if (language.isSelect()) {
                        String s = language.getName();
                        if (lan.isEmpty()) {
                            lan = lan + s;
                        } else {
                            lan = lan + ", " + s;
                        }
                    }
                }

                chooseLanguageSelectAppLanguage.setText(lan);

               /* chooseLanguageSelectAppLanguage.setSelected(!chooseLanguageSelectAppLanguage.isSelected());
                linearLayoutLocalAppLanguage.setVisibility(chooseLanguageSelectAppLanguage.isSelected() ? View.VISIBLE : View.GONE);*/

            }
        });
        recyclerAppLanguage.setAdapter(chooseLanguageAppNewAdapter);

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
        chooseLanguageSelectAppLanguage.setSelected(true);
    }

    private void setAppLanguage(){
        String lan = "";

        for (Language language : appLanguages) {
            if (language.isSelect()) {
                String s = language.getName();
                if (lan.isEmpty()) {
                    lan = lan + s;
                } else {
                    lan = lan + ", " + s;
                }
            }
        }

        chooseLanguageSelectAppLanguage.setText(lan);
    }

    private void setSpeakLanguage(){
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
