package com.springtour.otg.infrastructure.channel.cmb;

import java.text.SimpleDateFormat;

import lombok.Setter;
import cmb.netpayment.Settle;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import org.apache.log4j.Logger;

public class CmbExternalTransactionQueryObject extends AbstractChannelExternalTransactionQueryObjectAdapter {
    @Setter
    private Configurations configurations;

    private Logger log = LoggerFactory.getLogger();

    public CmbExternalTransactionQueryObject(String channel) {
        super(channel);
    }

    @Override
    public ExternalTransactionQueryResult queryBy(Transaction transaction) {
        Settle settle = new Settle();
        int iRet = settle.SetOptions("payment.ebank.cmbchina.com");
        if (iRet == 0) {
            log.info("SetOptions ok");
        } else {
            log.info(settle.GetLastErr(iRet));
        }

        iRet = settle.LoginC(configurations.getCmbBranchId(), transaction.getMerchant().getCode(), CmbConstants.QUERY_PASSWORD);
        if (iRet == 0) {
            log.info("LoginC ok");
        } else {
            log.info(settle.GetLastErr(iRet));
        }
        StringBuffer strbuf = new StringBuffer();
        settle.QuerySingleOrder(new SimpleDateFormat("yyyyMMdd").format(transaction.getWhenRequested()), transaction
                .getTransactionNo().getNumber(), strbuf);
        log.info("CMB responsed data: " + strbuf.toString() + ",  TXN NUMBER ["
                + transaction.getTransactionNo().getNumber() + "]");
        String[] cmbData = strbuf.toString().split("\n");
        settle.Logout();
        return ThrowableExternalTransactionQueryResultConverter.convertToExternalTransactionQueryResult(cmbData,
                transaction);

    }

}
