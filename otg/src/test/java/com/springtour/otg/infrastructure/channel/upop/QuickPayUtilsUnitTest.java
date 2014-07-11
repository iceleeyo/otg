package com.springtour.otg.infrastructure.channel.upop;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class QuickPayUtilsUnitTest {

    public QuickPayUtils target;

    @Before
    public void init() {
        target = new QuickPayUtils();
    }

    @Test
    public void testCheckSign() {
        Map<String, String> originalNotification = new HashMap<String, String>();
        originalNotification.put("charset", "UTF-8");
        originalNotification.put("cupReserved", "");
        originalNotification.put("exchangeDate", "");
        originalNotification.put("exchangeRate", "");
        originalNotification.put("merAbbr", "上海春秋国际旅行社（集团）有限公司（春秋旅游网）");
        originalNotification.put("merId", "103310047220214");
        originalNotification.put("orderAmount", "109000");
        originalNotification.put("orderCurrency", "156");
        originalNotification.put("orderNumber", "2014031948778620");
        originalNotification.put("qid", "201403191542485834422");
        originalNotification.put("respCode", "00");
        originalNotification.put("respMsg", "Success!");
        originalNotification.put("respTime", "20140319153157");
        originalNotification.put("settleAmount", "109000");
        originalNotification.put("settleCurrency", "156");
        originalNotification.put("settleDate", "0319");
        originalNotification.put("traceNumber", "583442");
        originalNotification.put("traceTime", "0319154248");
        originalNotification.put("transType", "01");
        originalNotification.put("version", "1.0.0");
        originalNotification.put("signMethod", "MD5");
        originalNotification.put("signature", "db1219bd7ec547a6ef2567bb74e90aac");

        String[] resArr = new String[QuickPayConf.notifyVo.length];
        for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
            resArr[i] = originalNotification.get((QuickPayConf.notifyVo[i]));
        }
        String signature = originalNotification.get(QuickPayConf.signature);
        String signMethod = originalNotification.get(QuickPayConf.signMethod);
        Boolean signatureCheck = target.checkSign(resArr, signMethod, signature, "88888888");
        assertTrue(signatureCheck);
    }

    @Test
    public void testCheckSecurity() {
        String[] arr =
                target
                        .getResArr("charset=UTF-8&cupReserved=&exchangeDate=&exchangeRate=&qid=&queryResult=&respCode=82&respTime=&settleAmount=&settleCurrency=&settleDate=&signMethod=MD5&signature=358c3ecebcd38a581b6b2ea20cb02d3a&traceNumber=&traceTime=&version=1.0.0");
        int i = target.checkSecurity(arr, "88888888");
        assertEquals(1, i);
    }

}
