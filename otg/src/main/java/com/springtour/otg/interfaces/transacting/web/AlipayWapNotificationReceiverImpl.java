package com.springtour.otg.interfaces.transacting.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.NotificationService;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayWapNotificationReceiverImpl extends MultiActionController
		implements NotificationReceiver {
	@Setter
	private NotificationAcknowledger acknowledger;
	@Setter
	private NotificationService notificationService;
	@Setter
	private ApplicationEvents applicationEvents;
	@Setter
	private String channelId;

	@Override
	public void receive(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Notification notification = null;
		try {
			Map<String, String> aOriginalNotification = sortParametersFromRequest(request);
			LoggerFactory.getLogger().info(
					"Raw notification received:"
							+ aOriginalNotification.toString());
			notification = notificationService.receive(channelId,
					aOriginalNotification);
			applicationEvents.notificationWasReceived(notification);
			ack(request, response, notification);
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}

	}
	
	private void ack(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        if (notification != null && acknowledger != null) {
            acknowledger.acknowledge(request, response, notification);
        }
    }

	private Map sortParametersFromRequest(HttpServletRequest request)
			throws UnsupportedEncodingException {
		LoggerFactory.getLogger().info(
				"CharacterEncoding:" + request.getCharacterEncoding());
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("gbk"), "utf-8");
			params.put(name, valueStr);
		}
		return params;
	}

	@Override
	public String getChannel() {
		return channelId;
	}

}
