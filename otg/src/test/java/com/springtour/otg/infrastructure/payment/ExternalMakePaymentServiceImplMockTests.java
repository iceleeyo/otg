package com.springtour.otg.infrastructure.payment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.domain.model.transaction.HandlingActivity;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.infrastructure.mail.MailManager;
import com.springtour.util.DateUtils;

@RunWith(JMock.class)
public class ExternalMakePaymentServiceImplMockTests {
	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private ExternalMakePaymentServiceImpl target;
	private MakeAutoPaymentService makeAutoPaymentService = context
			.mock(MakeAutoPaymentService.class);
	private MailManager mailManager = context.mock(MailManager.class);
	final SimpleDateFormat sdf = DateUtils.simpleDateFormatDetail();
	final String transactionNo = "1";
	Transaction transaction = null;
	final String title = "客户付款成功，旅游系统订单处理失败！所属应用为：0";
	final String content = "旅游系统交易流水号为：【" + transactionNo + "】，请人工处理该笔交易！";
	
	@Before
	public void init() {
		target = new ExternalMakePaymentServiceImpl();
		target.setMailManager(mailManager);
		target.setMakeAutoPaymentService(makeAutoPaymentService);
		HandlingActivity handlingActivity = new HandlingActivity();
		handlingActivity.setWhenHandled(new Date());
		transaction = new TransactionFixture().specify(transactionNo).specify(handlingActivity).build();
	}
	
	@Test
	public void testPaymentWhenSuccess(){

		context.checking(new Expectations() {

			{
				{

					oneOf(makeAutoPaymentService)
							.makePayment(
									transaction.getOrderId().getOrderId(),
									String.valueOf(transaction.getAmount()
											.getAmount()),
									transaction.getAmount().getCurrencyCode(),
									transaction.getTransactionNo().getNumber(),
									String.valueOf(transaction.getChannel()
											.getId()),
									sdf.format(transaction.getHandlingActivity()
											.getWhenHandled()),
									transaction.getMerchantCode(), transaction.getChargeFor());
					will(returnValue("0"));

				}
			}
		});
		target.makePayment(transaction);
	}
	
	@Test(expected = CannotMakePaymentException.class)
	public void testPaymentWhenFailure(){
		
		context.checking(new Expectations() {

			{
				{
						
					oneOf(makeAutoPaymentService)
							.makePayment(
									transaction.getOrderId().getOrderId(),
									String.valueOf(transaction.getAmount()
											.getAmount()),
									transaction.getAmount().getCurrencyCode(),
									transaction.getTransactionNo().getNumber(),
									String.valueOf(transaction.getChannel()
											.getId()),
									sdf.format(transaction.getHandlingActivity()
											.getWhenHandled()),
									transaction.getMerchantCode(),transaction.getChargeFor());
					will(returnValue("-1"));

					oneOf(mailManager).send(title, content, "0");

				}
			}
		});
		target.makePayment(transaction);
	}
	
	@Test(expected = CannotMakePaymentException.class)
	public void testPaymentWhenThrowException(){
		
		context.checking(new Expectations() {

			{
				{

					oneOf(makeAutoPaymentService)
							.makePayment(
									transaction.getOrderId().getOrderId(),
									String.valueOf(transaction.getAmount()
											.getAmount()),
									transaction.getAmount().getCurrencyCode(),
									transaction.getTransactionNo().getNumber(),
									String.valueOf(transaction.getChannel()
											.getId()),
									sdf.format(transaction.getHandlingActivity()
											.getWhenHandled()),
									transaction.getMerchantCode(),transaction.getChargeFor());
					will(throwException(new NullPointerException()));

				}
				
				oneOf(mailManager).send(title, content, "0");
			}
		});
		target.makePayment(transaction);
	}
	
}
