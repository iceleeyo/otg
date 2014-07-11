package com.springtour.otg.domain.model.transaction;

import com.springtour.otg.domain.shared.IdentityType;

public class CardInfoFixture {

    public final String cardNo = "1334567855";
    private final String expireDate="20130201";
    private final String cvv2="123";
    private CardHolder cardHolder = new CardHolder("zhouyugang", "3111111989101011512", IdentityType.IDENTITY);
    
	public CardInfo build(){
		CardInfo cardInfo = new CardInfo(cardNo, expireDate, cvv2, cardHolder);
		return cardInfo;
	}
}
