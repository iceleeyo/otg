/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import com.springtour.otg.application.util.Md5Encrypt;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author 006874
 */
public class TenpayNotificationValidatorImpl implements NotificationValidator {

    @Override
    public boolean validate(Map<String, String> originalNotification) {
        Map<String, String> sortedParameterMap = sortParametersFromRequest(originalNotification);
        StringBuilder sb = new StringBuilder();
        Set es = sortedParameterMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)
                    && !"merchantKey".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(originalNotification.get("merchantKey"));
        String sign = Md5Encrypt.md5(sb.toString()).toLowerCase();
        String tenpaySign = (((String) originalNotification.get("sign") == null) ? ""
                : (String) originalNotification.get("sign")).toLowerCase();
        return tenpaySign.equals(sign);

    }

    private Map sortParametersFromRequest(Map<String, String> parametersUnsorted) {
        // map -> sorted map
        SortedMap parameters = new TreeMap();
        Iterator itUnsorted = parametersUnsorted.keySet().iterator();
        while (itUnsorted.hasNext()) {
            String k = (String) itUnsorted.next();
            String v = (String) parametersUnsorted.get(k);
            if (null != v) {
                v = v.trim();
                parameters.put(k, v);
            } else {
                parameters.put(k, "");
            }
        }
        return parameters;
    }
}
