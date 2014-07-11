/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import org.junit.runner.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

/**
 * 
 * @author 001595
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:com/springtour/otg/applicationContext.xml",
		"classpath:com/springtour/otg/applicationContext-cxf.xml",
		"classpath:com/springtour/otg/messaging.xml",
		"classpath:com/springtour/otg/otg.xml",
		"classpath:com/springtour/otg/stubs.xml" })
public abstract class AbstractOtgApplicationContextIntegrationTests {
}
