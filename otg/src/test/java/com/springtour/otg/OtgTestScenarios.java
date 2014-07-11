package com.springtour.otg;

import java.math.BigDecimal;

public class OtgTestScenarios {

    public static final String AN_AVAILABLE_TR_NO = "2012020108790905";
    public static final String A_UNAVAILABLE_MERCHANT_ORG = "-111";
    public static final String AN_AVAILABLE_MERCHANT_ORG = "61";
    public static final String AN_AVAILABLE_MERCHANT_NAME = "春秋国旅";
    public static final String AN_AVAILABLE_MERCHANT_CODE = "198819982008001";
    public static final String AN_AVAILABLE_TERMINAL_ID = "198819982";
    public static final String A_PARTNER_AVAILABLE_CAHNNEL = "12";
    public static final String A_UNAVAILABLE_PAYMENT_APP = "-1";
    public static final String AN_ALWAYS_SUCCEED_PAYMENT_APP = "1";
    public static final String AN_ALWAYS_FAILED_PAYMENT_APP = "2";
    public static final String AN_ALWAYS_ERROR_PAYMENT_APP = "3";
    public static final String AN_AVAILABLE_GATEWAY = "CCB";
    public static final BigDecimal A_PAYABLE_AMOUNT = BigDecimal.valueOf(100.015);
    public static final String CNY = "CNY";
    public static final String A_UNSUPPORTED_CURRENCY = "GBP";
    public static final String A_UNRECOGNIZED_CURRENCY = "";
    public static final String AN_EXIST_CARDNO = "5528010000000001";
    public static final String AN_EXIST_EXPIREDATE = "0213";
    public static final String AN_EXIST_CVV2 = "456";
    public static final String AN_EXIST_CARDHOLDER_FULLNAME = "testUser";
    public static final String AN_EXIST_CARDHOLDER_ID_NO = "42155598648546569854";
    public static final String AN_EXIST_CARDHOLDER_ID_TYPE = "identity";
    public static final String A_UNSUPPORTED_CAHNNEL = "-1";
    public static final String A_UNAVAILABLE_CAHNNEL = "1";
    public static final String AN_AVAILABLE_IP_FOR_TENPAY_REQUEST = "http://127.0.0.1";
    public static final String AN_AVAILABLE_RETURN_URL_REQUEST_WITHOUTCARD = "http://www.springtour.com/web/return.htm";

    //public static final Notification A_SAMPLE_NOTIFICATION_FROM_CHINAPNR = aSampleNotificationFromChinapnr();
    //public static final Transaction A_SAMPLE_TRANSACTION_OF_CHINAPNR = aSampleTransactionOfChinapnr();
//	private static Notification aSampleNotificationFromChinapnr() {
//		return new Notification(null, null, null, null, false, null, null);
//	}
//	private static Transaction aSampleTransactionOfChinapnr() {
//		Transaction txn =  new Transaction();
//		txn.setTransactionNo(new TransactionNo(""));
//		return txn;
//	}
    private OtgTestScenarios() {
    }
}
