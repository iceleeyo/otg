package com.springtour.otg.domain.model.transaction;

import java.math.BigDecimal;

import lombok.Setter;
import com.springtour.otg.application.exception.CannotCancelTransactionException;
import com.springtour.otg.application.exception.UnavailablePartnerException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.exception.UnavailableGatewayException;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;
import com.springtour.otg.application.exception.UnavailableMerchantException;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.service.MakePaymentService;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.Clock;

/**
 * 默认交易工厂实现
 * 
 * @author Hippoom
 * 
 */
public class TransactionFactory {

	public TransactionNo nextTransactionNo(String channelId, Merchant merchant) {
		return transactionNoGenerator.nextTransactionNo(channelId, merchant,
				transactionRepository.nextTransactionNoSequence(), clock.now());
	}

	public Transaction makeTransaction(String partnerId, String channelId,
			String gateway, String orgId, Money amount, OrderIdentity orderId)
			throws UnavailablePartnerException, UnavailableChannelException,
			UnavailableCurrencyException, UnavailableMerchantException,
			UnavailablePaymentApplicationException, UnavailableGatewayException {	
		return makeTransaction(partnerId, channelId,
				gateway, orgId, amount, orderId, Constants.EMPTY);
	}
	
	public Transaction makeTransaction(String partnerId, String channelId,
			String gateway, String orgId, Money amount, OrderIdentity orderId, String chargeFor)
			throws UnavailablePartnerException, UnavailableChannelException,
			UnavailableCurrencyException, UnavailableMerchantException,
			UnavailablePaymentApplicationException, UnavailableGatewayException {
		Partner partner = partnerRepository.find(partnerId);
		Channel channel = channelRepository.find(channelId);
		Merchant merchant = merchantRepository.find(channelId, orgId);
		if (partner == null) {
			throw new UnavailablePartnerException(partnerId);
		}
		if (channel == null || !partner.available(channel)) {
			throw new UnavailableChannelException(partnerId, channelId);
		}
		if (!channel.support(amount.getCurrency())) {
			throw new UnavailableCurrencyException(channelId,
					amount.getCurrency());
		}
		if (!channel.support(gateway)) {
			throw new UnavailableGatewayException(channelId, gateway);
		}
		if (merchant == null || !merchant.isEnabled()) {
			throw new UnavailableMerchantException(channelId, orgId);
		}
		if (!makePaymentService.support(orderId.getApplication())) {
			throw new UnavailablePaymentApplicationException(
					orderId.getApplication());
		}

		return new Transaction(nextTransactionNo(channel.getId(), merchant),
				partner, amount, clock.now(), orderId, merchant, channel,
				gateway, chargeFor);
	}

	public Transaction cancelTransactionBy(Transaction oldTransaction) {
		if (cannotBeCannelled(oldTransaction)) {
			throw new CannotCancelTransactionException(oldTransaction.state());
		}
		// 金额为原交易的金额的负数 因为第三方支付会对金额进行校验 所以此处不进行校验
		String newChargeFor = createNewChargeFor(oldTransaction);
		Transaction newTransaction = new Transaction(nextTransactionNo(
				oldTransaction.getChannel().getId(),
				oldTransaction.getMerchant()), oldTransaction.getPartner(),
				oldTransaction.getAmount().negate(), clock.now(),
				oldTransaction.getOrderId(), oldTransaction.getMerchant(),
				oldTransaction.getChannel(), oldTransaction.getGateway(),
				TransactionType.CANCEL, oldTransaction.getTransactionNo()
						.getNumber(), newChargeFor);
		return newTransaction;
	}
	
	//将保险费变为负值，其他值不变
	private String createNewChargeFor(Transaction oldTransaction) {
		String newChargeFor = null;
		String[] chargeFors = oldTransaction.getChargeFor().split(",");
		for(int i=0;i<chargeFors.length;i++){
			String value = chargeFors[i];
			if(i==3){
				value = new BigDecimal(chargeFors[i]).negate().toString();
			}
			if(newChargeFor == null){
				newChargeFor = value;
			}else{
				newChargeFor = newChargeFor + "," + value;
			}
		}
		return newChargeFor;
	}

	private boolean cannotBeCannelled(Transaction oldTransaction) {
		if ((!oldTransaction.isConcluded())
				&& (!oldTransaction.isResponsedSuccess())) {
			return true;
		}
		return false;
	}

	public TransactionSelectionSpecification correlatedCancelSpec(
			String oldTransactionNumber) {
		return new CorrelatedCancelSpecification(oldTransactionNumber);
	}

	private TransactionRepository transactionRepository;
	private MerchantRepository merchantRepository;
	private ChannelRepository channelRepository;
	private PartnerRepository partnerRepository;
	private MakePaymentService makePaymentService;
	private Clock clock;
	@Setter
	private TransactionNoGenerator transactionNoGenerator;

	public void setTransactionRepository(
			TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void setChannelRepository(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}

	public void setPartnerRepository(PartnerRepository partnerRepository) {
		this.partnerRepository = partnerRepository;
	}

	public void setMakePaymentService(MakePaymentService makePaymentService) {
		this.makePaymentService = makePaymentService;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

}
