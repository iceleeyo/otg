package com.springtour.otg.domain.model.transaction;

import com.springtour.otg.domain.shared.IdentityType;

public class CardHolder {

    private String fullname;
    private String idNo;
    private IdentityType idType;

    public CardHolder(String cardHolderFullname, String cardHolderIdNo,
            IdentityType cardHolderIdType) {
        this.fullname = cardHolderFullname;
        this.idNo = cardHolderIdNo;
        this.idType = cardHolderIdType;
    }

    public CardHolder(String cardHolderFullname, String cardHolderIdNo,
            String cardHolderIdType) {
        this.fullname = cardHolderFullname;
        this.idNo = cardHolderIdNo;
        this.idType = IdentityType.of(cardHolderIdType);
    }

    public String getFullname() {
        return fullname;
    }

    public String getIdNo() {
        return idNo;
    }

    public IdentityType getIdType() {
        return idType;
    }
}
