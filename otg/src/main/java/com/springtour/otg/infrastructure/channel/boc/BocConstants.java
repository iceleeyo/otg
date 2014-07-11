/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.boc;
/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-4
 */
public class BocConstants {
	// 支付类型（1：网上购物）
    public final static String PAY_TYPE = "1";
    // 交易币种(人民币)
    public final static String CNY = "001";
    // 支付结果通知类型
    public final static String NOTIFY_SIGN = "1";//0：只接收支付成功结果,1：全部接收支付结果,2：不接收支付结果
    // 客户取货类型
    public final static String GOODS_SIGN = "1";//0：成功时展示取货地址,1：成功失败都展示取货地址,2：不展示取货地址
}
