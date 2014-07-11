/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.OtgTestScenarios;
import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.CardHolder;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author 005073
 */
public class GenerateRequestDataTests {

	private String correctTr1Request;
	private String correctTr4Request;

	@Before
	public void prepareRequest() throws UnsupportedEncodingException,
			IOException {
		InputStream is = this
				.getClass()
				.getResourceAsStream(
						"/com/springtour/otg/infrastructure/channel/bill99/test_request_file_correct.xml");
		BufferedReader in = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		line = in.readLine();
		sb.append(line);
		while ((line = in.readLine()) != null) {
			sb.append("\n");
			sb.append(line);
		}
		this.correctTr1Request = sb.toString();
		//
		is = this
				.getClass()
				.getResourceAsStream(
						"/com/springtour/otg/infrastructure/channel/bill99/test_request_tr4_file.xml");
		in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		sb = new StringBuilder();
		line = in.readLine();
		sb.append(line);
		while ((line = in.readLine()) != null) {
			sb.append("\n");
			sb.append(line);
		}
		this.correctTr4Request = sb.toString();
	}

	@Test
	public void generatePurchaseTr1XmlData() throws ParseException,
			UnavailableCurrencyException, CannotMapIdentityTypeException {
		Transaction transaction = new Transaction(new TransactionNo(
				OtgTestScenarios.AN_AVAILABLE_TR_NO), null,
				Money.valueOf(OtgTestScenarios.A_PAYABLE_AMOUNT), Calendar
						.getInstance().getTime(), new OrderIdentity(
						OtgTestScenarios.AN_ALWAYS_SUCCEED_PAYMENT_APP, "111",
						"ABC111"), new Merchant(
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_NAME,
						new Channel(
								OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL),
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_ORG, "00002033",
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_CODE
								+ ",99billpassword"), new Channel(
						OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL), "");
		CardInfo cardInfo = new CardInfo(OtgTestScenarios.AN_EXIST_CARDNO,
				OtgTestScenarios.AN_EXIST_EXPIREDATE,
				OtgTestScenarios.AN_EXIST_CVV2, new CardHolder(
						OtgTestScenarios.AN_EXIST_CARDHOLDER_FULLNAME,
						OtgTestScenarios.AN_EXIST_CARDHOLDER_ID_NO,
						OtgTestScenarios.AN_EXIST_CARDHOLDER_ID_TYPE));
		String res = GenerateRequestData.generatePurchaseTr1XmlData(
				transaction, cardInfo, "http://localhost:7001/returnUrl.htm",
				new SimpleDateFormat("yyyyMMddHHmmss").parse("20120210144540"));
		Assert.assertTrue(res.equals(this.correctTr1Request));
	}

	@Test
	public void generatePurchaseTr4XmlData() {
		String merchatCode = "10101061";
		String terminalId = "10611006511000290,password";
		String refTxn = "131231312312";
		String res = GenerateRequestData.generatePurchaseTr4XmlData(
				merchatCode, terminalId, refTxn);
		Assert.assertTrue(res.equals(this.correctTr4Request));
	}
}
