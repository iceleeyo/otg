/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.interfaces.transacting.facade.rq.RequestWithoutCardInfoRq;
import org.dom4j.DocumentException;
import org.junit.Assert;

/**
 *
 * @author Future
 */
public class OtgChannelSadPathTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public OtgChannelSadPathTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
    }

    public void testChannelNull() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setChannelId(null);
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 增加对NULL的检查
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNAVAILABLE_CHANNEL));
    }  

    public void testChannelCanNotFind() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.ChannelSadPath");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setChannelId("9");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNAVAILABLE_CHANNEL));
    }
    
    public void testChannelNotSupportedByPartner() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.ChannelSadPath");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setChannelId("7");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNAVAILABLE_CHANNEL));
    }
}
