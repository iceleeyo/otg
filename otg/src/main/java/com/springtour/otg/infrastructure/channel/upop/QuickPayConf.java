package com.springtour.otg.infrastructure.channel.upop;

public class QuickPayConf {

	// 版本号
	public final static String version = "1.0.0";

	// 编码方式
	public final static String charset = "UTF-8";

	// 消费交易类型
	public static final String transType = "01";

	// 交易币种(人民币)
	public final static String CNY = "156";

	// 加密方式
	public final static String signType = "MD5";
	
	// 签名
	public final static String signature = "signature";
	public final static String signMethod = "signMethod";

	// 组装消费请求包
	public final static String[] reqVo = new String[] { "version", "charset",
			"transType", "origQid", "merId", "merAbbr", "acqCode", "merCode",
			"commodityUrl", "commodityName", "commodityUnitPrice",
			"commodityQuantity", "commodityDiscount", "transferFee",
			"orderNumber", "orderAmount", "orderCurrency", "orderTime",
			"customerIp", "customerName", "defaultPayType",
			"defaultBankNumber", "transTimeout", "frontEndUrl", "backEndUrl",
			"merReserved" };

	public final static String[] notifyVo = new String[]{
        "charset",
        "cupReserved",
        "exchangeDate",
        "exchangeRate",
        "merAbbr",
        "merId",
        "orderAmount",
        "orderCurrency",
        "orderNumber",
        "qid",
        "respCode",
        "respMsg",
        "respTime",
        "settleAmount",
        "settleCurrency",
        "settleDate",
        "traceNumber",
        "traceTime",
        "transType",
        "version"
	};

	public final static String[] queryVo = new String[]{
		"version",
		"charset",
		"transType",
		"merId",
		"orderNumber",
		"orderTime",
		"merReserved"
	};

}
