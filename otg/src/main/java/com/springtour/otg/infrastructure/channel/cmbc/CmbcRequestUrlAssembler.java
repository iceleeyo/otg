package com.springtour.otg.infrastructure.channel.cmbc;

import java.util.*;

import lombok.*;

import com.springtour.otg.application.util.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.time.DateUtils;

public class CmbcRequestUrlAssembler {
	@Setter
	private Configurations configurations;
	@Setter
	private Encryption encryption;

	public String assembleUrl(Transaction transaction,String returnUrl) throws Exception {
		StringBuilder url = new StringBuilder(configurations.getCmbcRequestUrl());
		url.append("?");
		ArrayList<String> params = makeParams(transaction, returnUrl);
		
		AssembleRequestParamsForEncryption assembler = new AssembleRequestParamsForEncryption();
		String strToEncrypt = assembler.assemble(params);
		String reqStr = encryption.encrypt(strToEncrypt);
		
		url.append("reqStr=");
		url.append(reqStr);
		url.append("&customerId=");
		url.append(transaction.getMerchantCode());
		return url.toString();
	}

	private ArrayList<String> makeParams(Transaction transaction,
			String returnUrl) {
		ArrayList<String> params = new ArrayList<String>();
		params.add(transaction.getTransactionNo().getNumber());
		params.add(String.valueOf(transaction.getAmount().getAmount()));
		params.add(CmbcConstants.CNY);
		params.add(DateUtils.getDate(transaction.getWhenRequested()));
		params.add(DateUtils.getTime(transaction.getWhenRequested()));
		params.add(transaction.getMerchantCode());
		params.add(transaction.getMerchant().getName());
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.IS_REAL_TIME_SETTLE);
		params.add(returnUrl);
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.INSTALLMENT_PERIODS);
		params.add(CmbcConstants.TRADE_CREDIT);
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.EMPTY_STR);
		params.add(CmbcConstants.EMPTY_STR);
		return params;
	}
}
