/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.domain.model.transaction.Transaction;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author Future
 */
public class ResolveResponseData {

    public static Map<String, String> resolvePurchaseTr2PesponseData(String response, Transaction transaction) throws DocumentException {
        Map<String, String> resolvedData = new HashMap<String, String>();
        Document document = DocumentHelper.parseText(response);
        Element headElement = document.getRootElement();
        if (!responseVersionCheck(headElement.element("version").getTextTrim())) {
            throw new IllegalArgumentException("99Bill TR2 response version error!");
        }
        Element txEle = headElement.element("TxnMsgContent");
        if (txEle == null) {
            Element errorEle = headElement.element("ErrorMsgContent");
            if (errorEle == null) {
                throw new IllegalArgumentException("99Bill TR2 response xml file can not resolve correctly!");
            }
            resolvedData.put("RespCode", errorEle.element("errorCode").getTextTrim());
            return resolvedData;
        } else {
            //流水判断
            if (!transaction.getTransactionNo().getNumber().equals(
                    txEle.element("externalRefNumber").getTextTrim())) {
                throw new IllegalArgumentException("99Bill TR2 response transaction number error!");
            }
            //交易类型判断
            if (!isPurchaseTransaction(txEle.element("txnType").getTextTrim())) {
                throw new IllegalArgumentException("99Bill TR2 response transaction type error!");
            }
            // TODO 更多的检查
            resolvedData.put("OrdAmt", txEle.element("amount").getTextTrim());
            resolvedData.put("TrxId", txEle.element("refNumber").getTextTrim());
            resolvedData.put("OrdId", txEle.element("externalRefNumber").getTextTrim());
            resolvedData.put("InteractiveStatus", txEle.element("interactiveStatus").getTextTrim());
            resolvedData.put("RespCode", txEle.element("responseCode").getTextTrim());
            return resolvedData;
        }
    }

    public static Map<String, String> resolvePurchaseTr3PesponseData(String response) throws DocumentException {
        Map<String, String> resolvedData = new HashMap<String, String>();
        Document document = DocumentHelper.parseText(response);
        Element headElement = document.getRootElement();
        if (!responseVersionCheck(headElement.element("version").getTextTrim())) {
            throw new IllegalArgumentException("99Bill TR2 response version error!");
        }
        Element txEle = headElement.element("TxnMsgContent");
        if (txEle == null) {
            throw new IllegalArgumentException("99Bill TR3 response xml file can not resolve correctly!");
        } else {
            // TODO 更多的检查
            resolvedData.put("OrdAmt", txEle.element("amount").getTextTrim());
            resolvedData.put("TrxId", txEle.element("refNumber").getTextTrim());
            resolvedData.put("OrdId", txEle.element("externalRefNumber").getTextTrim());
            resolvedData.put("InteractiveStatus", txEle.element("interactiveStatus").getTextTrim());
            resolvedData.put("RespCode", txEle.element("responseCode").getTextTrim());
            resolvedData.put("ChkValue", txEle.element("signature").getTextTrim());
            //去掉签名后的报文数据
            resolvedData.put("MessageWithoutSignature", response.replace("<signature>"
                    + txEle.element("signature").getTextTrim()
                    + "</signature>", ""));
        }
        return resolvedData;
    }

    private static boolean responseVersionCheck(String version) {
        return Bill99Constants.VERSION.equals(version);
    }

    private static boolean isPurchaseTransaction(String transactionType) {
        return Bill99Constants.TRANSACTION_TYPE.equals(transactionType);
    }
}
