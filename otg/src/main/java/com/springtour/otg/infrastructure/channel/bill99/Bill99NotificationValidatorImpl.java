/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

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
import sun.misc.BASE64Decoder;

/**
 *
 * @author Future
 */
public class Bill99NotificationValidatorImpl implements NotificationValidator {

    //
    private Configurations configurations;

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }

    @Override
    public boolean validate(Map<String, String> originalNotification) throws CannotLaunchSecurityProcedureException {
        String message = originalNotification.get("MessageWithoutSignature");
        String signature = originalNotification.get("ChkValue");
        return this.signatureValidate(message, signature);
    }

    private Boolean signatureValidate(String message, String signature) throws CannotLaunchSecurityProcedureException {
        try {
            CertificateFactory cf = CertificateFactory.getInstance(Bill99Constants.CERTIFICATE_TYPE);
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(this.getClass().getResourceAsStream(configurations.bill99PublicKeyClasspathResourceName()));
            PublicKey pk = certificate.getPublicKey();
            Signature signatureTool = Signature.getInstance(Bill99Constants.SIGNATURE_ALGORITHM);
            signatureTool.initVerify(pk);
            signatureTool.update(message.getBytes());
            BASE64Decoder decoder = new BASE64Decoder();
            return signatureTool.verify(decoder.decodeBuffer(signature));
        } catch (IOException ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex.getCause());
        } catch (InvalidKeyException ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex.getCause());
        } catch (SignatureException ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex.getCause());
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex.getCause());
        } catch (CertificateException ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex.getCause());
        }
    }
}
