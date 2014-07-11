package com.springtour.otg.application.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.springtour.otg.application.exception.CannotRetrieveSynchronizingMethodException;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.time.Clock;

import java.util.Date;

@RunWith(JMock.class)
public class NotificationServiceImplMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private NotificationServiceImpl target;
    private static final String EMPTY_STRING = "";
    private Clock clock = context.mock(Clock.class);
    private NotificationFactory notificationFactory = context.mock(NotificationFactory.class);
    private NotificationRepository notificationRepository = context.mock(NotificationRepository.class);
    private TransactionRepository transactionRepository = context.mock(TransactionRepository.class);
	private String transactionNumber = "123456";
	private TransactionNo transactionNo = new TransactionNo(transactionNumber );
	private String extTxnNo = "654321";
	private Charge charged = Charge.of("1");
	final Date now = now();
	private SynchronizingMethod retrieveType;
	
	final String notificationSeq = "123456";
	final Transaction transaction = new TransactionFixture().specify(transactionNo.getNumber()).build();	
	

    @Before
    public void setUp() {
        target = new NotificationServiceImpl();
        //target.setApplicationEvents(applicationEvents);
        target.setClock(clock);
        target.setNotificationFactory(notificationFactory);
        target.setNotificationRepository(notificationRepository);
        target.setTransactionRepository(transactionRepository);
        
        context.checking(new Expectations() {

			{
				{
					allowing(transactionRepository).find(transactionNo);
					will(returnValue(transaction));
					
					allowing(notificationRepository).nextSequence();
					will(returnValue(notificationSeq));
					
					allowing(clock).now();
					will(returnValue(now));
				}
			}
		});
    }

    @Test
    public void testHandRetrieve() {
    	retrieveType = SynchronizingMethod.MANUAL;
    	final Notification notification = Notification.handMake(notificationSeq, now, transaction, extTxnNo, charged);
    	context.checking(new Expectations() {

			{
				{
					oneOf(notificationRepository).store(notification);
				}
			}
		});
    	assertNotification(notificationSeq, transaction, retrieveType);
    }
    
    @Test
    public void testCheckRetrieve() {
    	retrieveType = SynchronizingMethod.CHECK;
    	
    	final Notification notification = Notification.byCheck(notificationSeq, now, transaction, extTxnNo, charged);
    	context.checking(new Expectations() {

			{
				{
					oneOf(notificationRepository).store(notification);
				}
			}
		});
    	
    	assertNotification(notificationSeq, transaction, retrieveType);
    }
    
    @Test(expected = CannotRetrieveSynchronizingMethodException.class)
    public void testCannotRetrieveSynchronizingMethod() {
    	retrieveType = SynchronizingMethod.ASYNCHRONOUS;
    	target.retrieve(transactionNo, extTxnNo, charged, retrieveType);
    }

	private void assertNotification(final String notificationSeq,
			final Transaction transaction, SynchronizingMethod retrieveType) {
		Notification actualNotification = target.retrieve(transactionNo, extTxnNo, charged, retrieveType);
        Assert.assertEquals(transaction.getAmount(), actualNotification.getAmount());
        Assert.assertEquals(charged.getCode(),actualNotification.getCharged());
        Assert.assertEquals(extTxnNo,actualNotification.getExtTxnNo());
        Assert.assertEquals(notificationSeq,actualNotification.getSequence());
        Assert.assertEquals(EMPTY_STRING,actualNotification.getMessage());
        Assert.assertEquals(EMPTY_STRING,actualNotification.getSignature());
        Assert.assertEquals(transactionNo,actualNotification.getTxnNo());
        Assert.assertEquals(now,actualNotification.getWhenReceived());
        Assert.assertEquals(retrieveType.getCode(),actualNotification.getSynchronizingMethod());
	}
    
    private Date now() {
		return new Date();
	}
}
