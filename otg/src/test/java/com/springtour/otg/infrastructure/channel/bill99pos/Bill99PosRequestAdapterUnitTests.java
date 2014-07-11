package com.springtour.otg.infrastructure.channel.bill99pos;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.transaction.HandlingActivity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;

@RunWith(JMock.class)
public class Bill99PosRequestAdapterUnitTests {

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Bill99PosRequestAdapter target;
	private Transaction transaction;
	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);

	@Before
	public void prepare() {
		target = new Bill99PosRequestAdapter("1");
		target.setTransactionRepository(transactionRepository);

	}

	@Test
	public void requestForNewTxn() {
		transaction = new TransactionFixture().build();
		String token = target.request(transaction, null, null);
		String expected = "0000000001000000000071000102131243532014021312000012                                              ";
		assertEquals(expected, token);
		assertEquals(98, token.length());
	}

	@Test
	public void requestForCancelTxn() {
		final String transactionNo = "A258521";
		final Transaction referenceTxn = context.mock(Transaction.class);
		transaction = new TransactionFixture()
				.specify(Money.valueOf(new BigDecimal(-100)))
				.specify(TransactionType.CANCEL).referenceNumber(transactionNo).build();
		final HandlingActivity handlingActivity = context.mock(HandlingActivity.class);
		final Notification notification = context.mock(Notification.class);
		
		final String externalTxnNo= "456,123,123456";
		context.checking(new Expectations(){
			{
				allowing(transactionRepository).find(new TransactionNo(transactionNo));
				will(returnValue(referenceTxn));
				
				allowing(referenceTxn).getHandlingActivity();
				will(returnValue(handlingActivity));
				
				allowing(handlingActivity).getNotification();
				will(returnValue(notification));
				
				allowing(notification).getExtTxnNo();
				will(returnValue(externalTxnNo));
			}
		});
		String token = target.request(transaction, null, null);
		String expected = "0200000001000000000071000102131243532014021312000012    123456                                    ";
		assertEquals(expected, token);
		assertEquals(98, token.length());
	}

}
