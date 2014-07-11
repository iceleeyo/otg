package com.springtour.otg.integration;

import com.springtour.otg.*;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.transaction.Transaction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.Assert;

public abstract class AbstractNotificationReceiverHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private ReceivingTestDriver driver;
    private String channel;
    private String dbopBeanName;
    private String receiverBeanName;
    private String chargedSuccess;
    private String chargedFailure;

    public AbstractNotificationReceiverHappyPathIntegrationTests() {
        super();
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setChargedSuccess(String goodSignature) {
        this.chargedSuccess = goodSignature;
    }

    public void setChargedFailure(String chargedFailure) {
        this.chargedFailure = chargedFailure;
    }

    public void setReceiverBeanName(String receiverBeanName) {
        this.receiverBeanName = receiverBeanName;
    }

    public void setDbopBeanName(String dbopBeanName) {
        this.dbopBeanName = dbopBeanName;
    }

    @Override
    public void onSetUp() throws Exception {
        driver = new ReceivingTestDriver(super.getApplicationContext(), dbopBeanName, receiverBeanName, channel);
    }

    /**
     * 假如有一笔请求中的交易，当接收到一条扣款成功的通知后，通知系统处理付款并确认通知已收到
     * @throws Exception 
     */
    public void testGivenARequestedTransactionWhenReceivesAChargedSuccessNotificationThenNotifiesPaymentMakingAndAcknowledges() throws Exception {
        //driver.switchToProductionValidator();
        driver.switchToAlwaysPassValidator();//todo 需要切换production



        //driver.receiveConcurrently(aNotificationAttmeptWith(chargedSuccess));
        driver.receive(aNotificationAttmeptWith(chargedSuccess));

        Transaction transaction = driver.getTransaction();
        List<Notification> notifications = driver.getNotifications();

        Assert.assertEquals(3, transaction.getVersion());
        Assert.assertTrue(transaction.isConcluded());
        //Assert.assertEquals(transaction.getHandlingActivity().getNotification().getSequence(), notifications.get(ReceivingTestDriver.RECEIVING_COUNT - 1).getSequence());
        //Assert.assertTrue(notifications.size() == ReceivingTestDriver.RECEIVING_COUNT);
        Assert.assertTrue(notifications.size() == 1);
    }

    /**
     * 假如有一笔请求中的交易，当接收到一条扣款失败的通知后，通知系统关闭交易并确认通知已收到
     * @throws Exception 
     */
    public void testGivenARequestedTransactionWhenReceivesAChargedFailureNotificationThenNotifiesTransactionClosingAndAcknowledges() throws Exception {

        //driver.switchToProductionValidator();
        driver.switchToAlwaysPassValidator();//todo 需要切换production



        //driver.receiveConcurrently(aNotificationAttmeptWith(chargedFailure));
        driver.receive(aNotificationAttmeptWith(chargedFailure));

        Transaction transaction = driver.getTransaction();
        List<Notification> notifications = driver.getNotifications();

        Assert.assertEquals(2, transaction.getVersion());
        Assert.assertTrue(transaction.isChargedFailure());
        //Assert.assertEquals(transaction.getHandlingActivity().getNotification().getSequence(), notifications.get(ReceivingTestDriver.RECEIVING_COUNT - 1).getSequence());
        //Assert.assertTrue(notifications.size() == ReceivingTestDriver.RECEIVING_COUNT);
        Assert.assertTrue(notifications.size() == 1);
    }

    private HttpServletRequest aNotificationAttmeptWith(String goodSignature) throws Exception {
        return driver.givesANotificationWith(goodSignature);
    }
}
