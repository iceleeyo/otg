package com.springtour.otg.uat;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.integration.AbstractOtgIntegrationTests;
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

public class ChannelAdminHappyPathUserAcceptanceTests extends AbstractOtgIntegrationTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private WebDriver driver;
    private Configurations configurations;
    private DataPrepareOperation dataBuilder;

    public ChannelAdminHappyPathUserAcceptanceTests() {
        super();
    }

    @Before
    @Override
    public void onSetUp() throws Exception {
        this.driver = new InternetExplorerDriver();
        dataBuilder = (DataPrepareOperation) super.getApplicationContext().getBean("otg.ChannelAdminDataBuilder");
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
    public void testUpdatesAvailableCurrenciesThenRefreshes() throws Exception {
        final String channelId = "12";//汇付线下
        final String availableCurrencies = "CNY,USD";

        ChannelAdminPage channelAdminPage = PageFactory.initElements(driver, ChannelAdminPage.class);
        channelAdminPage.open(driver, configurations.localApplicationUrl());
        channelAdminPage.query(channelId);
        channelAdminPage.switchToUpateAvailableCurrenciesForm();
        channelAdminPage.updateAvailableGateways(availableCurrencies);
        channelAdminPage.waitThenConfirm(1000L);

        Assert.assertEquals(availableCurrencies, channelAdminPage.getAvailableCurrencies());
    }

    @Test
    public void testAddsAGatewayThenRefreshes() throws Exception {
        final String channelId = "15";//快钱
        final String gatewayCode = "CMB";
        final String dialect = "Bill99_CMB";
        final int priority = 1;

        ChannelAdminPage channelAdminPage = PageFactory.initElements(driver, ChannelAdminPage.class);
        channelAdminPage.open(driver,configurations.localApplicationUrl());
        channelAdminPage.query(channelId);
        channelAdminPage.switchToSaveGatewayForm();
        channelAdminPage.saveGateway(gatewayCode, dialect, priority);

        Assert.assertEquals("保存成功！", channelAdminPage.waitThenConfirm(1000L));
    }

    @Test
    public void testRemovesAGatewayThenRefreshes() throws Exception {
        final String channelId = "12";//汇付线下
        final String gatewayCode = "CCB";

        ChannelAdminPage channelAdminPage = PageFactory.initElements(driver, ChannelAdminPage.class);
        channelAdminPage.open(driver, configurations.localApplicationUrl());
        channelAdminPage.query(channelId);
        channelAdminPage.removeGateway(gatewayCode);

        Assert.assertEquals("保存成功！", channelAdminPage.waitThenConfirm(1000L));
    }
}
