/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Future
 */
public class SavingTrustManager implements X509TrustManager{
    private final X509TrustManager tm;
    private X509Certificate[] chain;
    
    public SavingTrustManager(X509TrustManager tm) {
        this.tm = tm;
    }
    
    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
    
}
