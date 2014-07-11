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
public class OtgOrderInfoSadPathTests extends AbstractOtgIntegrationTests {
    private TestEngine testEngine;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public OtgOrderInfoSadPathTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
    }

    public void testOrderIdNull() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrgId(null);
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 报错不准确
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNKNOWN_ERROR));
    }
    
    public void testOrderIdEmpty() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrgId("");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 未检查
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNKNOWN_ERROR));
    }
    
    public void testOrderNoNull() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrderNo(null);
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 报错不准确
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNKNOWN_ERROR));
    }
    
    public void testOrderNoEmpty() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrderNo("");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 未检查
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNKNOWN_ERROR));
    }
}
