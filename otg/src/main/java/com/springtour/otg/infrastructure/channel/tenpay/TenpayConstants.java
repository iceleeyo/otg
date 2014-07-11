package com.springtour.otg.infrastructure.channel.tenpay;

import java.math.BigDecimal;

public class TenpayConstants {
	public static final BigDecimal AMOUNT_CONVERTION_SCALE = BigDecimal
			.valueOf(100);
        public static final String CNY = "1";
        

	public static final String CONFIRMATION_FLAG = "success";

	public static final String CHARGED_SUCCESS = "0";

	public static final String TRADE_MODE = "1";

}
