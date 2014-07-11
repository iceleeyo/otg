package com.springtour.otg.infrastructure.channel.ccb;

import java.util.Map;

public class CcbInstallmentNotificationValidatorImpl extends
		AbstractCcbNotificationValidator {

	public String validateStrFrom(Map<String, String> originalNotification) {
		String srcStr = "";
		srcStr = appendParam(srcStr, "POSID", originalNotification.get("POSID"));
		srcStr = appendParam(srcStr, "BRANCHID", originalNotification
				.get("BRANCHID"));
		srcStr = appendParam(srcStr, "ORDERID", originalNotification
				.get("ORDERID"));
		srcStr = appendParam(srcStr, "PAYMENT", originalNotification
				.get("PAYMENT"));
		srcStr = appendParam(srcStr, "CURCODE", originalNotification
				.get("CURCODE"));
		srcStr = appendParam(srcStr, "REMARK1", originalNotification
				.get("REMARK1"));
		srcStr = appendParam(srcStr, "REMARK2", originalNotification
				.get("REMARK2"));
		srcStr = appendParam(srcStr, "ACC_TYPE", originalNotification
				.get("ACC_TYPE"));
		srcStr = appendParam(srcStr, "SUCCESS", originalNotification
				.get("SUCCESS"));
		srcStr = appendParam(srcStr, "TYPE", originalNotification.get("TYPE"));
		srcStr = appendParam(srcStr, "REFERER", originalNotification
				.get("REFERER"));
		srcStr = appendParam(srcStr, "CLIENTIP", originalNotification
				.get("CLIENTIP"));
		srcStr = appendParam(srcStr, "ACCDATE", originalNotification
				.get("ACCDATE"));
		srcStr = appendParam(srcStr, "INSTALLNUM", originalNotification
				.get("INSTALLNUM"));
		srcStr = appendParam(srcStr, "ERRMSG", originalNotification
				.get("ERRMSG"));
		return srcStr;

	}

	private String appendParam(String returns, String paramId, String paramValue) {
		if (!returns.equals("")) {
			if (paramValue != null) {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (paramValue != null) {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}
}
