package com.springtour.otg.domain.service;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;
import com.springtour.otg.domain.model.transaction.Transaction;

/**
 * <p>
 * 支付服务
 * </p>
 * <p>
 * 业务系统完成支付动作,生成一笔支付
 * </p>
 * 
 */
public interface MakePaymentService {

    /**
     * 完成支付,生产一笔支付记录
     * </p>
     * 
     *
     * @return 0 成功
     */
    void makePayment(Transaction transaction) throws UnavailablePaymentApplicationException, CannotMakePaymentException;

    boolean support(String application);
}
