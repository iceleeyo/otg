package com.springtour.otg.application.exception;

import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;
import com.springtour.otg.domain.model.transaction.TransactionNo;

/**
 * 未找到商户异常
 * 
 * @author Hippoom
 * 
 */
public class UnavailableMerchantException extends AbstractCheckedApplicationExceptionWithStatusCode {
	private static final long serialVersionUID = 1L;

	public UnavailableMerchantException(String channelId, String orgId) {
		super(new StringBuilder("merchant not found:[channelId:").append(
				channelId).append(",").append("orgId:").append(orgId).append(
				"]").toString());
	}

	public UnavailableMerchantException(TransactionNo transactionNo) {
		super(new StringBuilder("merchant not found:[transactionNo:").append(
				transactionNo.getNumber()).append("]").toString());
	}

    @Override
    public String getStatusCode() {
        return StatusCode.UNAVAILABLE_MERCHANT;
    }

}
