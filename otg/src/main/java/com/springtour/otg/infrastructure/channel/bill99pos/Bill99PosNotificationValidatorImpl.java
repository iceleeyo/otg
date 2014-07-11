/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99pos;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

public class Bill99PosNotificationValidatorImpl implements
		NotificationValidator {

	//
	private Configurations configurations;

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {
		String message = assemblySignatureString(originalNotification);
		String signature = originalNotification.get("signature");
		return this.signatureValidate(message, signature);
	}

	private Boolean signatureValidate(String message, String signature)
			throws CannotLaunchSecurityProcedureException {
		boolean flag = false;
		try {
			CertificateFactory cf = CertificateFactory.getInstance(Bill99PosConstants.CERTIFICATE_TYPE);
			X509Certificate certificate = (X509Certificate) cf.generateCertificate(this.getClass().getResourceAsStream(configurations.bill99PosPublicKeyClasspathResourceName()));
            PublicKey pk = certificate.getPublicKey();
			Signature signatureTool = Signature.getInstance(Bill99PosConstants.SIGNATURE_ALGORITHM);
            signatureTool.initVerify(pk);
            signatureTool.update(message.getBytes());
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			flag = signatureTool.verify(decoder.decodeBuffer(signature));
		} catch (IOException ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		} catch (InvalidKeyException ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		} catch (SignatureException ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		} catch (NoSuchAlgorithmException ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		} catch (CertificateException ex) {
			throw new CannotLaunchSecurityProcedureException(ex.getMessage(),
					ex.getCause());
		}
		return flag;
	}
	
	private String assemblySignatureString(
			Map<String, String> originalNotification) {
		StringBuffer sb = new StringBuffer();
		sb.append(originalNotification.get("processFlag")).append(
				originalNotification.get("txnType")).append(
				originalNotification.get("orgTxnType"));
		sb.append(originalNotification.get("amt")).append(
				originalNotification.get("externalTraceNo")).append(
				originalNotification.get("orgExternalTraceNo")).append(
				originalNotification.get("terminalOperId"));
		sb.append(originalNotification.get("authCode")).append(
				originalNotification.get("RRN")).append(
				originalNotification.get("txnTime")).append(
				originalNotification.get("shortPAN")).append(
				originalNotification.get("responseCode"));
		sb.append(originalNotification.get("cardType")).append(
				originalNotification.get("issuerId"));
		return sb.toString();
	}
}
