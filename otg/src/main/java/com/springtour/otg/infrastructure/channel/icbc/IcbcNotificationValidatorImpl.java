/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import java.util.Map;

import lombok.Setter;

import cn.com.infosec.icbc.ReturnValue;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;

/**
 * @author 005073
 * 
 */
public class IcbcNotificationValidatorImpl implements NotificationValidator {

	@Setter
	private Configurations configurations;

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {
		IcbcCertFileReader icbcCertFileReader = new IcbcCertFileReader();
		byte[] bs = icbcCertFileReader.fileReader(configurations
				.icbcVerifySignPubCertResourceName());
		String notifyData = originalNotification.get("notifyData").trim();
		String signMsg = originalNotification.get("signMsg").trim();
		byte[] notifyDataBase64 = ReturnValue.base64dec(notifyData.getBytes());
		byte[] signMsgBase64 = ReturnValue.base64dec(signMsg.getBytes());
		try {	
			if (ReturnValue.verifySign(notifyDataBase64,
					notifyDataBase64.length, bs, signMsgBase64) == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(
					"icbc verifySign failed", e);
		}
	}
}
