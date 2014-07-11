/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.channel;

public enum Gateways {
	TDC("扫码支付"),
    ABC("农业银行"),
    ALIPAY("支付宝"),
    BOC("中国银行"),
    BOSH("上海银行"),
    CBHB("渤海银行"),
    CCB("建设银行"),
    CEB("光大银行"),
    CGB("广发银行"),
    CIB("兴业银行"),
    CITIC("中信银行"),
    CMB("招商银行"),
    CMBC("民生银行"),
    COMM("交通银行"),
    ICBC("工商银行"),
    PINGAN("平安银行"),
    PSBC("邮政储蓄"),
    SDB("深圳发展"),
    SPDB("浦发银行"),
    SPRING("春秋商旅卡"),
    TENPAY("财付通"),
    POS("快钱POS"),
	UPOP("中国银联");
    private final String name;
    private static final String FILE_NAME_SUFFIX = ".gif";

    private Gateways(String name) {
        this.name = name;
    }

    public String code() {
        return this.toString();
    }

    public String imageFileName() {
        return code().toLowerCase() + FILE_NAME_SUFFIX;
    }

    public static Gateways of(String code) {
        Gateways result = null;
        for (Gateways gateway : Gateways.values()) {
            if (gateway.code().equals(code)) {
                return gateway;
            }
        }
        return result;
    }

    public static String getName(String code) {
        Gateways g = of(code);
        if (g == null) {
            return code;
        } else {
            return of(code).getName();
        }
    }

    public String getName() {
        return name;
    }
}
