package com.springtour.otg.uat;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.integration.AbstractOtgIntegrationTests;
import java.util.*;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

public class PartnerAdminHappyPathUserAcceptanceTests extends AbstractOtgIntegrationTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private WebDriver driver;
    private Configurations configurations;
    private DataPrepareOperation dataBuilder;

    public PartnerAdminHappyPathUserAcceptanceTests() {
        super();
    }

    @Before
    @Override
    public void onSetUp() throws Exception {
        this.driver = new InternetExplorerDriver();
        dataBuilder = (DataPrepareOperation) super.getApplicationContext().getBean("otg.PartnerAdminDataBuilder");
        dataBuilder.appendData();
        dataBuilder.prepareData();//初始化gateways，全删全插

        configurations = (Configurations) super.getApplicationContext().getBean("otg.TestConfigurations");
    }

    @After
    @Override
    public void onTearDown() throws Exception {
        this.driver.quit();
    }

    @Test
    public void testUpdatesAvailableChannelsThenRefreshes() throws Exception {
        final String partnerId = "1";
        final List<String> expectedChannelIds = Arrays.asList("12");
        final List<String> expectedChannelIdsToBeSaved = Arrays.asList("5", "12");

        PartnerAdminPage partnerAdminPage = PageFactory.initElements(driver, PartnerAdminPage.class);
        partnerAdminPage.open(driver, configurations.localApplicationUrl());
        partnerAdminPage.query(partnerId);

        Assert.assertEquals(expectedChannelIds, partnerAdminPage.getActualChannelIds());

        AvailableChannelsPage availableChannelsPage = partnerAdminPage.switchToAvailableChannelsPage();

        Assert.assertEquals(expectedChannelIds, availableChannelsPage.getActualChannelIds());

        PartnerAdminPage refreshedPartnerAdminPage = availableChannelsPage.saveAvailableChannelsThenRefreshParent(expectedChannelIdsToBeSaved);

        Assert.assertEquals(expectedChannelIdsToBeSaved, refreshedPartnerAdminPage.getActualChannelIds());
    }

    @Test
    public void testUpdatesRecommendedGatewaysThenRefreshes() throws Exception {
        final String partnerId = "1";
        final List<String> expectedChannelIds = Arrays.asList("12");
        final String channelId = "5";
        final String gatewayTenpay = "TENPAY";
        final String gatewayAbc = "ABC";
        final List<String> expectedGatewayCodesToAdd = Arrays.asList(gatewayTenpay,gatewayAbc);

        PartnerAdminPage partnerAdminPage = PageFactory.initElements(driver, PartnerAdminPage.class);
        partnerAdminPage.open(driver, configurations.localApplicationUrl());
        partnerAdminPage.query(partnerId);

        Assert.assertEquals(expectedChannelIds, partnerAdminPage.getActualChannelIds());

        RecommendedGatewaysPage recommendedGatewaysPage = partnerAdminPage.switchToRecommendedGatewaysPage();

        Assert.assertEquals(expectedChannelIds, recommendedGatewaysPage.getAvaiableChannelIds());

        recommendedGatewaysPage.addRecommendedGateways(channelId, expectedGatewayCodesToAdd);
        
        recommendedGatewaysPage.up(channelId, gatewayAbc);
        
        recommendedGatewaysPage.down(channelId, gatewayAbc);
        
        recommendedGatewaysPage.remove(channelId, gatewayTenpay);
        
        Assert.assertEquals("保存成功！", recommendedGatewaysPage.submit());
        //Assert.assertEquals(expectedChannelIds, refreshedPartnerAdminPage.getActualChannelIds());
    }
}
