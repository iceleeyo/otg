/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.IdentityType;
import junit.framework.Assert;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.*;
import org.junit.runner.RunWith;

/**
 *
 * @author 006874
 */
@RunWith(JMock.class)
public class ApplicatonExceptionMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private UnavailablePaymentApplicationException unavailablePaymentApplicationException;
    private UnavailableChannelException unavailableChannelException;
    private UnavailableMerchantException unavailableMerchantException;
    private UnavailablePartnerException unavailablePartnerException;
    private UnavailableCurrencyException unavailableCurrencyException;
    private UnavailableGatewayException unavailableGatewayException;
    private CannotMapIdentityTypeException cannotMapIdentityTypeException;
    private CannotMakePaymentException cannotMakePaymentException;
    private CannotChangeCheckingStateException cannotChangeCheckingStateException;

    @Before
    public void onSetUp() {
        unavailablePaymentApplicationException = new UnavailablePaymentApplicationException("");
        unavailableChannelException = new UnavailableChannelException("");
        unavailableMerchantException = new UnavailableMerchantException("", "");
        unavailablePartnerException = new UnavailablePartnerException("");
        unavailableCurrencyException = new UnavailableCurrencyException("", null);
        unavailableGatewayException = new UnavailableGatewayException("", "");
        cannotMapIdentityTypeException = new CannotMapIdentityTypeException(null, IdentityType.PASSPORT);
        cannotMakePaymentException = new CannotMakePaymentException(new Transaction(), "");
        cannotChangeCheckingStateException = new CannotChangeCheckingStateException(CheckingState.VALID, CheckingState.DEAD);
    }

    @Test
    public void unavailablePaymentApplicationException() {
        String code = unavailablePaymentApplicationException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_PAYMENT_APP));
    }

    @Test
    public void unavailableChannelException() {
        String code = unavailableChannelException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_CHANNEL));
    }

    @Test
    public void unavailableMerchantException() {
        String code = unavailableMerchantException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_MERCHANT));
    }

    @Test
    public void unavailablePartnerException() {
        String code = unavailablePartnerException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_PARTNER));
    }

    @Test
    public void unavailableCurrencyException() {
        String code = unavailableCurrencyException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_CURRENCY));
    }

    @Test
    public void unavailableGatewayException() {
        String code = unavailableGatewayException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_GATEWAY));
    }

    @Test
    public void cannotMapIdentityTypeException() {
        String code = cannotMapIdentityTypeException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.UNAVAILABLE_CARD_HOLDER_ID_TYPE));
    }

    @Test
    public void cannotMakePaymentException() {
        String code = cannotMakePaymentException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.CANNOT_MAKE_PAYMENT));
    }
    
    @Test
    public void cannotChangeCheckingState() {
        String code = cannotChangeCheckingStateException.getStatusCode();
        Assert.assertTrue(code.equals(StatusCode.CANNOT_CHANGE_CHECKING_STATE));
    }
}
