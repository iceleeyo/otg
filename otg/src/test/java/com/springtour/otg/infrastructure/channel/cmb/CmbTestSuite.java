/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.cmb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-1
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( { CmbExternalTransactionQueryObjectUnitTest.class, CmbNotificationValidatorImplUnitTests.class,
        CmbRequestUrlAssemblerUnitTest.class, CmbTransactionNoGeneratorImplUnitTest.class })
public class CmbTestSuite {

}
