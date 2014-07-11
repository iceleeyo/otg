package com.springtour.otg.infrastructure.channel.bill99pos;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.springtour.otg.application.util.Configurations;

public class Bill99PosNotificationValidatorImplUnitTests {
	private Bill99PosNotificationValidatorImpl target;
	
	@Test
	public void testValidator(){
		target = new Bill99PosNotificationValidatorImpl();
		Configurations configurations = new Configurations();
		configurations.setBill99PosPublicKeyFileName("mgw.cer");
		target.setConfigurations(configurations);
		Map<String, String> originalNotification = new HashMap<String, String>();
		originalNotification.put("authCode", "376878");
		originalNotification.put("txnType", "PUR");
		originalNotification.put("shortPAN", "9206031684");
		originalNotification.put("processFlag", "0");
		originalNotification.put("cardType", "0000");
		originalNotification.put("RRN", "000008711464");
		originalNotification.put("signature", "V4uq/lFhiLciGdhsCBX8OWC4X+SI1WeSc9H21Z8oy3/CXuhapGX/F7kVSxoDqC/8EhW/R7mQmLgH1c/6AbJ4c5cOLDhLuRQSSWLzBYcI9IEVE8ukleAL9P++rlAks5MxvJAoghwkYw/Ry/N8vAzCY7T8iehTk3WwwllYUOamKP4=");
		originalNotification.put("issuerId", "");
		originalNotification.put("orgTxnType", "");
		originalNotification.put("txnTime", "20130813 164256");
		originalNotification.put("responseMessage", "交易成功");
		originalNotification.put("issuerIdView", "");
		originalNotification.put("terminalOperId", "001");
		originalNotification.put("amt", "99.00");
		originalNotification.put("terminalOperId", "001");
		originalNotification.put("orgExternalTraceNo", "");
		originalNotification.put("responseCode", "00");
		originalNotification.put("merchantId", "812025145110001");
		originalNotification.put("terminalId", "00000625");
		originalNotification.put("externalTraceNo", "2013081334989914");
		Assert.assertTrue(target.validate(originalNotification));
	}
	
}
