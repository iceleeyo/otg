package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.util.Map;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.infrastructure.channel.NotificationValidator;

public class Bill99GatewayNotificationValidatorImpl implements
		NotificationValidator {

	private PkiPair pki;

	@Override
	public boolean validate(Map<String, String> notification)
			throws CannotLaunchSecurityProcedureException {
		boolean flag = false;
		String merchantSignMsgVal = "";
		String signMsg = notification.get("signMsg");
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId",
				notification.get("merchantAcctId"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "version",
				notification.get("version"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "language",
				notification.get("language"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType",
				notification.get("signType"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType",
				notification.get("payType"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId",
				notification.get("bankId"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId",
				notification.get("orderId"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime",
				notification.get("orderTime"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount",
				notification.get("orderAmount"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard",
				notification.get("bindCard"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile",
				notification.get("bindMobile"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId",
				notification.get("dealId"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId",
				notification.get("bankDealId"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime",
				notification.get("dealTime"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount",
				notification.get("payAmount"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee",
				notification.get("fee"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1",
				notification.get("ext1"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2",
				notification.get("ext2"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult",
				notification.get("payResult"));
		merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode",
				notification.get("errCode"));
		try {
			flag = pki.isEnCodedByCer(merchantSignMsgVal, signMsg);
		} catch (Exception ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		}
		return flag;
	}

	private String appendParam(String returns, String paramId, String paramValue) {
		if (!returns.equals("")) {
			if (paramValue != null && !paramValue.equals("")) {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (paramValue != null && !paramValue.equals("")) {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}

	public void setPki(PkiPair pki) {
		this.pki = pki;
	}

}
