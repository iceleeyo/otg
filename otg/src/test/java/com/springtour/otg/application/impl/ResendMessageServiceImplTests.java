/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.impl;

import com.springtour.otg.application.ResendMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Future
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/com/springtour/otg/applicationContext.xml", "/com/springtour/otg/messaging.xml", "/com/springtour/otg/otg.xml", "/com/springtour/otg/applicationContext-cxf.xml"})
public class ResendMessageServiceImplTests {

    @Autowired
    @Qualifier("otg.ResendMessageServiceImpl")
    private ResendMessageService resendMessageService;

    @Test
    public void resend() {
        resendMessageService.resend();
    }
}
