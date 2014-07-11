package com.springtour.otg.infrastructure.channel.bosh;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import lombok.Setter;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;

public class BoshNotificationValidatorImplUnitTests {
	private BoshNotificationValidatorImpl target;
    private Configurations configurations = new Configurations();
	@Before
	public void init(){
	    target = new BoshNotificationValidatorImpl();
	    target.setConfigurations(configurations);
	}
	@Test
	public void testValidator(){
		
		Map<String, String> originalNotification = new HashMap<String, String>();
		originalNotification.put("signDataStr", "315310018000169|2014041141076222|0|10000|");
		originalNotification.put("signData", "rFiDvRAkZMk8cjAqxDJS6zCBEvjPE0udNbrWBCfcosPbnfOjmlzDdzYBns9zhp3K8Ru3XiTbcfx6JoIRD94MCKzSeTVJtmmRA2rw2LuKFFbesjRSkB1dN1uzLzL1YXIvyFC035VpulG8qgZx3/mVWDZDdbb/7TkCvenDAGhGsRQ=");
		
		Assert.assertTrue(target.validate(originalNotification));
	}
	
}
