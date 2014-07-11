/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bocomm;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.NetSignServer;
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
public class BocommNotificationValidatorImpl implements NotificationValidator {

	Logger log = LoggerFactory.getLogger();

	@Setter
	private Configurations configurations;

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {
		BOCOMB2CClient client = new BOCOMB2CClient();
		String path = this.getClass().getResource(configurations.getBocommXmlFileResourceName()).getPath();
        int ret = client.initialize(path); //该代码只需调用一次

        if (ret != 0) {
        	log.error("初始化失败,错误信息：" + client.getLastErr());
        	throw new RuntimeException("初始化失败,错误信息：" + client.getLastErr());
        }
		boolean isVaild = false;
		String signMsg = originalNotification.get("signMsg");
		String srcMsg = originalNotification.get("srcMsg");
		int veriyCode = -1;
		NetSignServer nss = new NetSignServer(); // 初始化签名库
		try {
			nss.NSDetachedVerify(signMsg.getBytes("GBK"),
					srcMsg.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
		} // 对通知结果进行验签
		veriyCode = nss.getLastErrnum();
		if (veriyCode < 0) {// 验签出错
			LoggerFactory.getLogger().error(
					"商户端验证签名失败：return code:" + veriyCode);
		} else {
			isVaild = true;
		}
		return isVaild;
	}

}
