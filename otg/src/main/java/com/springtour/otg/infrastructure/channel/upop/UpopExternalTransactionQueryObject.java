package com.springtour.otg.infrastructure.channel.upop;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class UpopExternalTransactionQueryObject extends AbstractChannelExternalTransactionQueryObjectAdapter {

    @Setter
    private Configurations configurations;

    protected Logger logger() {
        return LoggerFactory.getLogger();
    }

    public UpopExternalTransactionQueryObject(String channel) {
        super(channel);
    }

    @Override
    public ExternalTransactionQueryResult queryBy(Transaction transaction) {
        String responseText = httpPost(transaction);
        logger().info(
                "UPOP responsed encrypted data: " + responseText + ",  TXN NUMBER ["
                        + transaction.getTransactionNo().getNumber() + "]");
        if (responseText != null && !"".equals(responseText)) {
            String[] arr = QuickPayUtils.getResArr(responseText);
            if (checkSecurity(arr, transaction.getMerchant().getKey())) {// 验证签名
                return ThrowableExternalTransactionQueryResultConverter.convertToExternalTransactionQueryResult(arr,
                        transaction);
            } else {
                throw new IllegalArgumentException("response validation Failed! ");
            }
        } else {
            logger().info("报文格式为空");
            throw new IllegalArgumentException("报文格式为空");
        }
    }

    private String httpPost(Transaction transaction) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpget = new HttpPost(assembleUrl(transaction));
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);
            return responseText;
        } catch (ClientProtocolException e) {
            throw new IllegalArgumentException("Cannot connect to Upop!", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot get message from Upop of single query service!", e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    private String assembleUrl(Transaction transaction) {
        StringBuilder url = new StringBuilder(configurations.getUpopQueryUrl());
        url.append("?");
        String[] valueVo = new String[] { QuickPayConf.version,// 协议版本
                QuickPayConf.charset,// 字符编码
                QuickPayConf.transType,// 交易类型
                transaction.getMerchant().getCode(),// 商户代码
                transaction.getTransactionNo().getNumber(),// 订单号
                new SimpleDateFormat("yyyyMMddHHmmss").format(transaction.getWhenRequested()),// 交易时间
                Constants.EMPTY // 保留域
                };
        url
                .append(new QuickPayUtils().createBackStr(valueVo, QuickPayConf.queryVo, transaction.getMerchant()
                        .getKey()));
        return url.toString();
    }

    // 验证签名
    public boolean checkSecurity(String[] arr, String key) {
        // 验证签名
        int checkedRes = new QuickPayUtils().checkSecurity(arr, key);
        if (checkedRes == 1) {
            return true;
        } else if (checkedRes == 0) {
            logger().info("验证签名失败");
            return false;
        } else if (checkedRes == 2) {
            logger().info("报文格式错误");
            return false;
        }
        return false;
    }

}
