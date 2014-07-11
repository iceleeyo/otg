package com.springtour.otg.domain.model.transaction;

public class CardInfo {

    private String cardNo;
    private String expireDate;
    private String cvv2;
    private CardHolder cardHolder;

    public CardInfo(String cardNo, String expireDate, String cvv2,
            CardHolder cardHolder) {
        this.cardNo = cardNo;
        this.expireDate = expireDate;
        this.cvv2 = cvv2;
        this.cardHolder = cardHolder;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public String getCardHolderFullname() {
        return cardHolder.getFullname();
    }

    public String getCardHolderIdNo() {
        return cardHolder.getIdNo();
    }

    public String getCardHolderIdType() {
        return cardHolder.getIdType().getCode();
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }
}
