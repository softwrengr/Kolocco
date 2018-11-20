package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 19/6/18.
 */

public class CreditCard {
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("last4")
    @Expose
    private String last4;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("maskedNumber")
    @Expose
    private String maskedNumber;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
