package com.springtour.otg.integration;

import com.springtour.otg.*;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import javax.servlet.http.HttpServletRequest;
import org.junit.Assert;

public abstract class AbstractNotificationReceiverSadPathIntegrationTests extends AbstractOtgIntegrationTests {

    private ReceivingTestDriver driver;
    private String channel;
    private String dbopBeanName;
    private String receiverBeanName;
    private String badAmount;
    private String fakeSignature;
    private String badTxnNo;

    public AbstractNotificationReceiverSadPathIntegrationTests() {
        super();
    }

    public void setFakeSignatureBeanName(String fakeNotificationHttpServletRequestAssemblerBeanName) {
        this.fakeSignature = fakeNotificationHttpServletRequestAssemblerBeanName;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setBadAmountBeanName(String notificationHttpServletRequestAssemblerWithBadAmountBeanName) {
        this.badAmount = notificationHttpServletRequestAssemblerWithBadAmountBeanName;
    }

    public void setBadTxnNoBeanName(String notificationHttpServletRequestAssemblerWithBadTxnNoBeanName) {
        this.badTxnNo = notificationHttpServletRequestAssemblerWithBadTxnNoBeanName;
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

    public void testThrowsAnFakeNotificationExceptionWhenReceivingGivenAFakeNotification() throws Exception {
        //TODO 目前这个测试必须第一个执行，否则一旦switchToAlwaysPassValidator 下一个测试不能重置，因为整个测试用例初始化时才会重置
        driver.switchToProductionValidator();
        
        driver.receive(aNotificationAttmeptWith(fakeSignature));
        Assert.assertTrue(driver.getNotifications().isEmpty());
        Assert.assertTrue(driver.getTransaction().isRequested());
    }

    public void testThrowsAnIllegalAmountExceptionWhenHandlingGivenANotificationWithBadAmount() throws Exception {

        driver.switchToAlwaysPassValidator();

        driver.receive(aNotificationAttmeptWith(badAmount));

        Assert.assertTrue(driver.getNotifications().size() == 1);
        Assert.assertTrue(driver.getTransaction().isRequested());
    }

    public void testThrowsAnExceptionWhenReceivingGivenANotificationWithBadTransactionNo() throws Exception {
        driver.switchToAlwaysPassValidator();
        driver.receive(aNotificationAttmeptWith(badTxnNo));

        Assert.assertTrue(driver.getNotifications().isEmpty());
        Assert.assertTrue(driver.getTransaction().isRequested());
    }

    private HttpServletRequest aNotificationAttmeptWith(String notificationHttpServletRequestAssembler) throws Exception {
        return driver.givesANotificationWith(notificationHttpServletRequestAssembler);
    }
}
