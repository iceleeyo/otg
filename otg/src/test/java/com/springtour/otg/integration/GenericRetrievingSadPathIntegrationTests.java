package com.springtour.otg.integration;

import com.springtour.otg.*;
import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.application.exception.DuplicateResponseException;


import org.junit.Assert;

public class GenericRetrievingSadPathIntegrationTests extends AbstractOtgIntegrationTests {

    private RetrievingTestDriver driver;

    public GenericRetrievingSadPathIntegrationTests() {
        super();
    }

    @Override
    public void onSetUp() throws Exception {
        driver = new RetrievingTestDriver(super.getApplicationContext());
    }

    public void testMakesPaymentButFailsGivenATransactionWasResponsedSuccess() throws Exception {
        driver.givesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment();

        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());
        Exception e = null;
        try {
            driver.retryPaymentMaking();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof CannotMakePaymentException);
        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());
    }

    public void testMakesPaymentButErrorGivenATransactionWasResponsedSuccess() throws Exception {
        driver.givesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment();

        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());

        Exception e = null;
        try {
            driver.retryPaymentMaking();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof CannotMakePaymentException);
        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());
    }

    public void testMakesPaymentButErrorGivenATransactionWasRequested() throws Exception {
        driver.givesATransactionWasRequested();

        Assert.assertTrue(driver.getTransaction().isRequested());

        Exception e = null;
        try {
            driver.retryPaymentMaking();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalStateException);
        Assert.assertTrue(driver.getTransaction().isRequested());
    }

    public void testMakesPaymentButErrorGivenATransactionWasResponsedFailure() throws Exception {
        driver.givesATransactionWasChargedFailure();

        Assert.assertTrue(driver.getTransaction().isChargedFailure());

        Exception e = null;
        try {
            driver.retryPaymentMaking();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalStateException);
        Assert.assertTrue(driver.getTransaction().isChargedFailure());
    }

    public void testMakesPaymentButErrorGivenATransactionWasConcluded() throws Exception {
        driver.givesATransactionWasConcluded();

        Assert.assertTrue(driver.getTransaction().isConcluded());

        Exception e = null;
        try {
            driver.retryPaymentMaking();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IllegalStateException);
        Assert.assertTrue(driver.getTransaction().isConcluded());
    }

    public void testRehandlesGivenATransactionWasResponsedSuccess() throws Exception {
        driver.givesATransactionWasResponsedSuccess();

        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());
        Exception e = null;
        try {
            driver.handMakeAChargedNotification();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof DuplicateResponseException);
        Assert.assertTrue(driver.getTransaction().isResponsedSuccess());
    }

    public void testRehandlesGivenATransactionWasConcluded() throws Exception {
        driver.givesATransactionWasConcluded();

        Assert.assertTrue(driver.getTransaction().isConcluded());

        Exception e = null;
        try {
            driver.handMakeAChargedNotification();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof DuplicateResponseException);
        Assert.assertTrue(driver.getTransaction().isConcluded());
    }

    public void testRehandlesGivenATransactionWasChargedFailure() throws Exception {
        driver.givesATransactionWasChargedFailure();

        Assert.assertTrue(driver.getTransaction().isChargedFailure());

        Exception e = null;
        try {
            driver.handMakeAChargedNotification();
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof DuplicateResponseException);
        Assert.assertTrue(driver.getTransaction().isChargedFailure());
    }
}
