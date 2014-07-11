package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import java.util.Map;

public class AlwaysFailNotificationValidator implements NotificationValidator {

	@Override
	public boolean validate(Map<String, String> originalNotification) {
		return false;
	}

}
