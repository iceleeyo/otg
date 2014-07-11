/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcCustom;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcMessage;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcOrderInfo;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcPayRequest;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcSubOrderInfo;
import com.thoughtworks.xstream.XStream;

/**
 * @author Future
 * 
 */
public class AssembleTranDataForEncryption {
	public String transform(Date transactionDate, TransactionNo transactionNo,
			BigDecimal amount, String code, String merId, String ip,
			String returnUrl) {
		IcbcPayRequest request = new IcbcPayRequest();
		IcbcOrderInfo orderInfo = new IcbcOrderInfo();
		orderInfo.setOrderDate(transactionDate);// 交易日期时间
		orderInfo.setMerId(merId);// 商户代码
		List<IcbcSubOrderInfo> subOrderInfos = new ArrayList<IcbcSubOrderInfo>();
		orderInfo.setSubOrderInfos(subOrderInfos);
		IcbcSubOrderInfo subOrderInfo = new IcbcSubOrderInfo();
		subOrderInfo.setTransactionId(transactionNo.getNumber());// 流水
		subOrderInfo.setAmount(String.valueOf(amount.multiply(
				BigDecimal.valueOf(100L)).intValue()));// 订单金额
		subOrderInfo.setMerAcct(code);// 商户账号
		subOrderInfos.add(subOrderInfo);
		request.setOrderInfo(orderInfo);
		request.setCustom(new IcbcCustom());
		IcbcMessage message = new IcbcMessage();
		message.setMerReference("*.springtour.com");
		//message.setMerCustomIp(ip);// 用户ip
		message.setMerUrl(returnUrl);// 返回的url
		request.setMessage(message);

		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);

		return IcbcConstants.REQ_XML_HEAD + xStream.toXML(request);
	}
}
