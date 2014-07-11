/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.application.impl.NotificationServiceImpl;
import com.springtour.otg.application.impl.SynchronousApplicationEventsImpl;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.payment.PaymentDispatcher;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.channel.AlwaysFailNotificationValidator;
import com.springtour.otg.infrastructure.channel.AlwaysPassNotificationValidator;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.channel.chinapnr.ChinapnrNotificationFactoryImpl;
import com.springtour.otg.interfaces.transacting.facade.RequestHandlerFacade;
import com.springtour.otg.interfaces.transacting.facade.TransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import com.springtour.otg.interfaces.transacting.facade.internal.TransactingServiceFacadeImpl;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithCardInfoRq;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import com.springtour.otg.interfaces.transacting.facade.rs.AvailableGatewaysRs;
import com.springtour.otg.interfaces.transacting.facade.rs.RequestWithCardInfoRs;
import com.springtour.otg.interfaces.transacting.facade.rs.RequestWithoutCardInfoRs;
import com.springtour.otg.interfaces.transacting.web.NotificationReceiver;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author 001595
 */
public class TestEngine {

    private final String channelId;
    //状态数据，用来连接流程中的各个节点
    private final OrderIdentity orderIdentity;
    private AvailableGatewaysRs availableGatewaysRs;
    private RequestWithCardInfoRs requestWithCardInfoRs;
    private RequestWithoutCardInfoRs requestWithoutCardInfoRs;
    //被测试的目标
    private RequestHandlerFacade transactingServiceFacade;
    private NotificationReceiver notificationReceiver;
    private ResponseProcedure responseProcedure;
    //测试替身
    private PaymentDispatcher paymentDispatcher;
    private AbstractNotificationFactory notificationFactory;
    private SynchronousApplicationEventsImpl applicationEvents;
    //数据查询
    private TransactionRepository transactionRepository;
    private NotificationRepository notificationRepository;

    public TestEngine(String channelId, ApplicationContext context) {
        this.channelId = channelId;

        initTestDoubles(context, channelId);
        initTargets(context, channelId);
        injectTestDoubles();
        initFinders(context);

        this.orderIdentity = ((FakeOrderIdentityGenerator) context.getBean("otg.IBatisFakeOrderIdentityGeneratorImpl")).newOrderIdentity();

    }

    private void initTestDoubles(ApplicationContext context, String channelId) {
        Map<String, AbstractNotificationFactory> notificationFactories = context.getBeansOfType(AbstractNotificationFactory.class);

        Iterator<String> notificationFactoryNames = notificationFactories.keySet().iterator();
        while (notificationFactoryNames.hasNext()) {
            AbstractNotificationFactory factory = notificationFactories.get(notificationFactoryNames.next());
            if (factory.getChannel().equals(channelId)) {
                this.notificationFactory = factory;
            }
        }
        paymentDispatcher = (PaymentDispatcher) context.getBean("otg.MakePaymentServiceFactoryStub");

    }

    private void initTargets(ApplicationContext context, String channelId) {
        transactingServiceFacade = (RequestHandlerFacade) context.getBean("otg.RequestHandlerFacadeImpl");

        Map<String, NotificationReceiver> notificationReceivers = context.getBeansOfType(NotificationReceiver.class);
        Iterator<String> notificationReceiverNames = notificationReceivers.keySet().iterator();
        while (notificationReceiverNames.hasNext()) {//将所有的验签都设置为总是通过
            NotificationReceiver receiver = notificationReceivers.get(notificationReceiverNames.next());
            if (receiver.getChannel().equals(channelId)) {
                this.notificationReceiver = receiver;
            }
        }

        responseProcedure = (ResponseProcedure) context.getBean("otg.ResponseProcedure");
        responseProcedure.setMakePaymentService(paymentDispatcher);//将MakePaymentService替换成替身，模拟成功、失败、异常三种情况
    }

    private void injectTestDoubles() {
        responseProcedure.setMakePaymentService(paymentDispatcher);//将MakePaymentService替换成替身，模拟成功、失败、异常三种情况
        notificationFactory.setNotificationValidator(new AlwaysPassNotificationValidator());//将所有的验签都设置为总是通过

    }

    private void initFinders(ApplicationContext context) {
        this.transactionRepository = (TransactionRepository) context.getBean("otg.IBatisTransactionRepositoryImpl");
        this.notificationRepository = (NotificationRepository) context.getBean("otg.IBatisNotificationRepositoryImpl");
    }

    public void availableGateways() {
        //availableGatewaysRs = this.transactingServiceFacade.availableGateways();
    }

    public void requestWithCardInfo(RequestWithCardInfoRq requestWithCardInfoRq) {
        this.requestWithCardInfoRs = this.transactingServiceFacade.requestWithCardInfo(requestWithCardInfoRq);
    }

    public void requestWithoutCardInfo(RequestWithoutCardInfoRq requestWithoutCardInfoRq) {
        this.requestWithoutCardInfoRs = this.transactingServiceFacade.requestWithoutCardInfo(requestWithoutCardInfoRq);
    }

    public Transaction findTransaction() {
        return transactionRepository.find(new TransactionNo(requestWithCardInfoRs.getTransaction().getTransactionNo()));
    }
    
    public Transaction findTransactionWithoutCardInfo() {
        return transactionRepository.find(new TransactionNo(requestWithoutCardInfoRs.getTransaction().getTransactionNo()));
    }

    public void receiveNotification(boolean isFake, NotificationHttpServletRequestAssembler notificationHttpServletRequestAssembler) throws Exception {
        HttpServletRequest request = notificationHttpServletRequestAssembler.assemble(this.requestWithCardInfoRs.getTransaction());
        HttpServletResponse response = new MockHttpServletResponse();
        if (isFake) {
            //将Validator换成总是失败
            this.notificationFactory.setNotificationValidator(new AlwaysFailNotificationValidator());
        }
        notificationReceiver.handleRequest(request, response);//调用handleRequest方法可以验证url mapping

        Thread.sleep(2000);//等待2秒，异步调用
    }
    
    public void receiveNotificationWithoutCardInfo(boolean isFake, NotificationHttpServletRequestAssembler notificationHttpServletRequestAssembler) throws Exception {
        HttpServletRequest request = notificationHttpServletRequestAssembler.assemble(this.requestWithoutCardInfoRs.getTransaction());
        HttpServletResponse response = new MockHttpServletResponse();
        if (isFake) {
            //将Validator换成总是失败
            this.notificationFactory.setNotificationValidator(new AlwaysFailNotificationValidator());
        }
        notificationReceiver.handleRequest(request, response);//调用handleRequest方法可以验证url mapping

        Thread.sleep(2000);//等待2秒，异步调用
    }

    public RequestWithCardInfoRq aGoodRequestWithCardInfoRq() {
        RequestWithCardInfoRq rq = new RequestWithCardInfoRq();
        rq.setPartnerId("1");
        rq.setChannelId(channelId);
        rq.setOrgId(OtgTestScenarios.AN_AVAILABLE_MERCHANT_ORG);
        rq.setApplication(orderIdentity.getApplication());
        rq.setOrderId(orderIdentity.getOrderId());
        rq.setOrderNo(orderIdentity.getOrderNo());
        rq.setAmount(OtgTestScenarios.A_PAYABLE_AMOUNT);
        rq.setCurrency(OtgTestScenarios.CNY);
        rq.setGateway(OtgTestScenarios.AN_AVAILABLE_GATEWAY);

        rq.setCardHolderFullname(OtgTestScenarios.AN_EXIST_CARDHOLDER_FULLNAME);
        rq.setCardHolderIdNo(OtgTestScenarios.AN_EXIST_CARDHOLDER_ID_NO);
        rq.setCardHolderIdType(OtgTestScenarios.AN_EXIST_CARDHOLDER_ID_TYPE);
        rq.setCardNo(OtgTestScenarios.AN_EXIST_CARDNO);
        rq.setCvv2(OtgTestScenarios.AN_EXIST_CVV2);
        rq.setExpireDate(OtgTestScenarios.AN_EXIST_EXPIREDATE);
        return rq;
    }

    public RequestWithoutCardInfoRq aGoodRequestWithoutCardInfoRq() {
        RequestWithoutCardInfoRq rq = new RequestWithoutCardInfoRq();
        rq.setPartnerId("1");
        rq.setChannelId(channelId);
        rq.setOrgId(OtgTestScenarios.AN_AVAILABLE_MERCHANT_ORG);
        rq.setApplication(orderIdentity.getApplication());
        rq.setOrderId(orderIdentity.getOrderId());
        rq.setOrderNo(orderIdentity.getOrderNo());
        rq.setAmount(OtgTestScenarios.A_PAYABLE_AMOUNT);
        rq.setCurrency(OtgTestScenarios.CNY);
        rq.setGateway(OtgTestScenarios.AN_AVAILABLE_GATEWAY);
        rq.setReturnUrl(OtgTestScenarios.AN_AVAILABLE_RETURN_URL_REQUEST_WITHOUTCARD);
        rq.setSurferIp(OtgTestScenarios.AN_AVAILABLE_IP_FOR_TENPAY_REQUEST);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnavailableMerchant() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setOrgId(OtgTestScenarios.A_UNAVAILABLE_MERCHANT_ORG);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnsupportedChannel() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setChannelId(OtgTestScenarios.A_UNSUPPORTED_CAHNNEL);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnavailableChannel() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setChannelId(OtgTestScenarios.A_UNSUPPORTED_CAHNNEL);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnsupportedCurrency() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setCurrency(OtgTestScenarios.A_UNSUPPORTED_CURRENCY);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnrecognizedCurrency() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setCurrency(OtgTestScenarios.A_UNRECOGNIZED_CURRENCY);
        return rq;
    }

    public RequestWithCardInfoRq aRequestWithCardInfoRqGivenUnavailableApplication() {
        RequestWithCardInfoRq rq = aGoodRequestWithCardInfoRq();
        rq.setApplication(OtgTestScenarios.A_UNAVAILABLE_PAYMENT_APP);
        return rq;
    }

    public RequestWithCardInfoRs getRequestWithCardInfoRs() {
        return requestWithCardInfoRs;
    }

    public AvailableGatewaysRs getAvailableGatewaysRs() {
        return availableGatewaysRs;
    }

    public RequestWithoutCardInfoRs getRequestWithoutCardInfoRs() {
        return requestWithoutCardInfoRs;
    }
    
    public void setValidator(NotificationValidator validator) {
        this.notificationFactory.setNotificationValidator(validator);
    }
}
