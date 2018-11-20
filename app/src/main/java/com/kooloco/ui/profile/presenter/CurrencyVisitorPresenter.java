package com.kooloco.ui.profile.presenter;
/**
 * Created by hlink on 29/6/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.Currency;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.profile.view.CurrencyVisitorView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.realm.Realm;


@PerActivity
public class CurrencyVisitorPresenter extends BasePresenter<CurrencyVisitorView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CurrencyVisitorPresenter() {
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

    public void getData(boolean isVisitor) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("role", isVisitor ? "V" : "L");

        koolocoRepository.getCurrency(map).subscribe(new SubscribeWithView<Response<List<Currency>>>(view) {
            @Override
            public void onSuccess(Response<List<Currency>> languageResponseResponse) {
                view.hideLoader();
                view.setData(languageResponseResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callWs(Currency selectCurrency, boolean isVisitor) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("role", isVisitor ? "V" : "L");
        map.put("currency_id", selectCurrency.getId());

        koolocoRepository.setCurrency(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response languageResponseResponse) {
                view.hideLoader();
//                navigator.goBack();

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();

                navigator.startHomeActivity().byFinishingAll().start();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}