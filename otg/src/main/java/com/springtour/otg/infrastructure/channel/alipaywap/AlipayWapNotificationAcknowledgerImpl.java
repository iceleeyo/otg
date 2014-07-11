package com.springtour.otg.infrastructure.channel.alipaywap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;

public class AlipayWapNotificationAcknowledgerImpl implements NotificationAcknowledger {

	@Override
	public void acknowledge(HttpServletRequest request,
			HttpServletResponse response, Notification notification)
			throws IOException {
		PrintWriter out = response.getWriter();
        out.println(AlipayWapConfig.CONFIRMATION_FLAG);
        out.flush();
        out.close();
	}

}
