/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.OtgTestScenarios;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Map;
import junit.framework.Assert;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Future
 */
public class ResolveResponseDataTest {

	private String correctTr2Response;
	private String inCorrectTr2Response;
	private String correctTr3Response;
	private Transaction transaction;
	private Bill99NotificationValidatorImpl bill99NotificationValidatorImpl;

	@Before
	public void prepareResponse() throws Exception {
		Configurations configurations = new Configurations();
		configurations.setBill99PublicKeyFileName("mgw.cer");

		InputStream is = this
				.getClass()
				.getResourceAsStream(
						"/com/springtour/otg/infrastructure/channel/bill99/test_response_file.xml");
		BufferedReader in = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		this.correctTr2Response = sb.toString();
		//
		is = this
				.getClass()
				.getResourceAsStream(
						"/com/springtour/otg/infrastructure/channel/bill99/test_response_file_incorrect.xml");
		in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		this.inCorrectTr2Response = sb.toString();

		transaction = new Transaction(new TransactionNo(
				OtgTestScenarios.AN_AVAILABLE_TR_NO), null,
				Money.valueOf(OtgTestScenarios.A_PAYABLE_AMOUNT), Calendar
						.getInstance().getTime(), new OrderIdentity(
						OtgTestScenarios.AN_ALWAYS_SUCCEED_PAYMENT_APP, "111",
						"ABC111"), new Merchant(
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_NAME,
						new Channel(
								OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL),
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_ORG,
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_CODE, ""),
				new Channel(OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL), "");

		//
		is = this
				.getClass()
				.getResourceAsStream(
						"/com/springtour/otg/infrastructure/channel/bill99/test_response_tr3_file.xml");
		in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		this.correctTr3Response = sb.toString();

		this.bill99NotificationValidatorImpl = new Bill99NotificationValidatorImpl();
		this.bill99NotificationValidatorImpl.setConfigurations(configurations);
	}

	@Test
	public void resolvePurchaseTr2PesponseDataCorrectResponse()
			throws DocumentException {
		Map<String, String> res = ResolveResponseData
				.resolvePurchaseTr2PesponseData(correctTr2Response, transaction);
		Assert.assertTrue("C0".equals(res.get("RespCode")));
	}

	@Test
	public void resolvePurchaseTr2PesponseDataIncorrectResponse()
			throws DocumentException {
		Map<String, String> res = ResolveResponseData
				.resolvePurchaseTr2PesponseData(inCorrectTr2Response,
						transaction);
		Assert.assertTrue("B.BIN.0001".equals(res.get("RespCode")));
	}

	@Test
	public void resolvePurchaseTr3PesponseData() throws DocumentException,
			CannotLaunchSecurityProcedureException {
		Map<String, String> res = ResolveResponseData
				.resolvePurchaseTr3PesponseData(correctTr3Response);
		// System.out.println(res.get("MessageWithoutSignature"));
		Assert.assertTrue(this.bill99NotificationValidatorImpl.validate(res));
	}
}
