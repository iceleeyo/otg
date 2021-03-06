package com.springtour.otg.infrastructure.channel.bocomm;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.springtour.otg.application.util.Configurations;

public class BocommNotificationValidatorImplUnitTests {
	private BocommNotificationValidatorImpl target;
    private Configurations configurations = new Configurations();
	@Before
	public void init(){
	    target = new BocommNotificationValidatorImpl();
	    configurations.setBocommXmlFileName("B2CMerchant.xml");
	    configurations.setBocommPfxFileName("301310063009501.PFX");
        configurations.setBocommCertFileName("test_root.cer");
	    target.setConfigurations(configurations);
	}
	@Test
	public void testValidator(){
		String notifyMsg = "301310063009501|2014042448550533|1.00|CNY|20250402| |20140522|111409|A1220E93|1|5.12|0|6222600110057291788| |180.168.15.70| | |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";// 获取银行通知结果
		int lastIndex = notifyMsg.lastIndexOf("|");
		String signMsg = notifyMsg.substring(lastIndex + 1, notifyMsg.length());// 获取签名信息
		String srcMsg = notifyMsg.substring(0, lastIndex + 1);
		Map<String, String> originalNotification = new HashMap<String, String>();
		originalNotification.put("signMsg", signMsg);
    	originalNotification.put("srcMsg", srcMsg);
		
		Assert.assertTrue(target.validate(originalNotification));
	}

}
