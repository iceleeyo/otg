/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 006874
 */
public class TenpayNotificationValidatorImplUnitTests {
    
    private TenpayNotificationValidatorImpl tenpayNotificationValidatorImpl;
    
    @Before    
    public void setUp() {
        tenpayNotificationValidatorImpl = new TenpayNotificationValidatorImpl();
    }

//      bank_billno=201203084996425, 
//      bank_type=CCB, 
//      discount=0, 
//      fee_type=1, 
//        input_charset=GBK, 
//        merchantKey=4a83dbce7f6e88e377b6ce44c4b87001,
//                notify_id=6Yyittmnp4-Bg8S__oVqk-ntvo4pCTGUhxB0Gu8WqmPR8pZNcbhAKSvRZ7--cCbGn_IXgsOeXSwy5eEd4yHUKYu7xHtJhs4y, 
//                out_trade_no=2012030811539405, 
//                partner=1202405801,
//                product_fee=100, 
//                sign=795937D3E8855A555A69605973630A26, 
//                sign_type=MD5,
//                time_end=20120308103135, total_fee=100, trade_mode=1, trade_state=0, transaction_id=1202405801201203080089657687, transport_fee=0}
    @Test
    public void testValidate() {
        Map<String, String> notification = new HashMap<String, String>();
        notification.put("bank_billno", "201203084996425");
        notification.put("bank_type", "CCB");
        notification.put("discount", "0");
        notification.put("fee_type", "1");
        notification.put("input_charset", "GBK");
        notification.put("merchantKey", "4a83dbce7f6e88e377b6ce44c4b87001");
        notification.put("notify_id", "6Yyittmnp4-Bg8S__oVqk-ntvo4pCTGUhxB0Gu8WqmPR8pZNcbhAKSvRZ7--cCbGn_IXgsOeXSwy5eEd4yHUKYu7xHtJhs4y");
        notification.put("out_trade_no", "2012030811539405");
        notification.put("partner", "1202405801");
        notification.put("product_fee", "100");
        notification.put("sign", "795937D3E8855A555A69605973630A26");
        notification.put("sign_type", "MD5");
        notification.put("time_end", "20120308103135");
        notification.put("total_fee", "100");
        notification.put("trade_mode", "1");
        notification.put("trade_state", "0");
        notification.put("transaction_id", "1202405801201203080089657687");
        notification.put("transport_fee", "0");
        boolean a = tenpayNotificationValidatorImpl.validate(notification);
        Assert.assertTrue(a);
    }
}
