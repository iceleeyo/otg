package com.springtour.otg.infrastructure.payment;

import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.domain.service.MakePaymentService;
import java.util.Arrays;

@RunWith(JMock.class)
public class PaymentDispatcherMockTests /* extends JUnit4Mockery */ {
    // {
    // setImposteriser(ClassImposteriser.INSTANCE);
    // }

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private PaymentDispatcher target;
    private Transaction transaction = context.mock(
            Transaction.class);
    // @Mock
    private MakePaymentService makePaymentService1 = context.mock(
            MakePaymentService.class, "makePaymentService1");
    // @Mock
    private MakePaymentService makePaymentService2 = context.mock(
            MakePaymentService.class, "makePaymentService2");
    private MakePaymentService makePaymentService3 = context.mock(
            MakePaymentService.class, "makePaymentService3");

    @Before
    public void setUp() {
        target = new PaymentDispatcher();
        target.setServices(Arrays.asList(makePaymentService1, makePaymentService2, makePaymentService3));
    }

    @Test
    public void returnsTrueWhenAtLeastOneMatched() throws Exception {
        final String application = "1";
        context.checking(new Expectations() {

            {
                oneOf(makePaymentService1).support(application);
                will(returnValue(false));

                oneOf(makePaymentService2).support(application);
                will(returnValue(true));
            }
        });
        boolean result = target.support(application);

        Assert.assertTrue(result);
    }

    @Test
    public void returnsFalseWhenNoneMatched() throws Exception {
        final String application = "1";
        context.checking(new Expectations() {

            {
                oneOf(makePaymentService1).support(application);
                will(returnValue(false));

                oneOf(makePaymentService2).support(application);
                will(returnValue(false));

                oneOf(makePaymentService3).support(application);
                will(returnValue(false));
            }
        });
        boolean result = target.support(application);

        Assert.assertTrue(!result);
    }

    @Test
    public void returnsFalseGivenNull() throws Exception {

        final String application = null;
        context.checking(new Expectations() {

            {
                oneOf(makePaymentService1).support(application);
                will(returnValue(false));

                oneOf(makePaymentService2).support(application);
                will(returnValue(false));

                oneOf(makePaymentService3).support(application);
                will(returnValue(false));
            }
        });
        boolean result = target.support(application);

        Assert.assertTrue(!result);
    }

    @Test
    public void delegatesToASupportable() throws Exception {
        final String application = "1";
        final OrderIdentity orderId = OrderIdentity.valueOf(application, "1", "2");

        context.checking(new Expectations() {

            {
                oneOf(transaction).getOrderId();
                will(returnValue(orderId));

                oneOf(makePaymentService1).support(application);
                will(returnValue(false));

                oneOf(makePaymentService2).support(application);
                will(returnValue(true));



                oneOf(makePaymentService2).makePayment(transaction);
            }
        });
        target.makePayment(transaction);
    }

    @Test(expected = UnavailablePaymentApplicationException.class)
    public void throwsAnExceptionWhenNoneSupportsTheApplication() throws Exception {
        final String application = "1";
        final OrderIdentity orderId = OrderIdentity.valueOf(application, "1", "2");

        context.checking(new Expectations() {

            {
                oneOf(transaction).getOrderId();
                will(returnValue(orderId));

                oneOf(makePaymentService1).support(application);
                will(returnValue(false));

                oneOf(makePaymentService2).support(application);
                will(returnValue(false));

                oneOf(makePaymentService3).support(application);
                will(returnValue(false));
            }
        });
        target.makePayment(transaction);
    }
}
