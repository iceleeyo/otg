package com.springtour.otg.infrastructure.channel.chinapnr;

public class ChinapnrConstants {

    public static final String CHARGED_SUCCESS = "00";
    public static final String CONFIRMATION_FLAG_PREFIX = "RECV_ORD_ID_";
    public static final int VALID_SIGNATURE = 0;
    public static final String PENDING = "99";
    public static final String IDENTITY_TYPE_IDENTITY = "00";
    public static final String IDENTITY_TYPE_PASSPORT = "01";
    public static final String IDENTITY_TYPE_OTHERS = "07";
    /**
     * 目前是“10”，电话支付分账平台如果更改接口版本，会兼容老版本接口。
     */
    public static final String VERSION = "10";
    /**
     * 请求类型
     * 电话支付填“TelPay”
     * 电话支付退款填“TelRefund”
     */
    public static final String COMMAND_TELPAY = "TelPay";
}
