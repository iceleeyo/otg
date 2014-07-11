/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.chinapnr;

import chinapnr.SecureLink;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import java.util.Map;

/**
 *
 * @author 006874
 */
public class ChinapnrNotificationValidatorImpl implements NotificationValidator {

    @Override
    public boolean validate(Map<String, String> originalNotification) {
        final String actualSignature = originalNotification.get("ChkValue");
        final String externalTransactionNo = originalNotification.get("TrxId");
        final String amount = originalNotification.get("OrdAmt");
        final String transactionNo = originalNotification.get("OrdId");
        final String retType = originalNotification.get("RetType");
        final String curCode = originalNotification.get("CurCode");
        final String cmdId = originalNotification.get("CmdId");
        final String merchantCode = originalNotification.get("MerId");
        final String gateway = originalNotification.get("GateId");
        final String message = originalNotification.get("RespCode");
        StringBuilder expectedSignature = new StringBuilder().append(cmdId).append(merchantCode).append(message).append(
                externalTransactionNo).append(amount).append(curCode).append(transactionNo).append(retType).append(gateway);

        SecureLink link = new SecureLink();
        final String keyfilePath = this.getClass().getResource(configurations.chinapnrPublicKeyClasspathResourceName()).getPath();
        int result = link.VeriSignMsg(keyfilePath,
                expectedSignature.toString(), actualSignature);
        if (ChinapnrConstants.VALID_SIGNATURE == result) {
            return true;
        } else {
            return false;
        }
    }
    private Configurations configurations;

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }
}
