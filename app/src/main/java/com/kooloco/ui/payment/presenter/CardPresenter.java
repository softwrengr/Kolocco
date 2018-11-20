package com.kooloco.ui.payment.presenter;
/**
 * Created by hlink on 14/6/18.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Card;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.ui.payment.view.CardView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


@PerActivity
public class CardPresenter extends BasePresenter<CardView> {

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    Session session;

    @Inject
    public CardPresenter() {
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

    public void getData() {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());

        koolocoRepository.getCreditCardList(map).subscribe(new SubscribeWithView<Response<List<Card>>>(view) {
            @Override
            public void onSuccess(Response<List<Card>> homeNewResponseResponse) {

                view.setCardData(homeNewResponseResponse.getData());

                view.hideLoader();

            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setCardData(new ArrayList<>());
                    }
                } else {
                    super.onError(e);
                }

                view.hideLoader();
            }
        });

    }

    public void callDelete(Card card, int pos) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("card_id", card.getCardId());

        koolocoRepository.deleteCreditCardList(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response homeNewResponseResponse) {

                view.deleteCard(pos);

                view.hideLoader();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }

    public void saveCardData(String holderName, String cardNumber, String selectDate, String cvv, boolean isBooking) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("cardholdername", holderName);
        map.put("cardno", cardNumber);
        map.put("exp_month", selectDate.substring(0, 2));
        map.put("exp_year", selectDate.substring(3));
        map.put("cvv", cvv);
        map.put("user_id", session.getUser().getId());

        koolocoRepository.addCreditCardList(map).subscribe(new SubscribeWithView<Response<List<Card>>>(view) {
            @Override
            public void onSuccess(Response<List<Card>> homeNewResponseResponse) {
                if (isBooking) {
                    view.hideLoader();
                    navigator.goBack();
                } else {
                    view.clearData();
                    view.setCardData(homeNewResponseResponse.getData());
                    view.hideLoader();
                }
            }

            @Override
            public void onError(Throwable e) {

                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.showMessage(e.getMessage());
                        //view.setCardData(new ArrayList<>());
                    }
                } else {
                    super.onError(e);
                }

                view.hideLoader();
            }
        });
    }
}