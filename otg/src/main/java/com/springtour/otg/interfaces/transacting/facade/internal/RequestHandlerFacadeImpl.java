package com.springtour.otg.interfaces.transacting.facade.internal;

import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import com.springtour.otg.application.TransactingService;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.CardHolder;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.IdentityType;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.domain.service.RequestWithCardInfoService;
import com.springtour.otg.domain.service.RequestWithoutCardInfoService;
import com.springtour.otg.interfaces.transacting.facade.TransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.arg.FindMerchantArgument;
import com.springtour.otg.interfaces.transacting.facade.arg.NextTransactionNoArgument;
import com.springtour.otg.interfaces.transacting.facade.dto.MerchantDTO;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.MerchantDTOAssembler;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.RecommendedGatewayDtoAssembler;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.TransactionDtoAssembler;
import com.springtour.otg.interfaces.transacting.facade.result.FindMerchantResult;
import com.springtour.otg.interfaces.transacting.facade.result.NextTransactionNoResult;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.interfaces.transacting.facade.RequestHandlerFacade;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.AvailableChannelDtoAssembler;
import com.springtour.otg.interfaces.transacting.facade.rq.*;
import com.springtour.otg.interfaces.transacting.facade.rs.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "com.springtour.otg.interfaces.transacting.facade.RequestHandlerFacade")
@BindingType(value = SOAPBinding.SOAP11HTTP_BINDING)
public class RequestHandlerFacadeImpl implements RequestHandlerFacade {

	@Override
	public NewTransactionRs newTransaction(NewTransactionRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			Transaction transaction = transactingService.newTransactionWithChargeFor(rq
					.getPartnerId(), rq.getChannelId(), rq.getGateway(), rq
					.getOrgId(),
					Money.valueOf(rq.getAmount(), rq.getCurrency()),
					OrderIdentity.valueOf(rq.getApplication(), rq.getOrderId(),
							rq.getOrderNo()), rq.getChargeFor());
			NewTransactionRs rs = new NewTransactionRs(StatusCode.SUCCESS,
					new TransactionDtoAssembler().toDto(transaction));
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new NewTransactionRs(applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new NewTransactionRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public AvailableGatewaysRs availableGateways(AvailableGatewaysRq rq) {
		LoggerFactory.getLogger().info(rq);
		AvailableGatewaysRs rs = null;
		try {
			Partner partner = partnerRepository.find(rq.getPartnerId());
			if (partner == null) {
				rs = new AvailableGatewaysRs(StatusCode.UNAVAILABLE_PARTNER);
			} else {
				rs = new AvailableGatewaysRs(StatusCode.SUCCESS,
						new RecommendedGatewayDtoAssembler().toDto(partner
								.getRecommendedGateways()),
						new AvailableChannelDtoAssembler().toDto(partner
								.availableChannels(channelRepository)));
			}
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new AvailableGatewaysRs(applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new AvailableGatewaysRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public AvailableCardHolderIdTypesRs availableCardHolderIdTypes(
			AvailableCardHolderIdTypesRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			AvailableCardHolderIdTypesRs rs = new AvailableCardHolderIdTypesRs(
					StatusCode.SUCCESS,
					toIdTypeDto(requestAdapter.availableIdentityTypes(rq
							.getChannelId())));
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new AvailableCardHolderIdTypesRs(
					applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new AvailableCardHolderIdTypesRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public RequestWithCardInfoRs requestWithCardInfo(RequestWithCardInfoRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			Transaction transaction = transactingService.newTransaction(rq
					.getPartnerId(), rq.getChannelId(), rq.getGateway(), rq
					.getOrgId(),
					Money.valueOf(rq.getAmount(), rq.getCurrency()),
					OrderIdentity.valueOf(rq.getApplication(), rq.getOrderId(),
							rq.getOrderNo()));
			String statusCode = requestAdapter.request(transaction,
					toCardInfo(rq));
			RequestWithCardInfoRs rs = new RequestWithCardInfoRs(statusCode,
					new TransactionDtoAssembler().toDto(transaction));
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new RequestWithCardInfoRs(
					applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new RequestWithCardInfoRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public RequestWithoutCardInfoRs requestForCancelTransaction(
			CancelTransactionRq rq) {
		try {
			Transaction transaction = transactingService.cancelTransaction(rq
					.getOldTransactionNumber());
			String url = requestWithoutCardInfoAdapter.request(transaction,
					null, null);
			RequestWithoutCardInfoRs rs = new RequestWithoutCardInfoRs(
					StatusCode.SUCCESS,
					new TransactionDtoAssembler().toDto(transaction), url);
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new RequestWithoutCardInfoRs(
					applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new RequestWithoutCardInfoRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public RequestWithoutCardInfoRs requestWithoutCardInfo(
			RequestWithoutCardInfoRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 新增交易
			Transaction transaction = transactingService.newTransaction(rq
					.getPartnerId(), rq.getChannelId(), rq.getGateway(), rq
					.getOrgId(),
					Money.valueOf(rq.getAmount(), rq.getCurrency()),
					OrderIdentity.valueOf(rq.getApplication(), rq.getOrderId(),
							rq.getOrderNo()));
			map.put("surferIp", rq.getSurferIp());
			map.put("installment", rq.getInstallment());
			String url = requestWithoutCardInfoAdapter.request(transaction,
					rq.getReturnUrl(), map);
			RequestWithoutCardInfoRs rs = new RequestWithoutCardInfoRs(
					StatusCode.SUCCESS,
					new TransactionDtoAssembler().toDto(transaction), url);
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new RequestWithoutCardInfoRs(
					applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new RequestWithoutCardInfoRs(StatusCode.UNKNOWN_ERROR);
		}
	}
	
	@Override
	public RequestWithoutCardInfoRs requestWithoutCardInfoForBill99Pos(
			RequestWithoutCardInfoRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			Map<String, String> map = new HashMap<String, String>();
			// 新增交易
			Transaction transaction = transactingService.newTransactionWithChargeFor(rq
					.getPartnerId(), rq.getChannelId(), rq.getGateway(), rq
					.getOrgId(),
					Money.valueOf(rq.getAmount(), rq.getCurrency()),
					OrderIdentity.valueOf(rq.getApplication(), rq.getOrderId(),
							rq.getOrderNo()),rq.getChargeFor());
			map.put("surferIp", rq.getSurferIp());
			map.put("installment", rq.getInstallment());
			String url = requestWithoutCardInfoAdapter.request(transaction,
					rq.getReturnUrl(), map);
			RequestWithoutCardInfoRs rs = new RequestWithoutCardInfoRs(
					StatusCode.SUCCESS,
					new TransactionDtoAssembler().toDto(transaction), url);
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
			LoggerFactory.getLogger().error(applicationException.getMessage(),
					applicationException);
			return new RequestWithoutCardInfoRs(
					applicationException.getStatusCode());
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new RequestWithoutCardInfoRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	@Override
	public ListTransactionsRs listTransactions(ListTransactionsRq rq) {
		LoggerFactory.getLogger().info(rq);
		try {
			List<Transaction> transactions = transactionRepository.find(
					rq.getApplicationEq(), rq.getOrderIdEq(), 1,
					Integer.MAX_VALUE);
			ListTransactionsRs rs = new ListTransactionsRs(StatusCode.SUCCESS,
					new TransactionDtoAssembler().toDto(transactions));
			LoggerFactory.getLogger().info(rs);
			return rs;
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new ListTransactionsRs(StatusCode.UNKNOWN_ERROR);
		}
	}

	private CardInfo toCardInfo(RequestWithCardInfoRq rq) {
		return new CardInfo(rq.getCardNo(), rq.getExpireDate(), rq.getCvv2(),
				new CardHolder(rq.getCardHolderFullname(),
						rq.getCardHolderIdNo(), IdentityType.of(rq
								.getCardHolderIdType())));
	}

	private List<String> toIdTypeDto(List<IdentityType> identityTypes) {
		if (identityTypes == null) {
			return Collections.EMPTY_LIST;
		} else {
			List<String> dtos = new ArrayList<String>();
			for (IdentityType type : identityTypes) {
				dtos.add(type.getCode());
			}
			return dtos;
		}
	}

	private TransactingService transactingService;
	private PartnerRepository partnerRepository;
	private ChannelRepository channelRepository;
	private TransactionRepository transactionRepository;
	private RequestWithCardInfoService requestAdapter;
	private RequestWithoutCardInfoService requestWithoutCardInfoAdapter;

	public void setRequestAdapter(RequestWithCardInfoService requestAdapter) {
		this.requestAdapter = requestAdapter;
	}

	public void setTransactingService(TransactingService transactingService) {
		this.transactingService = transactingService;
	}

	public void setRequestWithoutCardInfoAdapter(
			RequestWithoutCardInfoService requestWithoutCardInfoAdapter) {
		this.requestWithoutCardInfoAdapter = requestWithoutCardInfoAdapter;
	}

	public void setPartnerRepository(PartnerRepository partnerRepository) {
		this.partnerRepository = partnerRepository;
	}

	public void setChannelRepository(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	public void setTransactionRepository(
			TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
}
