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
public class OtgOrganizationSadPathTests extends AbstractOtgIntegrationTests {

    private TestEngine testEngine;
    private DataPrepareOperation dataPrepareOperation;
    private final String channelId = "7";

    public OtgOrganizationSadPathTests() {
        super();
    }

    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
    }

    public void testOrgNull() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.AlipayOKRequestTestsDataPrepare");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrgId(null);
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        // TODO 添加分社报错？
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNKNOWN_ERROR));
    }
    
    public void testOrgError() throws DocumentException {
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.OrganizationSadPath");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
        RequestWithoutCardInfoRq rq = testEngine.aGoodRequestWithoutCardInfoRq();
        rq.setOrgId("65");
        //请求交易
        testEngine.requestWithoutCardInfo(rq);
        //System.out.println(testEngine.getRequestWithoutCardInfoRs().getStatusCode());
        Assert.assertTrue(testEngine.getRequestWithoutCardInfoRs().getStatusCode().equals(StatusCode.UNAVAILABLE_MERCHANT));
    }
}
