/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import lombok.Setter;
import cn.com.infosec.icbc.ReturnValue;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;

/**
 * @author Future
 * 
 */
public class IcbcRequestUrlAssembler {
	@Setter
	private Configurations configurations;
	
	@Setter
	private Encryption encryption;

	public String assembleUrl(Transaction transaction, String returnUrl,
			String ip) {
		AssembleTranDataForEncryption assembleTranDataForEncryption = new AssembleTranDataForEncryption();
		String merId = transaction.getMerchant().getKey().split(",")[0];
		String key = transaction.getMerchant().getKey().split(",")[1];
		String tranData = assembleTranDataForEncryption.transform(
				transaction.getWhenRequested(), transaction.getTransactionNo(),
				transaction.getAmount().getAmount(),
				transaction.getMerchantCode(), merId, ip, returnUrl);
		StringBuilder sBuilder = new StringBuilder(configurations.getIcbcPayRequestUrl());
		sBuilder.append("?interfaceName=").append(IcbcConstants.INTERFACE_NAME)
				.append("&");
		sBuilder.append("interfaceVersion=")
				.append(IcbcConstants.INTERFACE_VERSION).append("&");
		String tranDataBase64 = new String(ReturnValue.base64enc(tranData
				.getBytes()));
		sBuilder.append("tranData=").append(tranDataBase64.replaceAll("\r\n", "")).append("&");
		String signMsg = encryption.encrypt(tranData, key);
		sBuilder.append("merSignMsg=").append(signMsg.replaceAll("\r\n", "")).append("&");
		String cert = encryption.getCertBase64();
		sBuilder.append("merCert=").append(cert.replaceAll("\r\n", ""));
		return sBuilder.toString();
	}
}
