package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 3/10/17.
 */

import com.kooloco.constant.EventBusAction;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.uilocal.addservice.view.ChooseLanguagesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

@PerActivity
public class ChooseLanguagesPresenter extends BasePresenter<ChooseLanguagesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public ChooseLanguagesPresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openSportSelect() {
        navigator.openChooseSportActivityNewView().replace(true);
    }


    public void openSetLocation() {
        navigator.openSetLocationView().replace(true);
    }

    public void getLanguages() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.language(map).subscribe(new SubscribeWithView<Response<LanguageResponse>>(view) {
            @Override
            public void onSuccess(Response<LanguageResponse> languageResponseResponse) {
                view.hideLoader();

                view.setLanguage(languageResponseResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWs(final List<Language> appLanguages, final List<Language> speakLanguages, final boolean isEdit) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("app_lang_id", getKey(appLanguages));

        //map.put("speak_lang_id", getKey(speakLanguages));

        koolocoRepository.localLanguage(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> userResponse) {

                view.changeAppLanguage(getKeyValue(appLanguages));

                if (isEdit) {
                    EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);
                }


                view.hideLoader();
                if (isEdit) {
                    navigator.goBack();
                } else {
                    openSetLocation();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }


    private String getKey(List<Language> languages) {
        String lngId = "";

        for (Language language : languages) {
            if (language.isSelect()) {
                if (lngId.isEmpty()) {
                    lngId = lngId + language.getId();
                } else {
                    lngId = lngId + "," + language.getId();
                }
            }
        }

        return lngId;
    }

    private String getKeyValue(List<Language> languages) {
        String lngName = "";

        for (Language language : languages) {
            if (language.isSelect()) {
                lngName = language.getName();
            }
        }

        return lngName;
    }
}
