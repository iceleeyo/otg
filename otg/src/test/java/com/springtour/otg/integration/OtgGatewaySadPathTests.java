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
public class OtgGatewaySadPathTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public OtgGatewaySadPathTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
    }

    public void testGatewayNull() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setGateway(null);
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 应当为成功
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.SUCCESS));
    }
    
    public void testGatewayEmpty() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setGateway("");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 应当为成功
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.SUCCESS));
    }
    
    public void testGatewayError() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setGateway("ICBC");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNAVAILABLE_GATEWAY));
    }
}
