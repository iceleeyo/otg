package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;

public class NonExistedExternalTxnQueryResult implements
		ExternalTransactionQueryResult {

	@Override
	public Money amountFromSupplier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge chargedFromSupplier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extTxnNo() {
		// TODO Auto-generated method stub
		return null;
	}

	public static NonExistedExternalTxnQueryResult newInstance() {
		return new NonExistedExternalTxnQueryResult();
	}
}
