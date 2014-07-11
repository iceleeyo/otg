/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Future
 */
public class SpringCardNotificationValidationTests {

    @Test
    public void validate() throws CannotLaunchSecurityProcedureException {
        SpringCardNotificationValidatorImpl target = new SpringCardNotificationValidatorImpl();
        Map<String, String> map = new HashMap<String, String>();
        map.put("BillNo", "2012031300290002");
        map.put("BillAmount", "1");
        map.put("SUCC", "Y");
        map.put("Key", "01lh17");
        map.put("SignMD5", "a6db82023226ea29f7b6cdbf7d4f8e27");
        Assert.assertTrue(target.validate(map));
    }
}
