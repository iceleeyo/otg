package com.springtour.otg.infrastructure.channel.upop;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.transaction.Transaction;

public class UpopRequestUrlAssembler {
    @Setter
    private Configurations configurations;

    public String assembleUrl(Transaction transaction, String returnUrl, String ip) {

        // 中国银联交易金额保留到角分，故需在我们交易金额的基础上乘以100
        BigDecimal orderAmount = transaction.getAmount().getAmount().multiply(new BigDecimal(100)).setScale(0);
        String[] valueVo = new String[] { QuickPayConf.version,// 协议版本
                QuickPayConf.charset,// 字符编码
                QuickPayConf.transType,// 交易类型
                Constants.EMPTY,// 原始交易流水号 撤销时填入
                transaction.getMerchant().getCode(),// 商户代码
                transaction.getMerchant().getName(),// 商户简称
                Constants.EMPTY,// 收单机构代码（仅收单机构接入需要填写）
                Constants.EMPTY,// 商户类别（收单机构接入需要填写）
                Constants.EMPTY,// 商品URL
                Constants.EMPTY,// 商品名称
                Constants.EMPTY,// 商品单价 单位：分
                Constants.EMPTY,// 商品数量
                Constants.EMPTY,// 折扣 单位：分
                Constants.EMPTY,// 运费 单位：分
                transaction.getTransactionNo().getNumber(),// 订单号（需要商户自己生成）
                String.valueOf(orderAmount),// 交易金额 单位：分
                QuickPayConf.CNY,// 交易币种
                new SimpleDateFormat("yyyyMMddHHmmss").format(transaction.getWhenRequested()),// 交易时间
                ip,// 用户IP
                Constants.EMPTY,// 用户真实姓名
                Constants.EMPTY,// 默认支付方式
                Constants.EMPTY,// 默认银行编号
                configurations.getTransTimeout(),// 交易超时时间
                returnUrl,// 前台回调商户URL
                configurations.getUpopNotifyUrl(),// 后台回调商户URL
                Constants.EMPTY // 商户保留域
                };
        return new QuickPayUtils().createPayHtml(valueVo, QuickPayConf.signType, configurations.getUpopGateway(),
                transaction.getMerchant().getKey());
    }
}
