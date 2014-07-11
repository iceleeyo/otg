package com.springtour.otg.infrastructure.channel.bocomm;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMSetting;
import com.bocom.netpay.b2cAPI.NetSignServer;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.RequestUtils;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class BocommRequestUrlAssembler {
	
	@Setter
	private Configurations configurations;
	
	Logger log = LoggerFactory.getLogger();
	
	public String assembleUrl(Transaction transaction, String returnUrl,
			String ip) {
		
		BOCOMB2CClient client = new BOCOMB2CClient();
		String path = this.getClass().getResource(configurations.getBocommXmlFileResourceName()).getPath();

		log.info("交通银行配置文件路径："+path);
        
		int ret = client.initialize(path); //该代码只需调用一次

        if (ret != 0) {
        	log.error("初始化失败,错误信息：" + client.getLastErr());
        	throw new RuntimeException("初始化失败,错误信息：" + client.getLastErr());
        }
		String interfaceVersion = BocommConstants.INTERFACE_VERSION;
		
		String merID = BOCOMSetting.MerchantID;                
		String orderid = transaction.getTransactionNo().getNumber();         
		String orderDate = new SimpleDateFormat("yyyyMMdd").format(transaction.getWhenRequested());             
		String orderTime = new SimpleDateFormat("HHmmss").format(transaction.getWhenRequested());             
		String tranType = BocommConstants.TRANTYPE;              
		String amount = transaction.getAmount().getAmount().setScale(2).toString();                
		String curType = BocommConstants.CNY;               
		String orderContent = "上海春秋国旅旅游产品";          
		String orderMono = "";             
		String phdFlag = "";               
		String notifyType = BocommConstants.NOTIFY_TYPE;            
		String merURL = "";                
		String goodsURL = returnUrl;              
		String jumpSeconds = "";           
		String payBatchNo = "";           
		String proxyMerName = "";          
		String proxyMerType = "";          
		String proxyMerCredentials = "";   
		String netType = BocommConstants.NET_TYPE;
		String merSignMsg = "";
		String sourceMsg = interfaceVersion + "|" + merID + "|" + orderid + "|" + orderDate + "|" + orderTime + "|" + tranType + "|" + amount + "|" + curType + "|" + orderContent + "|" + orderMono + "|" + phdFlag + "|" + notifyType + "|" + merURL + "|" + goodsURL + "|" + jumpSeconds + "|" + payBatchNo + "|" + proxyMerName + "|" + proxyMerType + "|" + proxyMerCredentials + "|" + netType;
		NetSignServer nss = new NetSignServer();
        String merchantDN = BOCOMSetting.MerchantCertDN;
        try {
			nss.NSSetPlainText(sourceMsg.getBytes("GBK"));
			byte bSignMsg [] = nss.NSDetachedSign(merchantDN);

	        if (nss.getLastErrnum() < 0) {
	            log.error("ERROR:商户端签名失败");
	        }

	        merSignMsg = new String(bSignMsg, "GBK");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
        StringBuilder url = new StringBuilder(configurations.getBocommRequestUrl());
		url.append("?");
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("interfaceVersion", interfaceVersion);
		parms.put("merID", merID);
		parms.put("orderid", orderid);
		parms.put("orderDate", orderDate);
		parms.put("orderTime", orderTime);
		parms.put("tranType", tranType);
		parms.put("amount", amount);
		parms.put("curType", curType);
		parms.put("orderContent", orderContent);
		parms.put("orderMono", orderMono);
		parms.put("phdFlag", phdFlag);
		parms.put("notifyType", notifyType);
		parms.put("merURL", merURL);
		parms.put("goodsURL", goodsURL);
		parms.put("jumpSeconds", jumpSeconds);
		parms.put("payBatchNo", payBatchNo);
		parms.put("proxyMerName", proxyMerName);
		parms.put("proxyMerType", proxyMerType);
		parms.put("proxyMerCredentials", proxyMerCredentials);
		parms.put("netType", netType);
		parms.put("merSignMsg", merSignMsg);
		url.append(RequestUtils.joinMapValue(parms, '&'));
		log.info("交通银行提交Form："+RequestUtils.generateAutoSubmitForm(configurations.getBocommRequestUrl(), parms));
		return url.toString();
	}
	
}
