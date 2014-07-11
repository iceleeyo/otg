/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

/**
 *
 * @author Future
 */
public class Bill99Constants {
    
    public static final String CHARGED_SUCCESS = "00";
    public static final String TX_WAITING = "C0";
    public static final String INTERACTIVE_STATUS_REQUEST = "TR1";
    public static final String INTERACTIVE_STATUS_RETURN = "TR2";
    public static final String INTERACTIVE_STATUS_NOTIFY = "TR3";
    public static final String INTERACTIVE_STATUS_ACKNOWLEDGE = "TR4";
    
    public static final String ID_TYPE_IDENTITY = "0";
    public static final String ID_TYPE_PASSPORT = "1";
    public static final String ID_TYPE_OTHERS = "7";
    
    public static final String CERTIFICATE_TYPE = "X.509";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    
    public static final String HTTPS_SECURITY_ALGORITHM = "sunx509";
    public static final String KEY_TYPE = "JKS";
    public static final String SSL_TYPE = "SSL";
    
    public static final String VERSION = "1.0";
    public static final String TRANSACTION_TYPE = "PUR";
}
