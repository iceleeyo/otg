/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.boc;

import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;

import com.bocnet.common.security.PKCS7Tool;
import com.koalii.svs.SvsVerify;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * 
 * @author 010586
 * @date 2014-4-8
 */
public class BocNotificationValidatorImpl implements NotificationValidator {

	Logger log = LoggerFactory.getLogger();

	@Setter
	private Configurations configurations;

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {

		boolean isVaild = false;
		String merchantNo = originalNotification.get("merchantNo");
		String orderNo = originalNotification.get("orderNo");
		String orderSeq = originalNotification.get("orderSeq");
		String cardTyp = originalNotification.get("cardTyp");
		String payTime = originalNotification.get("payTime");
		String orderStatus = originalNotification.get("orderStatus");
		String payAmount = originalNotification.get("payAmount");

		String unSignData = merchantNo + "|" + orderNo + "|" + orderSeq + "|"
				+ cardTyp + "|" + payTime + "|" + orderStatus + "|" + payAmount;
		
		String signData = originalNotification.get("signData");

		try {
			final String rootCertificatePath = this.getClass()
					.getResource(configurations.getBoshPublicKeyPath())
					.getPath();
			PKCS7Tool tool = PKCS7Tool.getVerifier(rootCertificatePath);
			tool.verify(signData, unSignData.getBytes(), null);
			isVaild = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CannotLaunchSecurityProcedureException(
					"Boc verifySign failed"+e.getMessage(), e);
		}
		return isVaild;
	}

}
