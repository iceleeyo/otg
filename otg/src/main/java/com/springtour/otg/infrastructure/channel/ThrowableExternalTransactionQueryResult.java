package com.springtour.otg.infrastructure.channel;

import lombok.*;

import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.shared.*;

@ToString
public class ThrowableExternalTransactionQueryResult implements
		ExternalTransactionQueryResult {

	private Charge chargedFromSupplier;
	private Money amountFromSupplier;
	private String extTxnNo;

	public ThrowableExternalTransactionQueryResult(Charge chargedFromSupplier,
			Money amountFromSupplier, String extTxnNo) {
		this.chargedFromSupplier = chargedFromSupplier;
		this.amountFromSupplier = amountFromSupplier;
		this.extTxnNo = extTxnNo;
	}

	@Override
	public Charge chargedFromSupplier() {
		return chargedFromSupplier;
	}

	@Override
	public Money amountFromSupplier() {
		return amountFromSupplier;
	}

	@Override
	public String extTxnNo() {
		return extTxnNo;
	}

}
