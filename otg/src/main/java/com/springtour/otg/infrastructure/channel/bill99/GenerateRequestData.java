/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author 005073
 */
public class GenerateRequestData {

    public static String generatePurchaseTr1XmlData(Transaction transaction, CardInfo cardInfo, String returnUrl, Date traDate) throws CannotMapIdentityTypeException {
        Bill99KeyManager bill99KeyManager = new Bill99KeyManager();
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        //根节点
        Element headElement = document.addElement("MasMessage", "http://www.99bill.com/mas_cnp_merchant_interface");
        //报文版本
        headElement.addElement("version").setText(Bill99Constants.VERSION);
        //报文内容
        Element txEle = headElement.addElement("TxnMsgContent");
        txEle.addElement("txnType").setText(Bill99Constants.TRANSACTION_TYPE);
        txEle.addElement("interactiveStatus").setText(Bill99Constants.INTERACTIVE_STATUS_REQUEST);
        txEle.addElement("cardNo").setText(cardInfo.getCardNo());
        txEle.addElement("expiredDate").setText(cardInfo.getExpireDate());
        txEle.addElement("cvv2").setText(cardInfo.getCvv2());
        txEle.addElement("amount").setText(transaction.getAmount().getAmount().toString());
        txEle.addElement("merchantId").setText(bill99KeyManager.merchantId(transaction.getMerchant().getKey()));
        txEle.addElement("terminalId").setText(transaction.getMerchantCode());
        txEle.addElement("idType").setText(Bill99IdentityTypeConverter.convert(cardInfo.getCardHolder().getIdType()));
        txEle.addElement("entryTime").setText(new SimpleDateFormat("yyyyMMddHHmmss").format(traDate));
        txEle.addElement("externalRefNumber").setText(transaction.getTransactionNo().getNumber());
        //客户号目前不需要
        //txEle.addElement("customerId").setText(cardInfo.getCardNo());
        txEle.addElement("cardHolderName").setText(cardInfo.getCardHolderFullname());
        txEle.addElement("cardHolderId").setText(cardInfo.getCardHolderIdNo());
        txEle.addElement("tr3Url").setText(returnUrl);
        return document.asXML();
    }

    public static String generatePurchaseTr4XmlData(String merchantCode, String merchantKey, String refNumber) {
        Bill99KeyManager bill99KeyManager = new Bill99KeyManager();
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        //根节点
        Element headElement = document.addElement("MasMessage", "http://www.99bill.com/mas_cnp_merchant_interface");
        //报文版本
        headElement.addElement("version").setText(Bill99Constants.VERSION);
        //报文内容
        Element txEle = headElement.addElement("TxnMsgContent");
        txEle.addElement("txnType").setText(Bill99Constants.TRANSACTION_TYPE);
        txEle.addElement("interactiveStatus").setText(Bill99Constants.INTERACTIVE_STATUS_ACKNOWLEDGE);
        txEle.addElement("merchantId").setText(bill99KeyManager.merchantId(merchantKey));
        txEle.addElement("terminalId").setText(merchantCode);
        txEle.addElement("refNumber").setText(refNumber);
        return document.asXML();
    }
}
