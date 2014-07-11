package com.springtour.otg.integration;

import com.springtour.otg.*;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.interfaces.transacting.facade.dto.AvailableChannelDto;
import com.springtour.otg.interfaces.transacting.facade.dto.RecommendedGatewayDto;
import com.springtour.otg.interfaces.transacting.facade.rs.AvailableCardHolderIdTypesRs;
import com.springtour.otg.interfaces.transacting.facade.rs.AvailableGatewaysRs;


import org.junit.Assert;

public class GenericRequestingHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private RequestingTestDriver driver;

    public GenericRequestingHappyPathIntegrationTests() {
        super();
    }

    @Override
    public void onSetUp() throws Exception {
        driver = new RequestingTestDriver(super.getApplicationContext());
    }

    public void testReturnsAvailableGatewaysGivenAPartnerWellConfigured() throws Exception {
        driver.givesAPartnerWellConfigured();

        AvailableGatewaysRs rs = driver.availableGateways();

        Assert.assertEquals(StatusCode.SUCCESS, rs.getStatusCode());
        for (RecommendedGatewayDto recommendedGateway: rs.getRecommendedGateways()) {
            System.out.println(recommendedGateway);
        }
        for (AvailableChannelDto AvailableChannel: rs.getAvailableChannels()) {
            System.out.println(AvailableChannel);
        }
    }
    
    public void testReturnsAvailableCardHolderIdTypesGivenAWithCardInfoChannel() throws Exception {
        driver.givesAPartnerWellConfigured();

        AvailableCardHolderIdTypesRs rs = driver.availableCardHolderIdTypes();

        Assert.assertEquals(StatusCode.SUCCESS, rs.getStatusCode());
        for (String type: rs.getIdTypes()) {
            System.out.println(type);
        }
    }
}
