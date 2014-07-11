package com.springtour.otg.infrastructure.channel.alipaywap;

public class AlipayWapConfig {

	// 商户的私钥
	// 如果签名方式设置为“0001”时，请设置该参数
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALsWpv1V7PmUttne"
			+ "bXENfN+5Bjh6ERNH9BiuXAE5JgFSo+B0f48DgBmK7bBSgnXQ4uydC+G6mYg/En7S"
			+ "WBh0pfrnrHJxhv3McO9ao0if2PauOhYGZ+qhhRVG+I4GTyyj8sN6TxzA5pPTTATa"
			+ "PFKjU/96TVRFz4UEpJGD9ILG2HUBAgMBAAECgYBsC3D07SKmIFRZDjN2DqwHJ/uH"
			+ "sjcaQ3ucpVVM/4wLR4aMobrpBMR9+9W49m2RTMlAjJHIEtOH86TiyOdHeFZByxVK"
			+ "kLyVlNLufb9TDL4wf3MvCaVgP11ZxWPOSiFNO6BWAciY0U1bsiXlOJxA3M9Wbmao"
			+ "uYx1JvKeiWWYOSHm4QJBAOqq4VZIc1Gp83dync8NI5xZjNC4wJqJePDRm+trFuF2"
			+ "OFe9k89PKSbeI/OofF8NXDjObiGeVPGtXTjtNVwKndMCQQDMGIafSAWSsOJXpvLr"
			+ "Ffm4n3NQ7SmrqNAaNpzysGnC094ubaRBy+zCmqH6wECWhgi6x04/+GE89RxnI23k"
			+ "fFlbAkB2RDqeppLf1FwsleosgH2y/Xq3cnU3LRNzouAGzIjShictKREGSOn/ebL6"
			+ "yK4tpWkWBAuDC+3ZgzrZRss+oOcbAkEAlMzI0bYrwgBOdUNV1gmmkHFIHNdcSzf/"
			+ "ySzKDrKJ2S6ozUbIu+PWkSc2M2AZduTv1mVa6o7HLi8ybwvIog37AwJBAIajAWV5"
			+ "dUuPQS9N5/egeFRdq43fpjZ0g2BycAWxjG9jTtEnid1JECDuer8uk3xQVKP9vFfO"
			+ "IRnohh31clMwnc4=";

	// 支付宝的公钥
	// 如果签名方式设置为“0001”时，请设置该参数
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY/BlX/kFQ+752XrGJ7m3dqc3ytMRX1Y5sqNAk hHZENc+4TxczXDldWYHEU5fuB1sv93Nt/9y2OpkWmYMI8Li4WrIv3ak0qi/how7sX/JHPe66Jb1K TwzqbPjreOBJP11GlWz4YmPSWkvCAJX9enFo/vidBoNka4qeDOmQJxZONwIDAQAB";

	// 字符编码格式 目前支持 utf-8
	public static String input_charset = "utf-8";

	// 签名方式，选择项：0001(RSA)、MD5
	public static String sign_type = "MD5";
	// 无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA

	// 返回格式
	public static String format = "xml";
	// 必填，不需要修改

	// 返回格式
	public static String v = "2.0";
	
	public static String CONFIRMATION_FLAG = "success";

}
