package com.springtour.otg.infrastructure.channel.ccb;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;


import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;

public class CcbNotificationValidatorImplUnitTests {
	
	private CcbNotificationValidatorImpl target;
	
	@Before
	public void init(){
		target = new CcbNotificationValidatorImpl();
	}
	
	@Test
	public void validate() throws CannotLaunchSecurityProcedureException{
		Map<String, String> notification = new LinkedHashMap<String, String>();
		notification.put("POSID","100000589");
		notification.put("BRANCHID","310000000");
		notification.put("ORDERID","2012122027066206");
		notification.put("PAYMENT","0.01");
		notification.put("CURCODE","01");
		notification.put("REMARK1","");
		notification.put("REMARK2","");
		notification.put("ACC_TYPE","12");
		notification.put("SUCCESS","Y");
		notification.put("SIGN","3afd91ab285fd76f4db75a3f6847b319639bcf343842a276ab3dc69e68decb3f139f5bcf6906e26981b853b9191f6e2b10e33ded891a29833ab276e309236604a332b8e2420065f57a16becc5564ee64046d0b27f1f36e18a4100182433476a875f385fa7f4d692afb39d6b57f726ee5b36a2216e79500539d5e76859b58c962");
		notification.put("merchantKey", ",30819c300d06092a864886f70d010101050003818a0030818602818064c2a9cf8e6a17508d435da9470ffd5bfcb9298c07fcc5489cfafa46dac3bc05ce7bd576bc8cf6df796cc18bcc5e3111fb327fca44e7920cee8ae3195cd31a41bda4e7e8c9361ec47c8220b88444e92322bb4d97fdec85a6d93fe14fd746777bd17733a7f596d7086c18a73bd4ce744b9a3dc1acd3d35cccdb12c82b270e65f3020111");
		assertTrue(target.validate(notification));
	}

}
