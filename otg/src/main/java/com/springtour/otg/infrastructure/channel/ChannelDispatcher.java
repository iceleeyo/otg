/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import java.util.*;

import lombok.*;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.domain.shared.*;
import com.springtour.otg.infrastructure.logging.LoggingSupport;

/**
 * 
 * @author 006874
 */
public class ChannelDispatcher implements RequestWithCardInfoService,
		RequestWithoutCardInfoService, NotificationFactory,
		ExternalTransactionQueryObject, TransactionNoGenerator {

	@Override
	public List<IdentityType> availableIdentityTypes(String channelId)
			throws UnavailableChannelException, CannotMapIdentityTypeException {
		RequestWithCardInfoService requestWithCardInfoService = forward2RequestWithCardInfoService(channelId);
		return requestWithCardInfoService.availableIdentityTypes(channelId);
	}

	@Override
	public String request(Transaction transaction, CardInfo cardInfo)
			throws UnavailableChannelException, CannotMapIdentityTypeException,
			CannotLaunchSecurityProcedureException {
		RequestWithCardInfoService requestWithCardInfoService = forward(transaction
				.getChannel());
		return requestWithCardInfoService.request(transaction, cardInfo);
	}

	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		RequestWithoutCardInfoService requestWithoutCardInfoService = forward2RequestWithoutCardInfoService(transaction
				.getChannel());
		return requestWithoutCardInfoService.request(transaction, returnUrl,
				customParams);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableChannelException, UnavailableCurrencyException,
			FakeNotificationException {
		NotificationFactory notificationFactory = forward(channelId);
		return notificationFactory.make(channelId, aOriginalNotification);
	}

	private RequestWithCardInfoService forward(Channel channel)
			throws UnavailableChannelException {
		RequestWithCardInfoService requestWithCardInfoService = null;
		for (AbstractRequestWithCardInfoAdapter requestWithCardInfoAdapter : requestWithCardInfoAdapters) {
			if (match(requestWithCardInfoAdapter.getChannel(), channel)) {
				requestWithCardInfoService = requestWithCardInfoAdapter;
			}
		}
		if (requestWithCardInfoService == null) {
			throw new UnavailableChannelException(channel.getId().toString());
		}
		return requestWithCardInfoService;
	}

	private RequestWithCardInfoService forward2RequestWithCardInfoService(
			String channelId) throws UnavailableChannelException {
		RequestWithCardInfoService requestWithCardInfoService = null;
		for (AbstractRequestWithCardInfoAdapter requestWithCardInfoAdapter : requestWithCardInfoAdapters) {
			if (match(requestWithCardInfoAdapter.getChannel(), channelId)) {
				requestWithCardInfoService = requestWithCardInfoAdapter;
			}
		}
		if (requestWithCardInfoService == null) {
			throw new UnavailableChannelException(channelId);
		}
		return requestWithCardInfoService;
	}

	private RequestWithoutCardInfoService forward2RequestWithoutCardInfoService(
			Channel channel) throws UnavailableChannelException {
		RequestWithoutCardInfoService requestWithoutCardInfoService = null;
		for (AbstractRequestWithoutCardAdapter requestWithoutCardInfoAdapter : requestWithoutCardInfoAdapters) {
			if (match(requestWithoutCardInfoAdapter.getChannel(), channel)) {
				requestWithoutCardInfoService = requestWithoutCardInfoAdapter;
			}
		}
		if (requestWithoutCardInfoService == null) {
			throw new UnavailableChannelException(channel.getId().toString());
		}
		return requestWithoutCardInfoService;
	}

	private NotificationFactory forward(String channel)
			throws UnavailableChannelException {
		NotificationFactory notificationFactory = null;
		for (AbstractNotificationFactory abstractNotificationFactory : abstractNotificationFactories) {
			if (match(abstractNotificationFactory.getChannel(), channel)) {
				notificationFactory = abstractNotificationFactory;
			}
		}
		if (notificationFactory == null) {
			throw new UnavailableChannelException(channel);
		}
		return notificationFactory;
	}

	private boolean match(String expected, Channel actual) {
		return match(expected, String.valueOf(actual.getId()));
	}

	private boolean match(String expected, String actual) {
		return expected.equalsIgnoreCase(actual);
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		for (AbstractChannelExternalTransactionQueryObjectAdapter adapter : channelExternalTransactionQueryObjectAdapters) {
			if (match(adapter.getChannel(), transaction.getChannel())) {
				try {
					return adapter.queryBy(transaction);
				} catch (Exception e) {
					final String entry = ChannelDispatcher.class.getName()
							+ ".queryBy";
					loggingSupport.notifyCatching(entry,
							"fail to query from 3rd party for txn ["
									+ transaction.getTransactionNo()
											.getNumber() + "]", e);
					return NonExistedExternalTxnQueryResult.newInstance();
				}
			}
		}
		throw new UnavailableChannelException(transaction.getChannel().getId()
				.toString());
	}

	@Override
	public Set<String> supportedChannels() {
		final Set<String> supportedChannels = new HashSet<String>();
		for (AbstractChannelExternalTransactionQueryObjectAdapter adapter : channelExternalTransactionQueryObjectAdapters) {
			supportedChannels.addAll(adapter.supportedChannels());
		}
		return supportedChannels;
	}
	
	private TransactionNoGenerator forward2TransactionNoGenerator(String channel)
			throws UnavailableChannelException {
		AbstractTransactionNoGenerator abstractTransactionNoGenerator =abstractTransactionNoGeneratorMap.get(channel);
		if (abstractTransactionNoGenerator==null) {
			return transactionNoGenerator;
		}
		return abstractTransactionNoGenerator;

	}

	@Override
	public TransactionNo nextTransactionNo(String channelId, Merchant merchant, String transactionNoSequence, Date whenRequested) {
		TransactionNoGenerator transactionNoGenerator = forward2TransactionNoGenerator(channelId);
		return transactionNoGenerator.nextTransactionNo(channelId, merchant, transactionNoSequence, whenRequested);
	}

	@Setter
	private List<AbstractRequestWithCardInfoAdapter> requestWithCardInfoAdapters;
	@Setter
	private List<AbstractRequestWithoutCardAdapter> requestWithoutCardInfoAdapters;
	@Setter
	private List<AbstractNotificationFactory> abstractNotificationFactories;
	@Setter
	private List<AbstractChannelExternalTransactionQueryObjectAdapter> channelExternalTransactionQueryObjectAdapters;
	@Setter
	private Map<String, AbstractTransactionNoGenerator> abstractTransactionNoGeneratorMap;
	@Setter
	private TransactionNoGenerator transactionNoGenerator;
	@Setter
	private LoggingSupport loggingSupport;
}
