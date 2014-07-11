package com.springtour.otg.infrastructure.channel.ccb;

import java.util.Map;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.infrastructure.channel.NotificationValidator;

public abstract class AbstractCcbNotificationValidator implements
		NotificationValidator {

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {
		boolean flag = false;
		String strSrc = validateStrFrom(originalNotification).trim();
		final String publicKey = originalNotification.get("merchantKey").split(
				",")[1];
		RSASig rsa = new RSASig();
		rsa.setPublicKey(publicKey);
		final String strSign = originalNotification.get("SIGN").trim();
		try {
			flag = rsa.verifySigature(strSign, strSrc);
		} catch (Exception ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		}
		return flag;
	}

	public abstract String validateStrFrom(
			Map<String, String> originalNotification);

}
