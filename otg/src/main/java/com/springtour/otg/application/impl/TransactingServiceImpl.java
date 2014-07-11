package com.springtour.otg.application.impl;


import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.springtour.otg.application.exception.*;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.Clock;

/**
 * 
 * @author jker
 * 
 *         2011-5-5
 */
public class TransactingServiceImpl implements TransactingService {

	private TransactionFactory transactionFactory;
	private TransactionRepository transactionRepository;
	private MerchantRepository merchantRepository;
	private ChannelRepository channelRepository;
	private NotificationRepository notificationRepository;
	private Clock clock;

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}

	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	public void setTransactionRepository(
			TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void setChannelRepository(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	@Override
	@Transactional
	public Transaction newTransaction(String partnerId, String channelId,
			String gateway, String orgId, Money amount, OrderIdentity orderId)
			throws UnavailableChannelException, UnavailableCurrencyException,
			UnavailableMerchantException, UnavailablePartnerException,
			UnavailablePaymentApplicationException, UnavailableGatewayException {
		return newTransactionWithChargeFor(partnerId,
				channelId, gateway, orgId, amount, orderId, Constants.EMPTY);
	}
	
	@Override
	public Transaction newTransactionWithChargeFor(String partnerId,
			String channelId, String gateway, String orgId, Money amount,
			OrderIdentity orderId, String chargeFor)
			throws UnavailableChannelException, UnavailableCurrencyException,
			UnavailableGatewayException, UnavailableMerchantException,
			UnavailablePartnerException, UnavailablePaymentApplicationException {
		Transaction transaction = transactionFactory.makeTransaction(partnerId,
				channelId, gateway, orgId, amount, orderId, chargeFor);
		transactionRepository.store(transaction);
		return transaction;
	}

	@Override
	@Transactional
	public Transaction cancelTransaction(String oldTransactionNumber) {
		// 如果已经存在撤销交易则返回该交易，不存在则新建交易
		//get version
		Transaction oldTransaction = transactionRepository
				.find(new TransactionNo(oldTransactionNumber));
		
		List<Transaction> correlatedCancelTransactions = transactionRepository
				.findBy(transactionFactory
						.correlatedCancelSpec(oldTransactionNumber));
		Transaction transaction = null;
		if (correlatedCancelTransactions.size() == 0) {
			transaction = transactionFactory
					.cancelTransactionBy(oldTransaction);
			//read commited
			transactionRepository.store(oldTransaction);
			transactionRepository.store(transaction);
		} else {
			return correlatedCancelTransactions.get(0);
		}

		return transaction;
	}

	@Override
	@Transactional
	public Transaction handle(String notificationSeq)
			throws IllegalAmountException, DuplicateResponseException {
		Notification notification = notificationRepository
				.find(notificationSeq);
		if (notification == null) {
			throw new CannotReconsitituteNotificationException(notificationSeq);
		}
		Transaction transaction = transactionRepository.find(notification
				.getTxnNo());
		transaction.handle(notification, clock.now());
		transactionRepository.store(transaction);
		return transaction;
	}

	@Override
	@Transactional
	public Transaction conclude(TransactionNo transactionNo) {
		Transaction transaction = transactionRepository.find(transactionNo);
		transaction.conclude(clock.now());
		transactionRepository.store(transaction);
		return transaction;
	}

	@Override
	public Merchant findMerchant(String channelId, String orgId)
			throws UnavailableMerchantException {
		// 获取商户
		Merchant merchant = merchantRepository.find(channelId, orgId);
		if (!isMerchantValid(merchant)) {
			throw new UnavailableMerchantException(channelId, orgId);
		}
		return merchant;
	}

	private boolean isMerchantValid(Merchant merchant) {
		if (merchant != null && merchant.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	
}
