/**
 * 
 */
package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.OtgTestScenarios;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateway;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;

/**
 * @author Future
 * 
 */
public class Bill99GatewayRequestParametersAssembleUnitTest {

	private Bill99GatewayRequestParametersAssemble target;
	private String returnUrl = "http://192.168.9.101/return.htm";

	@Before
	public void testPrepare() {
		target = new Bill99GatewayRequestParametersAssemble();
		Configurations configurations = new Configurations();
		configurations
				.setBill99GatewayRequestUrl("https://sandbox2.99bill.com/gateway/recvMerchantInfoAction.htm");
		configurations.setBill99GatewayNotifyPath("/notify.htm");
		configurations
				.setGlobalApplicationUrl("http://192.168.9.101/bill99gateway");
		target.setConfigurations(configurations);
		PkiPair pkiPair = new PkiPair();
		pkiPair.setConfigurations(configurations);
		target.setPkiPair(pkiPair);
	}

	@Test
	public void assembleMsg() throws UnavailableCurrencyException,
			UnrecoverableKeyException, InvalidKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, SignatureException,
			IOException {
		Gateway gateway = new Gateway();
		gateway.setCode("default");
		gateway.setDialect("");
		gateway.setPriority(0);
		List<Gateway> gateways = new ArrayList<Gateway>();
		gateways.add(gateway);
		Channel channel = new Channel(
				OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL);
		channel.setGateways(gateways);
		Transaction transaction = new Transaction(new TransactionNo(
				OtgTestScenarios.AN_AVAILABLE_TR_NO), null,
				Money.valueOf(OtgTestScenarios.A_PAYABLE_AMOUNT), Calendar
						.getInstance().getTime(), new OrderIdentity(
						OtgTestScenarios.AN_ALWAYS_SUCCEED_PAYMENT_APP, "111",
						"ABC111"), new Merchant(
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_NAME,
						new Channel(
								OtgTestScenarios.A_PARTNER_AVAILABLE_CAHNNEL),
						OtgTestScenarios.AN_AVAILABLE_MERCHANT_ORG,
						"1001181534101", "123456"), channel, "default");
		String url = target.assembleMsg(transaction, returnUrl);
		System.out.println(url);
	}
}
