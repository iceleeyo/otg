/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.application.NotificationService;
import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.dbop.DataReader;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.channel.AlwaysPassNotificationValidator;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.payment.PaymentDispatcher;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.TransactionDtoAssembler;
import com.springtour.otg.interfaces.transacting.web.NotificationReceiver;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.DocumentException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author 001595
 */
public class ReceivingTestDriver {

    private DataPrepareOperation givesAll;
    private DataPrepareOperation notificationClearer;
    private NotificationReceiver notificationReceiver;
    private TransactionNo txnNoRequested;
    private TransactionRepository transactionRepository;
    private NotificationRepository notificationRepository;
    private ConfigurableApplicationContext applicationContext;
    private ResponseProcedure responseProcedure;
    private NotificationValidator productValidator;
    private String channel;
    public static final int RECEIVING_COUNT = 10;

    public ReceivingTestDriver(ConfigurableApplicationContext applicationContext, String dbopBeanName, String receiverBeanName, String channel) {
        this.applicationContext = applicationContext;
        this.channel = channel;
        givesAll =
                (DataPrepareOperation) applicationContext.getBean(dbopBeanName);

        notificationClearer = (DataPrepareOperation) applicationContext.getBean("otg.NotificationClearer");

        this.notificationReceiver = getReceiver(receiverBeanName);

        this.transactionRepository = (TransactionRepository) applicationContext.getBean("otg.IBatisTransactionRepositoryImpl");
        this.notificationRepository = (NotificationRepository) applicationContext.getBean("otg.IBatisNotificationRepositoryImpl");

        this.responseProcedure = (ResponseProcedure) applicationContext.getBean("otg.ResponseProcedure");
        //将MakePaymentService替换成替身，模拟成功、失败、异常三种情况
        responseProcedure.setMakePaymentService((PaymentDispatcher) applicationContext.getBean("otg.MakePaymentServiceFactoryStub"));

        txnNoRequested = new TransactionNo("20110427090166" + new DecimalFormat("00").format(Long.valueOf(channel)));
    }

    private NotificationReceiver getReceiver(String beanName) {
        return (NotificationReceiver) applicationContext.getBean(beanName);
    }

    public HttpServletRequest givesANotificationWith(String notificationHttpServletRequestAssemblerBeanName) throws Exception {
        notificationClearer.clearSpecifiedData();
        givesAll.clearSpecifiedData();
        givesAll.prepareData();
        givesAll.appendData();
        NotificationHttpServletRequestAssembler notificationHttpServletRequestAssembler = (NotificationHttpServletRequestAssembler) applicationContext.getBean(notificationHttpServletRequestAssemblerBeanName);;
        return notificationHttpServletRequestAssembler.assemble(this.getTransaction());
    }

    public void switchToProductionValidator() {
        Map<String, AbstractNotificationFactory> notificationFactories = applicationContext.getBeansOfType(AbstractNotificationFactory.class);

        Iterator<String> notificationFactoryNames = notificationFactories.keySet().iterator();
        while (notificationFactoryNames.hasNext()) {
            AbstractNotificationFactory factory = notificationFactories.get(notificationFactoryNames.next());

            if (channel.equals(factory.getChannel())) {
                factory.setNotificationValidator(productValidator);
            }
        }
    }

    public void switchToAlwaysPassValidator() {
        Map<String, AbstractNotificationFactory> notificationFactories = applicationContext.getBeansOfType(AbstractNotificationFactory.class);

        Iterator<String> notificationFactoryNames = notificationFactories.keySet().iterator();
        while (notificationFactoryNames.hasNext()) {
            AbstractNotificationFactory factory = notificationFactories.get(notificationFactoryNames.next());

            if (channel.equals(factory.getChannel())) {
                productValidator = factory.getNotificationValidator();//保存引用用来switch回去时用
                factory.setNotificationValidator(new AlwaysPassNotificationValidator());
            }
        }
    }

    public void receive(HttpServletRequest givesANotificationWithBadAmount) throws Exception {
        this.notificationReceiver.handleRequest(givesANotificationWithBadAmount, new MockHttpServletResponse());
        Thread.sleep(1000);

    }

    public void receiveConcurrently(HttpServletRequest givesANotificationWithBadAmount) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(RECEIVING_COUNT);
        Collection<Callable<Object>> callables = new ArrayList<Callable<Object>>();
        for (int i = 0; i < RECEIVING_COUNT; i++) {
            callables.add(new Receiving(givesANotificationWithBadAmount, notificationReceiver));
        }
        executor.invokeAll(callables);
        Thread.sleep(5000);
    }

    public Transaction getTransaction() {
        return transactionRepository.find(txnNoRequested);
    }

    public List<Notification> getNotifications() {
        return notificationRepository.find(txnNoRequested);
    }

    class Receiving implements Callable<Object> {

        private HttpServletRequest request;
        private NotificationReceiver notificationReceiver;

        public Receiving(HttpServletRequest request, NotificationReceiver notificationReceiver) {
            this.request = request;
            this.notificationReceiver = notificationReceiver;
        }

        @Override
        public Object call() throws Exception {
            return this.notificationReceiver.handleRequest(request, new MockHttpServletResponse());
        }
    }
}
