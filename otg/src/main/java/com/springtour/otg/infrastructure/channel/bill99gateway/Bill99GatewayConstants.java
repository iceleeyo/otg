/**
 * 
 */
package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.math.BigDecimal;

/**
 * @author Future
 * 
 */
public class Bill99GatewayConstants {
	public static final String INPUT_CHARSET = "1";
	public static final String VERSION = "v2.0";
	public static final String LANGUAGE = "1";
	public static final String SIGN_TYPE = "4";
	public static final String PAY_TYPE = "10";
	public static final String KEY_TYPE = "PKCS12";
	public static final String CERTIFICATE_TYPE="X.509";
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	public static final String KEY_NAME = "test-alias";
	public static final BigDecimal AMOUNT_CONVERTION_SCALE = BigDecimal
			.valueOf(100);
	public static final String CONFIRMATION_FLAG = "<result>1</result>";
	
	public static final String CHARGED_SUCCESS = "10";
	
}
