package com.springtour.otg.integration;

import com.springtour.otg.*;


import org.junit.Assert;

public class GenericRetrievingHappyPathIntegrationTests extends AbstractOtgIntegrationTests {

    private RetrievingTestDriver driver;

    public GenericRetrievingHappyPathIntegrationTests() {
        super();
    }

    @Override
    public void onSetUp() throws Exception {
        driver = new RetrievingTestDriver(super.getApplicationContext());
    }

    public void testMakesPaymentThenConcludesGivenATransactionWasResponsedSuccess() throws Exception {
        driver.givesATransactionWasResponsedSuccess();

        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());

        driver.retryPaymentMaking();

        Assert.assertTrue(driver.getTransaction().isConcluded());
    }
    
    public void testRehandlesGivenATransactionWasRequested() throws Exception {
        driver.givesATransactionWasRequested();

        Assert.assertTrue(driver.getTransaction().isRequested());

        driver.handMakeAChargedNotification();
        
        Assert.assertTrue(driver.getTransaction().isConcluded());
    }
}
