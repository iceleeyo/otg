package com.springtour.otg.application.exception;

public class StatusCode {

    private static final String PREFIX = "OTG_";
    /**
     * 成功
     */
    public static final String SUCCESS = "1";
    /**
     * 未知错误
     */
    public static final String UNKNOWN_ERROR = PREFIX + "UNKNOWN_ERROR";
    /**
     * 商户不可用，可能是由于未找到商户或商户状态已关闭
     */
    public static final String UNAVAILABLE_MERCHANT = PREFIX + "UNAVAILABLE_MERCHANT";
    /**
     * 未找到支付渠道，可能是由于该支付渠道对此Partner不可用
     */
    public static final String UNAVAILABLE_CHANNEL = PREFIX + "UNAVAILABLE_CHANNEL";
    /**
     * 付款应用不可用，可能是由于输入的application并未实现MakePaymentService
     */
    public static final String UNAVAILABLE_PAYMENT_APP = PREFIX + "UNAVAILABLE_PAYMENT_APP";
    /**
     * 对接伙伴不可用
     */
    public static final String UNAVAILABLE_PARTNER = PREFIX + "UNAVAILABLE_PARTNER";
    /**
     * 选择的网关不可用，可能是由于Channel不支持或未对此Partner开放
     */
    public static final String UNAVAILABLE_GATEWAY = PREFIX + "UNAVAILABLE_GATEWAY";
    /**
     * 选择的持卡人证件类型不可用，可能是由于Channel不支持
     */
    public static final String UNAVAILABLE_CARD_HOLDER_ID_TYPE = PREFIX + "UNAVAILABLE_CARD_HOLDER_ID_TYPE";
    /**
     * 选择的币种不可用，可能是由于Channel不支持
     */
    public static final String UNAVAILABLE_CURRENCY = PREFIX + "UNAVAILABLE_CURRENCY";
    /**
     * 签名/验签/加密失败，可能是由于该商户的签名机制没有配置好
     */
    public static final String CANNOT_LAUNCH_SECURITY_PROCEDURE = PREFIX + "CANNOT_LAUNCH_SECURITY_PROCEDURE";
    /**
     * 付款失败
     */
    public static final String CANNOT_MAKE_PAYMENT = PREFIX + "CANNOT_MAKE_PAYMENT";
    /**
     * 交易金额超过信用额度，主要是因为发卡行的限定或交易风险，比如一天至多交易X次等
     */
    public static final String UNAVAILABLE_CREDIT = PREFIX + "UNAVAILABLE_CREDIT";
    /**
     * 持卡人信息错误，可能是输入的持卡人信息不是银行登记的持卡人信息
     */
    public static final String INVALID_CARD_HOLDER = PREFIX + "INVALID_CARD_HOLDER";
    /**
     * 卡信息错误，可能是有效期、CVV2格式错误
     */
    public static final String INVALID_CARD_INFO = PREFIX + "INVALID_CARD_INFO";
    /**
     * 卡不可用，可能是由于该卡不支持在线交易业务或未在银行开通
     */
    public static final String UNSUPPORTED_SERVICE = PREFIX + "UNSUPPORTED_SERVICE";
    /**
     * 当前的核对状态不能再改变
     */
	public static final String CANNOT_CHANGE_CHECKING_STATE = PREFIX + "CANNOT_CHANGE_CHECKING_STATE";
	
	public static final String CANNOT_RETRIEVE_SYNCHRONIZING_METHOD = PREFIX + "CANNOT_RETRIEVE_SYNCHRONIZING_METHOD";
	/**
	 * 交易状态有误
	 */
	public static final String INVALID_TXN_STATE = PREFIX + "INVALID_TXN_STATE";
}
