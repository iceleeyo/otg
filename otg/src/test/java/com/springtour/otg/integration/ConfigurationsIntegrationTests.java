/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.application.util.Configurations;
import static org.junit.Assert.*;

/**
 * 
 * @author 001595
 */
public class ConfigurationsIntegrationTests extends AbstractOtgIntegrationTests {

	private Configurations target;

	public ConfigurationsIntegrationTests() {
		super();
	}

	@Override
	public void onSetUp() throws Exception {
		target = (Configurations) super.getApplicationContext().getBean(
				"otg.Configurations");
	}

	public void testChinapnr() {
		assertEquals("different request scheme", "http", target
				.getChinapnrRequestScheme());
		assertEquals("different request host", "test.chinapnr.com", target
				.getChinapnrRequestHost());
		assertEquals("different request port", "80", target
				.getChinapnrRequestPort());
		assertEquals("different request path", "/gar/entry.do", target
				.getChinapnrRequestPath());
		System.out.println("notifyUrl:" + target.chinapnrNotifyUrl());
		assertTrue("different notify url", target.chinapnrNotifyUrl().contains(
				"/otg/chinapnr/receive.htm"));
		assertEquals("different public key file",
				"/com/springtour/runtime/otg/PgPubk.key", target
						.chinapnrPublicKeyClasspathResourceName());
	}

	public void testSpringcard() {
		assertEquals("different request url",
				"http://210.51.48.110/stgwback/merInterface.do", target
						.getSpringcardRequestUrl());
		System.out.println("notifyUrl:" + target.springcardNotifyUrl());
		assertTrue("different notify url", target.springcardNotifyUrl()
				.contains("/otg/springcard/receive.htm"));
	}

	public void testTenpay() {
		assertEquals("different request url",
				"https://gw.tenpay.com/gateway/pay.htm", target
						.getTenpayRequestUrl());
		System.out.println("notifyUrl:" + target.tenpayNotifyUrl());
		assertTrue("different notify url", target.tenpayNotifyUrl().contains(
				"/otg/tenpay/receive.htm"));
	}

	public void testAlipay() {
		assertEquals("different request url",
				"https://mapi.alipay.com/gateway.do", target
						.getAlipayRequestUrl());
	}

	public void testBill99() {
		assertEquals("different request url",
				"https://sandbox2.99bill.com:9443/cnp/purchase", target
						.getBill99RequestUrl());
		System.out.println("notifyUrl:" + target.bill99NotifyUrl());
		assertTrue("different notify url", target.bill99NotifyUrl().contains(
				"/otg/bill99/receive.htm"));
		assertEquals("different public key file",
				"/com/springtour/runtime/otg/mgw.cer", target
						.bill99PublicKeyClasspathResourceName());
	}

	public void testCmbc() {
		assertEquals("different request url",
				"https://114.255.47.40/pay/merchant_pay.html", target
						.getCmbcRequestUrl());
		assertEquals("different query url",
				"https://114.255.47.40/pay/merchant_payCheck.html", target
						.getCmbcQueryUrl());
		assertEquals("different private key file",
				"/com/springtour/runtime/otg/cmbc305010601390507.cer", target
						.cmbcPrivateKeyClasspathResourceName());
		assertEquals("different public key file",
				"/com/springtour/runtime/otg/gwkey.cer", target
						.cmbcPublicKeyClasspathResourceName());
	}
}
