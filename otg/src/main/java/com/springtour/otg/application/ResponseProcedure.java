package com.springtour.otg.application;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;

import com.springtour.otg.application.exception.DuplicateResponseException;
import com.springtour.otg.application.exception.IllegalAmountException;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.service.MakePaymentService;

/**
 * 响应流程
 * <p>
 * 处理支付平台返回响应结果的流程
 * 
 * </p>
 * 
 * @see com.springtour.otg.application.TransactingService
 * 
 * @version 1.0
 * @author jker
 * @since 2011.04.15
 */
public class ResponseProcedure {

	@Setter
	private TransactingService transactingService;
	@Setter
	private TransactionRepository transactionRepository;
	@Setter
	private MakePaymentService makePaymentService;

	public void handle(String notificationSeq)
			throws DuplicateResponseException, IllegalAmountException,
			UnavailablePaymentApplicationException, CannotMakePaymentException {
		Transaction transaction = transactingService.handle(notificationSeq);
		// 只有响应为Success的时候才向业务系统发起支付请求
		if (transaction.isResponsedSuccess()) {
			// 收到通知自动付款时，如果付款失败则发送邮件至运营商
			makePaymentThenConclude(transaction);// 如果是重复的通知之前会抛出DuplicateResponseException不会执行到这里				
		}
	}

	/**
	 * 当交易状态为响应成功(此时业务系统支付不成功,为掉单)，模拟应答，手工恢复
	 * 
	 * @param transactionNo
	 * @return
	 */
	public void retryPaymentMaking(TransactionNo transactionNo)
			throws UnavailablePaymentApplicationException,
			CannotMakePaymentException {
		Transaction transaction = transactionRepository.find(transactionNo);
		if (transaction.isResponsedSuccess()) {
			makePaymentThenConclude(transaction);
		} else {
			throw new IllegalStateException(transaction.toString()
					+ ",attempt=" + Transaction.State.CONCLUDED);
		}
	}

	/**
	 * 调用支付，并根据支付结果完成交易
	 * 
	 * @param transaction
	 * @param synchronizingMethod
	 * @return
	 */
	private void makePaymentThenConclude(Transaction transaction)
			throws UnavailablePaymentApplicationException,
			CannotMakePaymentException {

		makePaymentService.makePayment(transaction);// 如果付款失败，会抛出CannotMakePaymentException
		transactingService.conclude(transaction.getTransactionNo());
	}
}
