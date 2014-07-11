package com.springtour.otg.integration;

import com.springtour.otg.TestEngine;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithCardInfoAdapter;
import com.springtour.otg.infrastructure.channel.ChannelDispatcher;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.infrastructure.channel.chinapnr.ChinapnrClientAwaysSucceedStub;
import org.dom4j.DocumentException;
import org.junit.Assert;


import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.dbop.DataPrepareOperation;
import java.util.ArrayList;
import java.util.List;

public class ChinapnrRequestWithCardInfoHappyPathIntegrationTests extends AbstractOtgIntegrationTests {
    
    private TestEngine testEngine;
    private NotificationHttpServletRequestAssembler requestAssembler;
    private final String channelId = "12";
    private DataPrepareOperation dataPrepareOperation;
    
    public ChinapnrRequestWithCardInfoHappyPathIntegrationTests() {
        super();
    }
    
    @Override
    public void onSetUp() throws DocumentException {
        testEngine = new TestEngine(channelId, super.getApplicationContext());
        requestAssembler = (NotificationHttpServletRequestAssembler) super.getApplicationContext().getBean("otg.ChinapnrNotificationHttpServletRequestAssemblerImpl");
        //将配置文件改为正式配置 在测试环境set always succeed的stub
        ChannelDispatcher channelDispatcher = (ChannelDispatcher) super.getApplicationContext().getBean("otg.ChannelDispatcher");
        List<AbstractRequestWithCardInfoAdapter> requestAdapters = new ArrayList<AbstractRequestWithCardInfoAdapter>();
        requestAdapters.add(new ChinapnrClientAwaysSucceedStub(channelId));
        channelDispatcher.setRequestWithCardInfoAdapters(requestAdapters);
        
        dataPrepareOperation = (DataPrepareOperation) super.getApplicationContext().getBean("otg.ChinapnrRequestHappyPath");
        dataPrepareOperation.prepareData();
        dataPrepareOperation.appendData();
    }

    /**
     * 返回一个交易列表，包含推荐网关及其他网关
     */
    // public void returnsAGatewayList() {
    //
    // }
    /**
     * 请求交易时，成功后返回一笔交易
     */
    public void testProcedure() throws Exception {
        //请求交易
        testEngine.requestWithCardInfo(testEngine.aGoodRequestWithCardInfoRq());
        
        Assert.assertEquals(StatusCode.SUCCESS, testEngine.getRequestWithCardInfoRs().getStatusCode());
        Assert.assertTrue(testEngine.findTransaction().isRequested());
        //接收发送
        testEngine.receiveNotification(false, requestAssembler);
        
        Assert.assertTrue(testEngine.findTransaction().isConcluded());
        
       
    }
}
