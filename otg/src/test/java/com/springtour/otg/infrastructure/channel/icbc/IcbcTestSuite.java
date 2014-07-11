/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 005073
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ IcbcRequestUrlAssemblerUnitTests.class,
		DecodeNotifyData2XmlUnitTests.class,
		IcbcNotificationValidatorImplUnitTests.class,
		ThrowableExternalTransactionQueryResultConverterUnitTests.class })
public class IcbcTestSuite {

}
