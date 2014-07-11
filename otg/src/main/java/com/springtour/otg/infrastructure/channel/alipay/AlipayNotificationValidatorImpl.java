/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Md5Encrypt;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Future
 */
public class AlipayNotificationValidatorImpl implements NotificationValidator {

    @Override
    public boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException {
        StringBuilder sb = new StringBuilder();
        Set es = originalNotification.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && !"sign_type".equals(k)
                    && null != v && !"".equals(v) && !"merchantKey".equals(k)) {
                sb.append(k).append("=").append(v);
                if (it.hasNext()) {
                    sb.append("&");
                }
            }
        }
        sb.append(originalNotification.get("merchantKey").split(",")[1]);
        String sign = Md5Encrypt.md5(sb.toString()).toLowerCase();
        String tenpaySign = (((String) originalNotification.get("sign") == null) ? ""
                : (String) originalNotification.get("sign")).toLowerCase();

        return tenpaySign.equals(sign);
    }
    
    public static void main(String []args){
    	String str = "20139090999LLFBDJ72078spring3toursl4k57j39g893j0g82b";
    	System.out.println(Md5Encrypt.md5(str));
    }
}
