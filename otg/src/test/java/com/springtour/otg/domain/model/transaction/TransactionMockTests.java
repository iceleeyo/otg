package com.springtour.otg.domain.model.transaction;

import static com.springtour.otg.domain.model.transaction.CheckingState.*;
import static org.junit.Assert.*;

import java.math.*;
import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.shared.*;

@RunWith(JMock.class)
public class TransactionMockTests {

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private Transaction transaction;
	private Merchant merchant = context.mock(Merchant.class);
	private Partner partner = context.mock(Partner.class);
	private Channel channel = context.mock(Channel.class);
	private Notification notification = context.mock(Notification.class);

	@Before
	public void onSetUp() throws UnavailableCurrencyException {
		final String merchantName = "merchant";
		context.checking(new Expectations() {

			{
				oneOf(merchant).getCode();
				will(returnValue(merchantName));

			}
		});
		transaction = new Transaction(new TransactionNo("111111"), partner,
				Money.valueOf(BigDecimal.valueOf(100)), now(),
				OrderIdentity.valueOf("1", "2", "3"), merchant, channel, "cmb");
		HandlingActivity handlingActivity = new HandlingActivity();
		handlingActivity.setNotification(notification);
		transaction.setHandlingActivity(handlingActivity);
		assertTrue(transaction.is(UNCHECKED));
	}

	private void handlesANotification(final Date whenReceived,
			final boolean isCharged, final Money amount) throws Exception {

		context.checking(new Expectations() {

			{
				oneOf(notification).getAmount();
				will(returnValue(amount));

				oneOf(notification).isCharged();
				will(returnValue(isCharged));

			}
		});

		transaction.handle(notification, whenReceived);
	}

	@Test
	public void handlesANotificationOfChargedSuccess() throws Exception {
		Date whenGetResponse = now();
		final boolean isSuccess = true;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));

		handlesANotification(whenGetResponse, isSuccess, amount);
		Assert.assertEquals(Transaction.State.RESPONSED_SUCCESS.getCode(),
				transaction.getState());
		Assert.assertEquals(whenGetResponse, transaction.getHandlingActivity()
				.getWhenHandled());
		Assert.assertTrue(notification == transaction.getHandlingActivity()
				.getNotification());

	}

	@Test
	public void regardedAsUncheckedWhenHandlesAValidNotificationGivenADeadTransaction()
			throws Exception {
		transaction.regardedAsDeadAt(now());
		handlesANotificationOfChargedSuccess();

		assertTrue(transaction.is(UNCHECKED));
	}

	private Date now() {
		return new Date();
	}

	@Test
	public void handlesANotificationOfChargedFailure() throws Exception {
		Date whenGetResponse = now();
		final boolean isSuccess = false;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));

		handlesANotification(whenGetResponse, isSuccess, amount);
		Assert.assertEquals(Transaction.State.RESPONSED_FAILURE.getCode(),
				transaction.getState());
		Assert.assertEquals(whenGetResponse, transaction.getHandlingActivity()
				.getWhenHandled());
		Assert.assertTrue(notification == transaction.getHandlingActivity()
				.getNotification());

	}

	@Test(expected = IllegalAmountException.class)
	public void throwsAnIllegalAmountExceptionWhenAmountMismatched()
			throws Exception {
		Date whenGetResponse = now();
		final Money amount = Money.valueOf(BigDecimal.valueOf(100), "USD");

		context.checking(new Expectations() {

			{
				exactly(2).of(notification).getAmount();
				will(returnValue(amount));

			}
		});

		transaction.handle(notification, whenGetResponse);

	}

	/**
	 * 当重复收到成功应答时
	 */
	@Test(expected = DuplicateResponseException.class)
	public void throwsADuplicateNotificationExceptionWhenResponsedSuccess()
			throws Exception {
		Date whenGetResponse = now();
		final boolean isSuccess = true;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));
		final String sequence = "1231231";

		handlesANotification(whenGetResponse, isSuccess, amount);

		context.checking(new Expectations() {

			{
				oneOf(notification).getSequence();
				will(returnValue(sequence));

			}
		});

		transaction.handle(notification, whenGetResponse);// 重复处理
	}

	/**
	 * 当重复收到失败应答时
	 */
	@Test(expected = DuplicateResponseException.class)
	public void throwsADuplicateNotificationExceptionWhenResponsedFailure()
			throws Exception {

		Date whenGetResponse = now();
		final boolean isSuccess = false;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));

		handlesANotification(whenGetResponse, isSuccess, amount);
		final String sequence = "1231231";
		context.checking(new Expectations() {

			{
				oneOf(notification).getSequence();
				will(returnValue(sequence));

			}
		});
		transaction.handle(notification, whenGetResponse);// 重复处理
	}

	/**
	 * 当重复收到失败应答时
	 */
	@Test(expected = DuplicateResponseException.class)
	public void throwsADuplicateNotificationExceptionWhenConcluded()
			throws Exception {
		Date whenGetResponse = now();
		concludesWhenResponsedSuccess();
		final String sequence = "1231231";
		context.checking(new Expectations() {

			{
				oneOf(notification).getSequence();
				will(returnValue(sequence));

			}
		});
		transaction.handle(notification, whenGetResponse);// 重复处理
	}

	@Test
	public void concludesWhenResponsedSuccess() throws Exception {

		Date whenGetResponse = now();
		final boolean isSuccess = true;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));

		handlesANotification(whenGetResponse, isSuccess, amount);
		Date whenConcluded = now();
		transaction.conclude(whenConcluded);
		Assert.assertEquals(Transaction.State.CONCLUDED.getCode(),
				transaction.getState());
		Assert.assertEquals(whenConcluded, transaction.getWhenConcluded());
	}

	/**
	 * 当transaction的state异常时
	 */
	@Test(expected = IllegalStateException.class)
	public void throwsAnIllegalStateExceptionWhenRequested() throws Exception {
		Date whenConcluded = now();
		transaction.conclude(whenConcluded);
	}

	/**
	 * 当transaction的state异常时
	 */
	@Test(expected = IllegalStateException.class)
	public void throwsAnIllegalStateExceptionWhenResponsedFailure()
			throws Exception {
		Date whenGetResponse = now();
		final boolean isSuccess = false;
		final Money amount = Money.valueOf(BigDecimal.valueOf(100));

		handlesANotification(whenGetResponse, isSuccess, amount);

		Date whenConcluded = now();
		transaction.conclude(whenConcluded);
	}

	/**
	 * 当transaction的state异常时
	 */
	@Test(expected = IllegalStateException.class)
	public void throwsAnIllegalStateExceptionWhenConcluded() throws Exception {
		Date whenConcluded = now();

		concludesWhenResponsedSuccess();

		transaction.conclude(whenConcluded);
	}

	@Test
	public void transactionIsCheckedWhenTransactionHasBeenRegardedAsChecked()
			throws Exception {
		Date now = now();

		transaction.regardedAsValidAt(now);

		assertTrue(transaction.is(VALID));
	}

	@Test(expected = CannotChangeCheckingStateException.class)
	public void throwsWhenTransactionIsRegardedAsCheckedGivenTransactionIsNotUnchecked()
			throws Exception {
		Date now = now();

		transaction.regardedAsInvalidAt(now);
		transaction.regardedAsValidAt(now);
	}

	@Test
	public void transactionIsCheckedWhenTransactionHasBeenRegardedAsInvalid()
			throws Exception {
		Date now = now();

		transaction.regardedAsInvalidAt(now);

		assertTrue(transaction.is(INVALID));
	}

	@Test(expected = CannotChangeCheckingStateException.class)
	public void throwsWhenTransactionIsRegardedAsInvalidGivenTransactionIsNotUnchecked()
			throws Exception {
		Date now = now();

		transaction.regardedAsValidAt(now);
		transaction.regardedAsInvalidAt(now);

	}

	@Test
	public void transactionIsCheckedWhenTransactionHasBeenRegardedAsDead()
			throws Exception {
		Date now = now();

		transaction.regardedAsDeadAt(now);

		assertTrue(transaction.is(DEAD));
	}

	@Test(expected = CannotChangeCheckingStateException.class)
	public void throwsWhenTransactionIsRegardedAsDeadGivenTransactionIsNotUnchecked()
			throws Exception {
		Date now = now();

		transaction.regardedAsValidAt(now);
		transaction.regardedAsDeadAt(now);

	}
	
	@Test
	public void returnTrueWhenNotificationIsRetrievedByCheck(){
		context.checking(new Expectations() {

			{
				oneOf(notification).isByCheck();
				will(returnValue(true));

			}
		});
		
		Assert.assertTrue(transaction.isNotificationRetrievedByCheck());
	}
	
	@Test
	public void returnFalseWhenNotificationIsNotRetrievedByCheck(){
		context.checking(new Expectations() {

			{
				oneOf(notification).isByCheck();
				will(returnValue(false));

			}
		});
		
		Assert.assertFalse(transaction.isNotificationRetrievedByCheck());
	}

	@Test
	public void isNewTxn() {

		assertTrue(transaction.isNewTxn());

	}

	@Test
	public void isCancelTxn() {

		transaction = new TransactionFixture().specify(TransactionType.CANCEL)
				.build();
		assertTrue(transaction.isCancelTxn());

	}
}
