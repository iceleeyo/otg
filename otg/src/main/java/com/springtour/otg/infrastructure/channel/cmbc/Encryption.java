package com.springtour.otg.infrastructure.channel.cmbc;

import java.io.*;
import java.net.URLEncoder;

import lombok.Setter;

import com.cmbc.cc.gateway.security.jca.*;
import com.cmbc.cc.gateway.security.jce.*;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;

public class Encryption {

	@Setter
	private Configurations configurations;

	public String encrypt(String toEncryptStr) throws Exception {
		try {
			// 数据加密
			DESEncrypt desEncrypt = new DESEncrypt();
			String keyStr = desEncrypt.encrypty(toEncryptStr);
			// 生成数字信封
			RSAEncrypt rsaEncrypt = new RSAEncrypt(getKey(
					configurations.cmbcPublicKeyClasspathResourceName())
					.replaceAll("\n", ""));
			String str = rsaEncrypt.encrypty(keyStr);
			// 对加密数据进行签名
			SignatureEncrypt signatureEncypt = new SignatureEncrypt(getKey(
					configurations.cmbcPrivateKeyClasspathResourceName())
					.replaceAll("\n", ""));
			String encyptStr = signatureEncypt.encrypty(str);
			// 针对特殊字符，需对上送数据进行UTF8 转码
			String encryptedStr = URLEncoder.encode(encyptStr, "UTF-8");
			return encryptedStr;
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(
					"cmbc encrypt failed!", e);
		}
	}

	public String decrypt(String toDecryptStr) {
		try {
			SignatureDecrypt signaturedecypt = new SignatureDecrypt(
					getKey(configurations.cmbcPublicKeyClasspathResourceName()),
					toDecryptStr.split(",")[1]);
			String signaturedecyptStr = signaturedecypt.decrypty(toDecryptStr
					.split(",")[0]);
			RSADecrypt rsaDecrypt = new RSADecrypt(
					getKey(configurations.cmbcPrivateKeyClasspathResourceName()));
			String rsaDecryptStr = rsaDecrypt.decrypty(signaturedecyptStr);
			DESDecrypt desDecrypt = new DESDecrypt();
			String decryptedStr = desDecrypt.decrypty(rsaDecryptStr);
			return decryptedStr;
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(
					"cmbc decrypt failed! ToDecryptStr is [" + toDecryptStr
							+ "]", e);
		}
	}

	private String getKey(String path) throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream(path);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			return reader.readLine();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				throw e;
			}

		}
	}

}
