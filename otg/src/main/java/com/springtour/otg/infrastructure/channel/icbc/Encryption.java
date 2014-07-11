/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import lombok.Setter;

import cn.com.infosec.icbc.ReturnValue;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;

/**
 * @author Future
 * 
 */
public class Encryption {
	@Setter
	private Configurations configurations;

	public String encrypt(String tranData, String key) {
		IcbcCertFileReader icbcCertFileReader = new IcbcCertFileReader();
		byte[] bs = icbcCertFileReader.fileReader(configurations
				.icbcKeyFileResourceName());
		String signMsg;
		try {
			signMsg = new String(ReturnValue.base64enc(ReturnValue.sign(
					tranData.getBytes(), tranData.getBytes().length, bs,
					key.toCharArray())));
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(
					"icbc encrypt failed!", e);
		}
		return signMsg;
	}

	public String getCertBase64() {
		IcbcCertFileReader icbcCertFileReader = new IcbcCertFileReader();
		byte[] bs = icbcCertFileReader.fileReader(configurations
				.icbcCertFileResourceName());
		return new String(ReturnValue.base64enc(bs));
	}

}
