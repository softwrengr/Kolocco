package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink44 on 22/9/17.
 */

public class Card {

    @SerializedName("card_id")
    @Expose
    private String cardId;


    @SerializedName("card_logo")
    @Expose
    private String image = "";

    @SerializedName("last4")
    @Expose
    private String cardNumber = "";

    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("maskedNumber")
    @Expose
    private String maskedNumber;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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
}
