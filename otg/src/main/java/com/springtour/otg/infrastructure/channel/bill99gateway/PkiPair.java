/**
 * 
 */
package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.springtour.otg.application.util.Configurations;

/**
 * @author Future
 * 
 */
public class PkiPair {
	private Configurations configurations;

	/**
	 * 对传入的MSG进行PKI加密
	 * 
	 * @param merchantCode
	 * @param key
	 * @param msg
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public String signMsg(String merchantCode, String key, String msg)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException,
			InvalidKeyException, SignatureException {
		String signedMsg = "";
		// 密钥仓库
		KeyStore ks = KeyStore.getInstance(Bill99GatewayConstants.KEY_TYPE);

		// 读取密钥数据
		BufferedInputStream ksbufin = new BufferedInputStream(
				this
						.getClass()
						.getResourceAsStream(
								configurations
										.bill99GatewayPrivateKeyClasspathResourceName(merchantCode)));

		// 从密钥仓库得到私钥
		char[] keyPwd = key.toCharArray();
		ks.load(ksbufin, keyPwd);
		PrivateKey priK = (PrivateKey) ks.getKey(
				Bill99GatewayConstants.KEY_NAME, keyPwd);
		Signature signature = Signature
				.getInstance(Bill99GatewayConstants.SIGNATURE_ALGORITHM);
		signature.initSign(priK);
		signature.update(msg.getBytes("utf-8"));
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		signedMsg = encoder.encode(signature.sign());

		return signedMsg;
	}

	/**
	 * 验签
	 * 
	 * @param val
	 * @param msg
	 * @return
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public boolean isEnCodedByCer(String val, String msg)
			throws CertificateException, NoSuchAlgorithmException,
			InvalidKeyException, SignatureException, IOException {
		boolean flag = false;
		// 获得文件(绝对路径)
		BufferedInputStream inStream = new BufferedInputStream(this.getClass()
				.getResourceAsStream(
						configurations
								.bill99GatewayPublicKeyClasspathResourceName()));

		CertificateFactory cf = CertificateFactory
				.getInstance(Bill99GatewayConstants.CERTIFICATE_TYPE);
		X509Certificate cert = (X509Certificate) cf
				.generateCertificate(inStream);
		// 获得公钥
		PublicKey pk = cert.getPublicKey();
		// 签名
		Signature signature = Signature
				.getInstance(Bill99GatewayConstants.SIGNATURE_ALGORITHM);
		signature.initVerify(pk);
		signature.update(val.getBytes("utf-8"));
		// 解码
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		flag = signature.verify(decoder.decodeBuffer(msg));
		return flag;
	}

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}
}
