package com.springtour.otg.interfaces.transacting.facade.internal;

import static org.junit.Assert.*;


import java.math.BigDecimal;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.springtour.otg.application.TransactingService;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.service.RequestWithoutCardInfoService;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.interfaces.transacting.facade.rq.CancelTransactionRq;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import com.springtour.otg.interfaces.transacting.facade.rs.RequestWithoutCardInfoRs;

@RunWith(JMock.class)
public class RequestHandlerFacadeImplUnitTests {

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private RequestHandlerFacadeImpl target;
	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);
	private TransactingService transactingService = context
			.mock(TransactingService.class);
	private RequestWithoutCardInfoService requestWithoutCardInfoAdapter = context
			.mock(RequestWithoutCardInfoService.class);

	@Before
	public void prepare() {
		target = new RequestHandlerFacadeImpl();
		target.setTransactingService(transactingService);
		target.setTransactionRepository(transactionRepository);
		target.setRequestWithoutCardInfoAdapter(requestWithoutCardInfoAdapter);
	}

	@Test
	public void requestForCancelTxn() {
		final CancelTransactionRq rq = new CancelTransactionRq();
		rq.setOldTransactionNumber("1234455");

		final Transaction newTransaction = new TransactionFixture().build();
		final String url = "";
		context.checking(new Expectations() {
			{
				oneOf(transactingService).cancelTransaction(rq.getOldTransactionNumber());
				will(returnValue(newTransaction));

				oneOf(requestWithoutCardInfoAdapter).request(
						with(any(Transaction.class)), with(any(String.class)),
						with(any(Map.class)));
				will(returnValue(url));
			}
		});
		RequestWithoutCardInfoRs rs = target.requestForCancelTransaction(rq);
		assertEquals(StatusCode.SUCCESS, rs.getStatusCode());
	}

	@Test
	public void requestWithoutCardInfoForNewTxn() {
		final RequestWithoutCardInfoRq rq = new RequestWithoutCardInfoRq();
		rq.setAmount(BigDecimal.valueOf(100));
		rq.setCurrency("CNY");
		final Transaction newTransaction = new TransactionFixture().build();
		final String url = "";
		context.checking(new Expectations() {
			{

				oneOf(transactingService).newTransaction(
						rq.getPartnerId(),
						rq.getChannelId(),
						rq.getGateway(),
						rq.getOrgId(),
						Money.valueOf(rq.getAmount(), rq.getCurrency()),
						OrderIdentity.valueOf(rq.getApplication(),
								rq.getOrderId(), rq.getOrderNo()));
				will(returnValue(newTransaction));

				oneOf(requestWithoutCardInfoAdapter).request(
						with(any(Transaction.class)), with(any(String.class)),
						with(any(Map.class)));
				will(returnValue(url));
			}
		});
		RequestWithoutCardInfoRs rs = target.requestWithoutCardInfo(rq);
		assertEquals(StatusCode.SUCCESS, rs.getStatusCode());
	}
}
