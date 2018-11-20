package com.kooloco.ui.payment.view;

import com.kooloco.model.Card;
import com.kooloco.ui.base.RootView;

import java.util.List;

/**
 * Created by hlink on 14/6/18.
 */

public interface CardView extends RootView {

    void setCardData(List<Card> data);

    void deleteCard(int pos);

    void clearData();

    void setIsBooking(boolean isBooking);

}