/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import junit.framework.Assert;
import org.dom4j.DocumentException;

/**
 *
 * @author Future
 */
public class SpringCardRequestWithoutCardInfoHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private NotificationHttpServletRequestAssembler requestAssembler;
    private final String channelId = "2";
    private DataPrepareOperation dataPrepareOperation;
    
    public SpringCardRequestWithoutCardInfoHappyPathIntegrationTests() {
        super();
    }
    
    @Override
    public void onSetUp() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.SpringCardOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        testEngine = new TestEngine(channelId, super.getApplicationContext());
        requestAssembler = (NotificationHttpServletRequestAssembler) super.getApplicationContext().getBean("otg.SpringCardNotificationHttpServletRequestAssemblerImpl");
    }
    
    public void testProcedure() throws Exception {
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        System.out.println(testEngine.getRequestWithoutCardInfoRs().getRequestUrl());
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.SUCCESS));
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        //接收发送
//        testEngine.receiveNotificationWithoutCardInfo(false, requestAssembler);
//        Assert.assertTrue(testEngine.findTransactionWithoutCardInfo().isConcluded());
    }
}
