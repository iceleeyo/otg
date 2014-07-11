package com.springtour.otg.infrastructure.channel.bill99pos;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.springtour.otg.domain.model.notification.Notification;

public class Bill99PosNotificationAcknowledgerImplUnitTests {
	private Bill99PosNotificationAcknowledgerImpl target;
	
	@Before
	public void prepare() {
		target = new Bill99PosNotificationAcknowledgerImpl();
	}
	
	@Test
	public void testAcknowledge() throws Exception {
		
		HttpServletRequest request = new MockHttpServletRequest();
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		Notification notification = new Notification();
		target.acknowledge(request , response , notification );
		Assert.assertEquals("0", response.getContentAsString());
	}
}
