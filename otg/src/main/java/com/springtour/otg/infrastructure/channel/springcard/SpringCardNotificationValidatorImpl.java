/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Md5Encrypt;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import java.util.Map;

/**
 *
 * @author Future
 */
public class SpringCardNotificationValidatorImpl implements NotificationValidator{

    @Override
    public boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException {
        String BillNo = originalNotification.get("BillNo");
        String BillAmount = originalNotification.get("BillAmount");
        String succ = originalNotification.get("SUCC");
        String key = originalNotification.get("Key");
        String signature = originalNotification.get("SignMD5");
        StringBuilder sb = new StringBuilder();
        sb.append(BillNo);
        sb.append(BillAmount);
        sb.append(succ);
        sb.append(key);
        return signature.equalsIgnoreCase(Md5Encrypt.md5(sb.toString()));
    }
    
}
