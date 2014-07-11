package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;

public class Bill99GatewayNotificationAcknowledgerImpl implements
		NotificationAcknowledger {

	@Override
	public void acknowledge(HttpServletRequest request,
			HttpServletResponse response, Notification notification)
			throws IOException {
		String redirectUrl = request.getParameter("ext1");
		String ackString = Bill99GatewayConstants.CONFIRMATION_FLAG
				+ "<redirecturl>" + redirectUrl + "</redirecturl>";
		response.getWriter().write(ackString);
	}

}
