/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.List;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.persistence.TransactionCriteria;
import com.springtour.otg.infrastructure.persistence.ibatis.*;
import com.springtour.otg.FixtureClearer;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.Clock;
import com.springtour.otg.integration.*;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IBatisTransactionRepositoryImplIntegrationTests extends AbstractOtgApplicationContextIntegrationTests {

    @Resource(name = "otg.IBatisTransactionRepositoryImpl")
    private IBatisTransactionRepositoryImpl target;
    @Resource(name = "otg.IBatisNotificationRepositoryImpl")
    private IBatisNotificationRepositoryImpl notificationRepository;
    @Resource(name = "otg.IBatisChannelRepositoryImpl")
    private IBatisChannelRepositoryImpl channelRepository;
    @Resource(name = "otg.IBatisPartnerRepositoryImpl")
    private IBatisPartnerRepositoryImpl partnerRepository;
    @Resource(name = "otg.IBatisMerchantRepositoryImpl")
    private IBatisMerchantRepositoryImpl merchantRepository;
    @Resource(name = "otg.IBatisClockImpl")
    private Clock clock;
    @Resource(name = "jdbcFixtureClearer")
    private FixtureClearer fixtureClearer;
    private static Transaction EXPECT;
    private static Notification EXPECT_NOTIFICATION;

    @Before
    public void resetFixture() throws Exception {
        Transaction expect = new TransactionFixture().build();
        
        fixtureClearer.clear("t_otg_channel",
                new String[]{"id"},
                new String[]{expect.getChannel().getId()});
        channelRepository.store(expect.getChannel());
        
        fixtureClearer.clear("t_otg_partner",
                new String[]{"id"},
                new String[]{expect.getPartner().getId()});
        partnerRepository.store(expect.getPartner());
        
        fixtureClearer.clear("t_merchant",
                new String[]{"merchant_code", "merchant_channel"},
                new String[]{expect.getMerchantCode(), expect.getChannel().getId()});
        merchantRepository.store(expect.getMerchant());
        
        fixtureClearer.clear("t_otg_transaction",
                new String[]{"transaction_no"},
                new String[]{expect.getTransactionNo().getNumber()});
        target.store(expect);
        EXPECT = expect;
        
        
        
        String sequence = notificationRepository.nextSequence();
        Notification expectNotification = new NotificationFixture(clock).specifySequence(sequence).build();
        fixtureClearer.clear("t_otg_notification",
                new String[]{"sequence"},
                new String[]{sequence});
        notificationRepository.store(expectNotification);
        EXPECT_NOTIFICATION = expectNotification;
    }
    
    @Test
    public void testFindByCriteria(){
    	String transactionNoForFind = "123456";
    	String extTxnNo = "654321";
		Transaction expect = new TransactionFixture().specify(transactionNoForFind).specify(TransactionType.CANCEL).build();
    	fixtureClearer.clear("t_otg_transaction",
                new String[]{"transaction_no"},
                new String[]{expect.getTransactionNo().getNumber()});
        target.store(expect);
        String sequence = notificationRepository.nextSequence();
        
		Notification expectNotification = new NotificationFixture(clock).specify(expect).specifyExtTxnNo(extTxnNo).specifySequence(sequence).build();
        fixtureClearer.clear("t_otg_notification",
                new String[]{"sequence"},
                new String[]{sequence});
        notificationRepository.store(expectNotification);
    	List<Transaction> actuals = target.find(new TransactionCriteria.Builder().transactionNoEq(transactionNoForFind).build());
    	Assert.assertEquals(1, actuals.size());
    }
    
    @Test
    public void test(){
        String transactionNoForFind = "123456";
        Transaction expect = new TransactionFixture().specify(transactionNoForFind).specify(TransactionType.CANCEL).build();
        fixtureClearer.clear("t_otg_transaction",
                new String[]{"transaction_no"},
                new String[]{expect.getTransactionNo().getNumber()});
        target.store(expect);
        Transaction transaction = target.find(new TransactionNo(transactionNoForFind));
        System.out.println(transaction.getChargeFor());
        OrderIdentity orderIdentity = new OrderIdentity(null,null,null);
        transaction.changeOrderInfo(orderIdentity, "1");
        target.store(transaction);
        Transaction transaction1 = target.find(new TransactionNo(transactionNoForFind));
        Assert.assertEquals("1", transaction1.getChargeFor());
    }

    @Test
    public void handle() throws Exception {
        Transaction reconsititute = assertResetFixture();

        reconsititute.handle(EXPECT_NOTIFICATION, clock.now());
        update(reconsititute);

        Transaction actual = target.find(EXPECT.getTransactionNo());
        //Assert.assertTrue(new TransactionMatcher().matchesSafely(reconsititute, actual));
    }
    
    @Test
    public void conclude() throws Exception {
        Transaction reconsititute = assertResetFixture();

        reconsititute.handle(EXPECT_NOTIFICATION, clock.now());
        update(reconsititute);

        Transaction handled = target.find(EXPECT.getTransactionNo());
        //Assert.assertTrue(new TransactionMatcher().matchesSafely(reconsititute, handled));
        
        handled.conclude(clock.now());
        update(handled);
        
        Transaction actual = target.find(EXPECT.getTransactionNo());
        Assert.assertTrue(new TransactionMatcher().matchesSafely(handled, actual));
        
    }

    private Transaction assertResetFixture() {
        Transaction reconsititute = target.find(EXPECT.getTransactionNo());
        //Assert.assertTrue(new TransactionMatcher().matchesSafely(EXPECT, reconsititute));
        return reconsititute;
    }

    private void update(Transaction aggregate) throws Exception {
        target.store(aggregate);
        addVersion(aggregate);
    }

    private void addVersion(Transaction aggregate) {
        aggregate.setVersion(aggregate.getVersion() + 1);
    }
}
