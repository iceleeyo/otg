package com.springtour.otg.interfaces.transacting.facade.internal;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import chinapnr.SecureLink;

import com.springtour.otg.application.TransactingService;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.interfaces.transacting.facade.ChinapnrVoicePayTransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.arg.ChinapnrVoicePayRequestArgument;

public class ChinaPNRVoicePayTransactingServiceFacadeImplSpringTest extends
		AbstractDependencyInjectionSpringContextTests {

	private ChinapnrVoicePayTransactingServiceFacade chinapnrVoicePayTransactingService;

	private TransactingService transactingService;

	private ApplicationContext context;

	private static String orderNo = "AAA12345";
	private static String email = "hippom@gmail.com";
	private static String mobile = "13524380250";

	private static String gateway = "T5";

	
	@Override
	public void onSetUp() {
		context = this.getApplicationContext();
		chinapnrVoicePayTransactingService = (ChinapnrVoicePayTransactingServiceFacade) context
				.getBean("otg.ChinapnrVoicePayTransactingServiceFacadeImpl");
		transactingService = (TransactingService) context
				.getBean("otg.TransactingServiceImpl");
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "com/springtour/otg/applicationContextTest.xml",
				"com/springtour/otg/otg.xml" };
	}

	@Test
	public void testPostInBoundGroup() throws UnsupportedEncodingException {
		final String orderIdNumber = "390331";
		final String application = "1";
		final String channelId = "12";
		String amount = "1.00";
		BigDecimal balanceDue = new BigDecimal(amount);
		String idType = "00";
		String idNo = "310110198505163751";
		String cardNo = "4392250027998730";
		final OrderIdentity orderId = new OrderIdentity(application,
				orderIdNumber, orderNo);
		Merchant merchant = null;
		Transaction transaction = null;
		ChinapnrVoicePayRequestArgument arg = new ChinapnrVoicePayRequestArgument();
		SecureLink link = new SecureLink();
		final String keyfilePath = this.getClass().getResource(
				"/com/springtour/otg/PgPubk.key").getPath();

		// 锟斤拷锟铰癸拷锟斤拷锟斤拷锟矫匡拷锟斤拷息,注锟解保锟斤拷
		
		

		String cardInfo = "5309700017275076" + "|0414" + "|034" + "|00"
				+ "|310109198006194419" + "|"+new String("钁ｆ枌".getBytes("GBK"),"GBK");
		System.out.println(cardInfo);
		link.EncOrderMsg(keyfilePath, cardInfo);
		arg.setCardInfo(link.getEncMsg());
		arg.setTransactionNo(transaction.getTransactionNo().getNumber());
		 System.out.println(chinapnrVoicePayTransactingService.request(arg));
	}
//
//	public void test() {
//		SecureLink link = new SecureLink();
//		final String keyfilePath = this.getClass().getResource(
//				"/com/springtour/otg/PgPubk.key").getPath();
//
//		link.EncOrderMsg(keyfilePath,
//				"5309700017275076|0414|034|00|310109198006194419|dongbing");
//
//		System.out.println(link.getEncMsg());
//
//	}
	// @Test
	// public void testPostOutBoundGroup() {
	// final String channelId = "12";
	// final String orderIdNumber = "115162";
	// final String application = "2";
	// final OrderIdentity orderId = new OrderIdentity(application,
	// orderIdNumber, orderNo);
	// final Contact contact = new Contact(email, mobile);
	// Merchant merchant = transactingService.findMerchant(channelId, "61");
	// Transaction transaction = transactingService.request(
	// merchant.getCode(), channelId, balanceDue, orderId, contact,
	// chargeFor, gateway);
	// ChinapnrVoicePayRequestArgument arg = new
	// ChinapnrVoicePayRequestArgument();
	// SecureLink link = new SecureLink();
	// final String keyfilePath = this.getClass().getResource(
	// "/com/springtour/otg/PgPubk.key").getPath();
	// link.EncOrderMsg(keyfilePath, "4367481111111111");
	// System.out.println(link.getEncMsg());
	// arg.setCardInfo(link.getEncMsg());
	// arg.setTransactionNo(transaction.getTransactionNo().getNumber());
	// System.out.println(chinapnrVoicePayTransactingService.request(arg));
	// }
}
