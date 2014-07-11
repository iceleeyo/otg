package com.springtour.otg.infrastructure.channel.alipay;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.springtour.otg.domain.model.transaction.Transaction;

public interface AlipayHandlingService {

    // boolean isTransactionCharged(AlipaySynchronizingStatus
    // synchronizingStatus);
    String confirmationFlag();

    String payConvert(Transaction transaction, String key, String notifyUrl, String returnUrl)
            throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
