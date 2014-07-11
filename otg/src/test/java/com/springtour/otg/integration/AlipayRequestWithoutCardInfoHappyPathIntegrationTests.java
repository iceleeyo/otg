/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import junit.framework.Assert;
import org.dom4j.DocumentException;

/**
 *
 * @author Future
 */
public class AlipayRequestWithoutCardInfoHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private NotificationHttpServletRequestAssembler requestAssembler;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public AlipayRequestWithoutCardInfoHappyPathIntegrationTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        testEngine = new TestEngine(channelId, super.getApplicationContext());
        requestAssembler = (NotificationHttpServletRequestAssembler) super.getApplicationContext().getBean("otg.AlipayNotificationHttpServletRequestAssemblerImpl");
    }

    public void testProcedure() throws Exception {
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        System.out.print(testEngine.getRequestWithoutCardInfoRs().getRequestUrl());
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getRequestUrl() != null);
        //接收发送
//        testEngine.receiveNotificationWithoutCardInfo(false, requestAssembler);
//        Assert.assertTrue(testEngine.findTransactionWithoutCardInfo().isConcluded());
    }
}
