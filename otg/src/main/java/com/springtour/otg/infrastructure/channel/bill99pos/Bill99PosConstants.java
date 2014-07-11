package com.springtour.otg.infrastructure.channel.bill99pos;

import java.math.BigDecimal;

public class Bill99PosConstants {
	//消费
	public static final String TRANS_TYPE = "00";
	//撤销
	public static final String CANCEL_TYPE = "02";
	public static final String POS_ID = "00000071";
	public static final String OPERATOR_ID = "0001";
	public static final BigDecimal AMOUNT_CONVERTION_SCALE = BigDecimal
			.valueOf(100);
	public static final String CONFIRMATION_FLAG = "0";
	public static final String CHARGED_SUCCESS = "00";
	
	public static final String CERTIFICATE_TYPE = "X.509";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    
    public static final String TXN_TYPE_PUR = "PUR";
    public static final String TXN_TYPE_VTX = "VTX";
}
