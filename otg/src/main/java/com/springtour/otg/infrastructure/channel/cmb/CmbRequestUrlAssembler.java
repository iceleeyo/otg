package com.springtour.otg.infrastructure.channel.cmb;

import java.text.SimpleDateFormat;

import lombok.Setter;

import cmb.MerchantCode;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;

public class CmbRequestUrlAssembler {
    @Setter
    private Configurations configurations;

    public String assembleUrl(Transaction transaction, String returnUrl, String ip) {
        StringBuilder url = requstUrlPrefix();
        url.append("BranchID=" + configurations.getCmbBranchId());
        url.append("&CoNo=" + transaction.getMerchant().getCode());
        url.append("&BillNo=" + transaction.getTransactionNo().getNumber());
        url.append("&Amount=" + transaction.getAmount().getAmount());
        url.append("&Date=" + new SimpleDateFormat("yyyyMMdd").format(transaction.getWhenRequested()));
        url.append("&MerchantUrl=" + returnUrl);
        url.append("&MerchantPara=");
        // 产生校验码
        MerchantCode mc = new MerchantCode();
        String strVerifyCode =
                mc.genMerchantCode(transaction.getMerchant().getKey(), new SimpleDateFormat("yyyyMMdd")
                        .format(transaction.getWhenRequested()), configurations.getCmbBranchId(), transaction
                        .getMerchant().getCode(), transaction.getTransactionNo().getNumber(), String
                        .valueOf(transaction.getAmount().getAmount()), "", returnUrl, "", "", ip,
                        CmbConstants.STR_GOODS_TYPE, "");
        url.append("&MerchantCode=" + strVerifyCode);
        return url.toString();
    }

    private StringBuilder requstUrlPrefix() {
        StringBuilder urlPrefix = new StringBuilder(configurations.getCmbRequestUrl());
        urlPrefix.append("?" + configurations.getCmbConnectionType() + "?");
        return urlPrefix;
    }
}
