/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.domain.model.transaction.Transaction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Future
 */
public class Bill99NotificationHttpServletRequestAssemblerWithBadTxnNo extends Bill99NotificationHttpServletRequestAssemblerImpl {

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
         MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/bill99/receive.htm");
        request.setContent(this.mockPurchaseTr3Message(transaction).getBytes());
        return request;
    }
    
    private String mockPurchaseTr3Message(Transaction transaction) {
        //TODO 快钱会记录卡号？
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        //根节点
        Element headElement = document.addElement("MasMessage", "http://www.99bill.com/mas_cnp_merchant_interface");
        //报文版本
        headElement.addElement("version").setText("1.0");
        //报文内容
        Element txEle = headElement.addElement("TxnMsgContent");
        txEle.addElement("txnType").setText("PUR");
        txEle.addElement("interactiveStatus").setText("TR3");
        txEle.addElement("storableCardNo").setText("4392255778");
        txEle.addElement("amount").setText(transaction.getAmount().getAmount().toString());
        txEle.addElement("merchantId").setText(transaction.getMerchantCode());
        txEle.addElement("terminalId").setText("00000001");
        txEle.addElement("entryTime").setText("20071219011215");
        txEle.addElement("externalRefNumber").setText("1-23i-0j");
        txEle.addElement("transTime").setText("20071219011216");
        txEle.addElement("refNumber").setText("000000000012");
        txEle.addElement("responseCode").setText("00");
        txEle.addElement("cardOrg").setText("CU");
        txEle.addElement("issuer").setText("招商银行");
        txEle.addElement("signature").setText("3sfwef321sf54s3f43we21fsd2f1");
        return document.asXML();
    }
}
