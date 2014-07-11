package com.springtour.otg.infrastructure.channel.bill99pos;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.persistence.TransactionCriteria;
import com.springtour.otg.infrastructure.persistence.ibatis.IBatisNotificationRepositoryImpl;
import com.springtour.otg.infrastructure.persistence.ibatis.IBatisTransactionRepositoryImpl;
import com.springtour.otg.infrastructure.time.Clock;
import com.springtour.test.AbstractJMockUnitTests;

public class Bill99PosNotificationFactoryImplUnitTests extends
		AbstractJMockUnitTests {

	private Bill99PosNotificationFactoryImpl target;
	private NotificationRepository notificationRepository = context
			.mock(IBatisNotificationRepositoryImpl.class);
	private NotificationValidator notificationValidator = context
			.mock(Bill99PosNotificationValidatorImpl.class);
	private TransactionRepository transactionRepository = context
			.mock(IBatisTransactionRepositoryImpl.class);
	private Clock clock = context.mock(Clock.class);
	private String channelId = "14";
	private String authCode = "376878";
	private String txnTypePur = "PUR";
	private String txnTypeVtx = "VTX";
	private String shortPAN = "9206031684";
	private String processFlag = "0";
	private String cardType = "0000";
	private String RRN = "000008711464";
	private String signature = "V4uq/lFhiLciGdhsCBX8OWC4X+SI1WeSc9H21Z8oy3/CXuhapGX/F7kVSxoDqC/8EhW/R7mQmLgH1c/6AbJ4c5cOLDhLuRQSSWLzBYcI9IEVE8ukleAL9P++rlAks5MxvJAoghwkYw/Ry/N8vAzCY7T8iehTk3WwwllYUOamKP4=";
	private String issuerId = "";
	private String orgTxnType = "";
	private String txnTime = "20130813 164256";
	private String responseMessage = "交易成功";
	private String issuerView = "";
	private String terminalOperId = "001";
	private String amt = "99.00";
	private String orgExternalTraceNo = "";
	private String responseCode = "00";
	private String merchantId = "812025145110001";
	private String terminalId = "00000625";
	private String externalTraceNo = "2013081334989914";
	private String termTraceNo = "000005";

	private final Transaction transaction = new TransactionFixture().specify(
			externalTraceNo).build();
	private final Map<String, String> originalNotification = new HashMap<String, String>();

	@Before
	public void init() {
		target = new Bill99PosNotificationFactoryImpl(channelId);
		target.setNotificationRepository(notificationRepository);
		target.setNotificationValidator(notificationValidator);
		target.setTransactionRepository(transactionRepository);
		target.setClock(clock);

		originalNotification.put("authCode", authCode);
		originalNotification.put("shortPAN", shortPAN);
		originalNotification.put("processFlag", processFlag);
		originalNotification.put("cardType", cardType);
		originalNotification.put("RRN", RRN);
		originalNotification.put("signature", signature);
		originalNotification.put("issuerId", issuerId);
		originalNotification.put("orgTxnType", orgTxnType);
		originalNotification.put("txnTime", txnTime);
		originalNotification.put("responseMessage", responseMessage);
		originalNotification.put("issuerView", issuerView);
		originalNotification.put("terminalOperId", terminalOperId);
		originalNotification.put("amt", amt);
		originalNotification.put("orgExternalTraceNo", orgExternalTraceNo);
		originalNotification.put("responseCode", responseCode);
		originalNotification.put("merchantId", merchantId);
		originalNotification.put("terminalId", terminalId);
		originalNotification.put("externalTraceNo", externalTraceNo);
		originalNotification.put("termTraceNo", termTraceNo);
	}

	@Test
	public void testMakeWhenTxnTypeIsPUR() throws Exception {
		originalNotification.put("txnType", txnTypePur);
		context.checking(new Expectations() {
			{

				oneOf(transactionRepository).find(
						new TransactionNo(externalTraceNo));
				will(returnValue(transaction));

				allowing(notificationValidator).validate(originalNotification);
				will(returnValue(true));

				allowing(notificationRepository).nextSequence();
				will(returnValue("5555"));

				allowing(clock).now();
				will(returnValue(new Date()));
			}
		});
		
		assertNotification(channelId,originalNotification);
	}

	@Test
	public void testMakeWhenTxnTypeIsVTX() throws Exception {
		originalNotification.put("txnType", txnTypeVtx);
		context.checking(new Expectations() {
			{

				oneOf(transactionRepository).find(
						new TransactionNo(externalTraceNo));
				will(returnValue(transaction));

				allowing(notificationRepository).nextSequence();
				will(returnValue("5555"));

				allowing(clock).now();
				will(returnValue(new Date()));
			}
		});
		
		assertNotification(channelId,originalNotification);
	}
	
	
	public void assertNotification(String channelId,Map<String, String> originalNotification){
		Notification notification = target
			.make(channelId, originalNotification);
		assertEquals(externalTraceNo, notification.getTxnNo().getNumber());
		assertEquals(new BigDecimal(amt), notification.getAmount().getAmount());
		assertEquals("1", notification.getCharged());
		String exceptExtTxnNo = authCode + "," + RRN + "," + termTraceNo;
		assertEquals(exceptExtTxnNo, notification.getExtTxnNo());
		assertEquals(responseCode, notification.getMessage());
		assertEquals(signature, notification.getSignature());
	}

}
