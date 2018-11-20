package com.kooloco.uilocal.addservice.presenter;
/**
 * Created by hlink on 22/5/18.
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
import com.kooloco.uilocal.addservice.view.ChooseSpeakLanguagesView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


@PerActivity
public class ChooseSpeakLanguagesPresenter extends BasePresenter<ChooseSpeakLanguagesView> {
    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public ChooseSpeakLanguagesPresenter() {
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

    public void callWs( final List<Language> speakLanguages, final boolean isEdit) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();

        map.put("user_id", session.getUser().getId());
        map.put("speak_lang_id", getKey(speakLanguages));

        koolocoRepository.setSPeakLanguage(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response userResponse) {

                view.hideLoader();

                navigator.goBack();

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

}