package com.kooloco.uilocal.addservice.presenter;/**
 * Created by hlink44 on 24/8/17.
 */


import javax.inject.Inject;


import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Currency;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.model.Response;
import com.kooloco.model.User;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.addservice.view.BecomeALocalView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class BecomeALocalPresenter extends BasePresenter<BecomeALocalView> {
    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    public BecomeALocalPresenter() {

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

        kooocoRepository.language(map).subscribe(new SubscribeWithView<Response<LanguageResponse>>(view) {
            @Override
            public void onSuccess(Response<LanguageResponse> languageResponseResponse) {
                //   view.hideLoader();

                view.setLanguage(languageResponseResponse.getData());
                getDataCurrency();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void getDataCurrency() {
        //   view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("role", "L");

        kooocoRepository.getCurrency(map).subscribe(new SubscribeWithView<Response<List<Currency>>>(view) {
            @Override
            public void onSuccess(Response<List<Currency>> languageResponseResponse) {
                view.hideLoader();
                view.setCurrencyData(languageResponseResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWs(String address, String countryCode, String phone, String intro, List<Language> speakLanguages, String currencyId) {
        view.showLoader();

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("country_code", "" + countryCode);
        map.put("phone", "" + phone);
        map.put("intro_your_self", intro);
        map.put("speak_lang_id", getKey(speakLanguages));
        map.put("currency_id_l", currencyId);

        kooocoRepository.localStepOne(map).subscribe(new SubscribeWithView<Response<User>>(view) {
            @Override
            public void onSuccess(Response<User> response) {
                view.hideLoader();
                //view.showMessage(response.getMessage());
                BaseActivity.currency = session.getCurrency();

                openAddImage();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void openAddImage() {
        navigator.openAddImageView().replace(true);
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
