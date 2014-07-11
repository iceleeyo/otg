package com.springtour.otg.application;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.partner.RecommendedGateway;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.Money;
import java.util.List;

/**
 * 交易服务
 * 
 * @author Hippoom
 * 
 */
public interface TransactingService {

    /**
     * 查找该组织在该支付渠道注册的商户
     * 
     * @deprecated
     * @param channelId
     * @param orgId
     * @return
     * @throws NoSuchMerchantException
     */
    Merchant findMerchant(String channelId, String orgId)
            throws UnavailableMerchantException;

    /**
     * 生成一笔交易待请求
     * @param partnerId
     * @param channelId
     * @param gateway
     * @param orgId
     * @param amount
     * @param orderId
     * @return
     * @throws UnavailableChannelException
     * @throws UnavailableCurrencyException
     * @throws UnavailableMerchantException
     * @throws UnavailablePartnerException
     * @throws UnavailablePaymentApplicationException 
     */
    Transaction newTransaction(String partnerId, String channelId, String gateway, String orgId,
            Money amount, OrderIdentity orderId)
            throws UnavailableChannelException, UnavailableCurrencyException, UnavailableGatewayException, UnavailableMerchantException,
            UnavailablePartnerException, UnavailablePaymentApplicationException;
    
    /**
     * 生成一笔交易待请求
     * @param partnerId
     * @param channelId
     * @param gateway
     * @param orgId
     * @param amount
     * @param orderId
     * @param chargeFor
     * @return
     * @throws UnavailableChannelException
     * @throws UnavailableCurrencyException
     * @throws UnavailableMerchantException
     * @throws UnavailablePartnerException
     * @throws UnavailablePaymentApplicationException 
     */
    Transaction newTransactionWithChargeFor(String partnerId, String channelId, String gateway, String orgId,
            Money amount, OrderIdentity orderId, String chargeFor)
            throws UnavailableChannelException, UnavailableCurrencyException, UnavailableGatewayException, UnavailableMerchantException,
            UnavailablePartnerException, UnavailablePaymentApplicationException;

    /**
     * 根据原交易生成一笔新的撤销交易
     */
	Transaction cancelTransaction(String oldTransactionNumber);
	
    /**
     * 根据交易流水号查找交易，并根据isCharged将变更其状态为已响应_扣款成功/已响应_扣款失败
     * @param notificationSeq
     * @return
     * @throws DuplicateResponseException
     * @throws IllegalAmountException 
     */
    Transaction handle(String notificationSeq) throws DuplicateResponseException, IllegalAmountException;

    /**
     * 完成交易
     * </p>
     * 
     * 在业务系统完成支付后，使用此API根据交易流水号查找交易，若状态为已响应_扣款成功，并变更其状态为已完成
     * 
     * @param transactionNo
     */
    Transaction conclude(TransactionNo transactionNo);


}
