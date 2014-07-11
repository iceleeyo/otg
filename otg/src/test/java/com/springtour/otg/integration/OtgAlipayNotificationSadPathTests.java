/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.channel.alipay.AlipayNotificationHttpServletRequestAssemblerErrorImpl;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import org.dom4j.DocumentException;
import org.junit.Assert;

/**
 *
 * @author Future
 */
public class OtgAlipayNotificationSadPathTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private TestEngine otherTestEngine;
    private AlipayNotificationHttpServletRequestAssemblerErrorImpl otherRequestAssembler;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public OtgAlipayNotificationSadPathTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
    }
    
    public void testTradeNoNull() throws DocumentException, Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        otherRequestAssembler.setOutTradeNo(testEngine.getRequestWithoutCardInfoRs().getTransaction().getTransactionNo());
        otherRequestAssembler.setAmount(testEngine.getRequestWithoutCardInfoRs().getTransaction().getAmount().toString());
        otherRequestAssembler.setTradeNo(null);
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        // TODO 未对外部流水检查
        Assert.assertTrue(testEngine.findTransactionWithoutCardInfo().isConcluded());
    }

    public void testAmountError() throws Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        //System.out.print(testEngine.getRequestWithoutCardInfoRs().getRequestUrl());
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        //接收发送
        otherRequestAssembler.setAmount("-100");
        otherRequestAssembler.setOutTradeNo(testEngine.getRequestWithoutCardInfoRs().getTransaction().getTransactionNo());
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        Assert.assertTrue(!testEngine.findTransactionWithoutCardInfo().isConcluded());
    }

    public void testTransactionError() throws Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayNotificationSadPathDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        otherTestEngine = new TestEngine("5", super.getApplicationContext());

        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        RequestWithoutCardInfoRq rqother = otherTestEngine.aGoodRequestWithoutCardInfoRq();
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        otherTestEngine.requestWithoutCardInfo(rqother);
        //System.out.print(testEngine.getRequestWithoutCardInfoRs().getRequestUrl());
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        Assert.assertTrue(otherTestEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        //接收发送
        otherRequestAssembler.setOutTradeNo(otherTestEngine.getRequestWithoutCardInfoRs().getTransaction().getTransactionNo());
        otherRequestAssembler.setAmount(testEngine.getRequestWithoutCardInfoRs().getTransaction().getAmount().toString());
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        Assert.assertTrue(!testEngine.findTransactionWithoutCardInfo().isConcluded());
        Assert.assertTrue(!otherTestEngine.findTransactionWithoutCardInfo().isConcluded());
    }

    public void testTransactionCanNotFind() throws DocumentException, Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        otherRequestAssembler.setOutTradeNo("-54555554545");
        otherRequestAssembler.setAmount(testEngine.getRequestWithoutCardInfoRs().getTransaction().getAmount().toString());
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        Assert.assertTrue(!testEngine.findTransactionWithoutCardInfo().isConcluded());
    }

    public void testTransactionNull() throws DocumentException, Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        otherRequestAssembler.setOutTradeNo(null);
        otherRequestAssembler.setAmount(testEngine.getRequestWithoutCardInfoRs().getTransaction().getAmount().toString());
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        Assert.assertTrue(!testEngine.findTransactionWithoutCardInfo().isConcluded());
    }

    public void testSignError() throws DocumentException, Exception {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        otherRequestAssembler = new AlipayNotificationHttpServletRequestAssemblerErrorImpl();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        otherRequestAssembler.setOutTradeNo(testEngine.getRequestWithoutCardInfoRs().getTransaction().getTransactionNo());
        otherRequestAssembler.setAmount(testEngine.getRequestWithoutCardInfoRs().getTransaction().getAmount().toString());
        otherRequestAssembler.setSign("123123");
        NotificationValidator validator = (NotificationValidator) super.getApplicationContext().getBean("otg.AlipayNotificationValidatorImpl");
        testEngine.setValidator(validator);
        testEngine.receiveNotificationWithoutCardInfo(false, otherRequestAssembler);
        Assert.assertTrue(!testEngine.findTransactionWithoutCardInfo().isConcluded());
    }
}
