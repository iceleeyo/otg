package com.springtour.otg.infrastructure.channel.bill99pos;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;

public class Bill99PosNotificationAcknowledgerImpl implements NotificationAcknowledger {

	@Override
	public void acknowledge(HttpServletRequest request,
			HttpServletResponse response, Notification notification)
			throws IOException {
		response.getWriter().write(Bill99PosConstants.CONFIRMATION_FLAG);
	}

}
