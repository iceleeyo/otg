/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithCardInfoRq;
import org.dom4j.DocumentException;
import org.junit.Assert;

/**
 *
 * @author Future
 */
public class Bill99RequestWithCardInfoHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private NotificationHttpServletRequestAssembler requestAssembler;
    private final String channelId = "15";
    private DataPrepareOperation dataPrepareOperation;

    public Bill99RequestWithCardInfoHappyPathIntegrationTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
        requestAssembler = (NotificationHttpServletRequestAssembler) super.getApplicationContext().getBean("otg.Bill99NotificationHttpServletRequestAssemblerImpl");

        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.Bill99RequestHappyPath");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
    }

    public void testProcedure() throws Exception {
//        testEngine.availableGateways();
//        Assert.assertEquals(StatusCode.SUCCESS, testEngine.getAvailableGatewaysRs().getStatusCode());

        //请求交易
        testEngine.requestWithCardInfo(testEngine.aGoodRequestWithCardInfoRq());
        Assert.assertEquals(StatusCode.SUCCESS, testEngine.getRequestWithCardInfoRs().getStatusCode());
        Assert.assertTrue(testEngine.findTransaction().isRequested());

        //接收发送
        //testEngine.receiveNotification(false, requestAssembler);
       // Assert.assertTrue(testEngine.findTransaction().isConcluded());
    }
    
    public void testOther() throws Exception { 
         //请求交易
        RequestWithCardInfoRq rq = testEngine.aGoodRequestWithCardInfoRq();
        rq.setOrgId("65");
        testEngine.requestWithCardInfo(rq);
        Assert.assertEquals(StatusCode.SUCCESS, testEngine.getRequestWithCardInfoRs().getStatusCode());
        Assert.assertTrue(testEngine.findTransaction().isRequested());
    }
}
 