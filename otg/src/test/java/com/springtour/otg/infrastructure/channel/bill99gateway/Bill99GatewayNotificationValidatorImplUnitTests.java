package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;

public class Bill99GatewayNotificationValidatorImplUnitTests {

	private Bill99GatewayNotificationValidatorImpl target;

	@Before
	public void prepare(){
		target = new Bill99GatewayNotificationValidatorImpl();
		Configurations configurations = new Configurations();
		configurations.setBill99GatewayPublicKeyFileName("99bill[1].cert.rsa.20140803.cer");
		PkiPair pkiPair = new PkiPair();
		pkiPair.setConfigurations(configurations);
		target.setPki(pkiPair);
	}
	/**
	 * @throws CannotLaunchSecurityProcedureException 
	 */
	@Test
	public void validate() throws CannotLaunchSecurityProcedureException {
		Map<String, String> notification = new LinkedHashMap<String, String>();
		notification.put("merchantAcctId","1001181534101");
		notification.put("version","v2.0");
		notification.put("language","1");
		notification.put("signType","4");
		notification.put("payType","10");
		notification.put("orderId","20120625140957");
		notification.put("orderTime","20120625140957");
		notification.put("orderAmount","1");
		notification.put("dealId","19647658");
		notification.put("bankDealId", "120625453155");
		notification.put("dealTime","20120625141142");
		notification.put("payAmount","1");
		notification.put("fee","0");
		notification.put("payResult","10");
		notification.put("bankId","");
		notification.put("errCode","");
		notification.put("ext1","");
		notification.put("ext2","");
		notification.put("signMsg","Knyzt4gHre9tx3uPtKp+dkcmkLa2O32GK7w4Bggkb+f65WAs/pX3DLAutF1KsklzBmm8Kzt0KF2SvmWz+RIjlrucnGN7PoeQo6d/TgtvBa9iShSd2DTN/6/OXe9RDcjuesOXB6sxORkZ/oGzK5zMMlkxxu9WjDh47+RntUITYV0=");
		boolean a = target.validate(notification);
		Assert.assertTrue(a);
	}
	
}
