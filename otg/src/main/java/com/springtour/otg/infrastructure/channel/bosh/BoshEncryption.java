/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import lombok.Setter;

import com.koalii.svs.SvsSign;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-8
 */
public class BoshEncryption {
    
    @Setter
    private Configurations configurations;
    
    public void encryptionForMap(Transaction transaction, String signDataStr, Map<String, String> parms) {
        // 签名，获得签名结果（Base64编码）
        String signData = "";
        // 获得签名证书的Base64编码
        String koalB64Cert = "";
        try {
            SvsSign signer = new SvsSign();
            String certFilePath = this.getClass().getResource(configurations.getBoshCertFileResourceName()).getPath();
            signer.initSignCertAndKey(certFilePath, transaction.getMerchant().getKey());//第二个参数为导出证书是的秘钥，正式和测试都为000000
            signData = signer.signData(signDataStr.getBytes());
            koalB64Cert = signer.getEncodedSignCert();
        } catch (Exception e) {
            throw new CannotLaunchSecurityProcedureException(
                    "bosh encrypt failed!", e);
        } 
        parms.put("signData", signData);
        parms.put("signDataStr", signDataStr);
        parms.put("KoalB64Cert", koalB64Cert);
    }
    
    public void encryptionForList(Transaction transaction, String signDataStr, List<BasicNameValuePair> nvps) {
        // 签名，获得签名结果（Base64编码）
        String signData = "";
        // 获得签名证书的Base64编码
        String koalB64Cert = "";
        try {
            SvsSign signer = new SvsSign();
            String certFilePath = this.getClass().getResource(configurations.getBoshCertFileResourceName()).getPath();
            signer.initSignCertAndKey(certFilePath, transaction.getMerchant().getKey());//第二个参数为导出证书是的秘钥，正式和测试都为000000
            signData = signer.signData(signDataStr.getBytes());
            koalB64Cert = signer.getEncodedSignCert();
        } catch (Exception e) {
            throw new CannotLaunchSecurityProcedureException(
                    "bosh encrypt failed!", e);
        } 
        nvps.add(new BasicNameValuePair("signData", signData));
        nvps.add(new BasicNameValuePair("signDataStr", signDataStr));
        nvps.add(new BasicNameValuePair("KoalB64Cert", koalB64Cert));
    }
}
