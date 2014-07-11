package com.springtour.otg.infrastructure.channel.cmb;

import java.util.Map;

import cmb.netpayment.Security;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

import org.apache.log4j.Logger;

public class CmbNotificationValidatorImpl implements NotificationValidator {
    Logger log = LoggerFactory.getLogger();

    @Override
    public boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException {
        boolean bRet = false;
        try {
            String srcStr = assemblySignatureString(originalNotification);
            log.info("招商银行验签数据：" + srcStr);
            final String keyfilePath = this.getClass().getResource("/com/springtour/runtime/otg/public.key").getPath();
            Security pay = new Security(keyfilePath);
            bRet = pay.checkInfoFromBank(srcStr.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CannotLaunchSecurityProcedureException("cmb verifySign failed", e);
        }
        return bRet;
    }

    private String assemblySignatureString(Map<String, String> originalNotification) {
        String srcStr = "";
        srcStr = appendParam(srcStr, "Succeed", originalNotification.get("Succeed"));
        srcStr = appendParam(srcStr, "CoNo", originalNotification.get("CoNo"));
        srcStr = appendParam(srcStr, "BillNo", originalNotification.get("BillNo"));
        srcStr = appendParam(srcStr, "Amount", originalNotification.get("Amount"));
        srcStr = appendParam(srcStr, "Date", originalNotification.get("Date"));
        srcStr = appendParam(srcStr, "MerchantPara", originalNotification.get("MerchantPara"));
        srcStr = appendParam(srcStr, "Msg", originalNotification.get("Msg"));
        srcStr = appendParam(srcStr, "Signature", originalNotification.get("Signature"));
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
