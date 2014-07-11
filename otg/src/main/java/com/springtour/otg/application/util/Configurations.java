/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author 001595
 */
public class Configurations {

	/**
	 * 所在应用的外网地址
	 */
	private String globalApplicationUrl;
	/**
	 * 所在应用的内网地址
	 */
	private String localApplicationUrl;
	/**
	 * Chinapnr 公钥文件名
	 */
	private String chinapnrPublicKeyFileName;
	/**
	 * Chinapnr 接口地址通信模式
	 */
	private String chinapnrRequestScheme;
	/**
	 * Chinapnr 接口地址主机
	 */
	private String chinapnrRequestHost;
	/**
	 * Chinapnr 接口地址端口
	 */
	private String chinapnrRequestPort;
	/**
	 * Chinapnr 接口地址路径
	 */
	private String chinapnrRequestPath;
	/**
	 * Chinpnr 通知地址路径
	 */
	private String chinapnrNotifyPath;
	/**
	 * 春秋商旅卡请求地址
	 */
	private String springcardRequestUrl;
	/**
	 * 春秋商旅卡通知地址路径
	 */
	private String springcardNotifyPath;
	/**
	 * 财付通请求地址
	 */
	private String tenpayRequestUrl;
	/**
	 * 财付通通知地址路径
	 */
	private String tenpayNotifyPath;
	/**
	 * 财付通交易查询地址
	 */
	@Getter
	@Setter
	private String tenpayQueryUrl;
	/**
	 * 支付宝请求地址
	 */
	private String alipayRequestUrl;
	/**
	 * 民生信用卡请求支付地址
	 */
	@Getter
	@Setter
	private String cmbcRequestUrl;
	/**
	 * 民生信用卡查询支付地址
	 */
	@Getter
	@Setter
	private String cmbcQueryUrl;
	/**
	 * 民生银行私钥地址
	 */
	@Setter
	private String cmbcPrivateKeyFileName;
	/**
	 * 民生银行公钥地址
	 */
	@Setter
	private String cmbcPublicKeyFileName;
	/**
	 * 建行直连请求地址
	 */
	private String ccbRequestUrl;
	/**
	 * 建行信用卡分期直连请求地址
	 */
	@Getter
	@Setter
	private String ccbInstallmentRequestUrl;
	/**
	 * 快钱 公钥文件名
	 */
	private String bill99PublicKeyFileName;
	/**
	 * 快钱POS 公钥文件名
	 */
	@Setter
	@Getter
	private String bill99PosPublicKeyFileName;
	/**
	 * 快钱请求地址
	 */
	private String bill99RequestUrl;
	/**
	 * 快钱通知地址路径
	 */
	private String bill99NotifyPath;
	/**
	 * 快钱网关支付通知地址
	 */
	private String bill99GatewayNotifyPath;
	/**
	 * 快钱网关支付请求地址
	 */
	private String bill99GatewayRequestUrl;
	/**
	 * 快钱人民币网关 公钥文件名
	 */
	private String bill99GatewayPublicKeyFileName;
	@Setter
	/**
	 * 工行证书文件名
	 */
	private String icbcCertFileName;
	@Setter
	/**
	 * 工行私钥文件名
	 */
	private String icbcKeyFileName;
	@Setter
	@Getter
	/**
	 * 工行请求地址
	 */
	private String icbcPayRequestUrl;
	@Setter
	/**
	 * 工行验签文件名
	 */
	private String icbcVerifySignPubFileName;
	/**
	 * 工行JKS证书名
	 */
	@Setter
	private String icbcJksFileName;
	@Setter
	@Getter
	/**
	 * 工行交易查询地址
	 */
	private String icbcQueryRequestUrl;
	/**
	 * Otg 运行时配置文件Classpath根目录
	 */
	private static final String OTG_RUNTIME_CLASSPATH = "/com/springtour/runtime/otg/";

	//支付宝wap支付开始
	/**
	 * 支付宝wap支付请求地址
	 */
	@Setter
	@Getter
	private String alipayWapRequestUrl = "https://mcashier.alipaydev.com/service/rest.htm";
	
	/**
	 * 支付宝wap支付验证地址
	 */
	public String getAlipayWapVerfyUrl(){
		return alipayWapQueryUrl + "?service=notify_verify&";
	}
	
	/**
	 * 支付宝wap查询交易地址
	 */
	@Setter
	@Getter
	private String alipayWapQueryUrl = "https://openapi.alipaydev.com/gateway.do";
	
	/**
	 * 支付宝wap地址
	 */
	@Setter
	private String alipayWapNotifyPath;
	
	public String getAlipayWapNotifyUrl(){
		return globalApplicationUrl + alipayWapNotifyPath;
	}
	
	//支付宝wap支付结束
	
	// 对接中国银联参数配置开始
	// 基础网址（请按相应环境切换）
	/* 测试环境 */
//	@Setter
//	private String upopBaseURL = "http://58.246.226.99/UpopWeb/api/";
	@Setter
	private String upopQueryURL = "http://58.246.226.99/UpopWeb/api/";
	/* PM环境（准生产环境） */
	@Setter
	private String upopBaseURL = "https://www.epay.lxdns.com/UpopWeb/api/";

	/* 生产环境 */
//	@Setter
//	private String upopBaseURL = "https://unionpaysecure.com/api/";
//	@Setter
//	private String upopPayURL = "https://query.unionpaysecure.com/api/";	
	

	
	// 交易超时时间
	@Setter
	@Getter
	public String transTimeout = "300000";
	
	//中国银联接收通知地址
	@Setter
	@Getter
	private String upopNotifyUrl;

	// 支付网址
	public String getUpopGateway(){
		return upopBaseURL + "Pay.action";
	}

	// 后续交易网址
	public String getUpopBackstageGateway(){
		return upopBaseURL + "BSPay.action";
	}

	// 查询网址
	public String getUpopQueryUrl(){
		return upopQueryURL + "Query.action";
	}
	
	// 对接中国银联参数配置结束
	
	// 对接招商银行参数配置开始*********************
	
	/**
	 * 招商请求支付地址
	 */
	@Getter
	@Setter
	private String cmbRequestUrl;
	
	/**
	 * 招商请求支付地址
	 */
	@Getter
	@Setter
	private String cmbConnectionType;
	
	/**
	 * 招商商户开户分行号
	 */
	@Setter
	@Getter
	private String cmbBranchId;
	
	// 对接招商银行参数配置结束**********************

	// 对接上海银行参数配置开始**********************
	
	/**
	 *  上海银行支付测试环境地址
	 */
	@Setter
    @Getter
	private String boshRequestUrl = "https://218.106.61.135:441/boscartoon/netpay.do";
	
	/**
     *  上海银行订单查询测试环境地址
     */
	@Setter
    @Getter
    private String boshQueryUrl = "https://218.106.61.135:441/boscartoon/orderquery.do";
	
	@Setter
    private String boshNotifyUrl;
    
	@Setter
    private String boshCertFileName = "boshcer.pfx";
    
	@Setter
    private String boshPublicKeyName = "bankofshanghai_test.cer";
	
    /**
     *  上海银行支付后台通知地址
     */
	public String getBoshNotifyUrl() {
	    return globalApplicationUrl + boshNotifyUrl;
	}
	
	/**
     *  获得上海银行支付证书文件名（含路径）
     */
	public String getBoshCertFileResourceName() {
	    return securityRepository() + boshCertFileName;
	}
	
	/**
     *  获得上海银行支付证书文件名（含路径）
     */
    public String getBoshPublicKeyPath() {
        return securityRepository() + boshPublicKeyName;
    }
	
	// 对接上海银行参数配置结束**********************
    
 // 对接中国银行参数配置开始**********************
    /**
     *  中国银行支付请求地址
     */
    @Setter
    @Getter
    private String bocRequestUrl="https://180.168.146.75:81/RecvOrder.do";
    
    /**
     *  中国银行查询交易地址
     */
    @Setter
    @Getter
    private String bocQueryUrl;
    
    @Setter
    private String bocCertFileName;
    
    /**
     *  获得中国银行支付证书文件名（含路径）
     */
    public String getBocCertFileResourceName() {
        return securityRepository() + bocCertFileName;
    }
    
    // 对接中国银行参数配置结束**********************
	
	// 对接交通银行参数配置开始**********************
	
	/**
	 *  交通银行支付请求地址
	 */
	@Setter
    @Getter
	private String bocommRequestUrl;
	
	@Setter
    private String bocommNotifyUrl;
	
	@Setter
    private String bocommXmlFileName;
	
	@Setter
	private String bocommPfxFileName;
	
	@Setter
	private String bocommCertFileName;
	/**
     *  获得交通银行配置文件地址（含路径）
     */
	public String getBocommXmlFileResourceName() {
	    return securityRepository() + bocommXmlFileName;
	}
	
	public String getBocommPfxFileName() {
	    return securityRepository() + bocommPfxFileName;
	}
	
	public String getBocommCertFileName() {
	    return securityRepository() + bocommCertFileName;
	}
	
	/**
     *  交通银行支付后台通知地址
     */
	public String getBocomNotifyUrl() {
	    return globalApplicationUrl + bocommNotifyUrl;
	}
	
	
	// 对接交通银行参数配置结束**********************
	
	/**
	 * 所在应用的外网地址
	 */
	public String globalApplicationUrl() {
		return globalApplicationUrl;
	}

	/**
	 * 所在应用的内网地址
	 */
	public String localApplicationUrl() {
		return localApplicationUrl;
	}

	/**
	 * 安全文件仓库，用于存放密钥、公钥
	 * 
	 * @return
	 */
	public String securityRepository() {
		return OTG_RUNTIME_CLASSPATH;
	}

	/**
	 * 获得Chinapnr公钥文件名（含路径）
	 * 
	 * @return
	 */
	public String chinapnrPublicKeyClasspathResourceName() {
		return securityRepository() + chinapnrPublicKeyFileName;
	}

	/**
	 * 获得Chinapnr密钥文件名（含路径）
	 * 
	 * @return
	 */
	public String chinapnrPrivateKeyClasspathResourceName(String merchantKey) {
		return securityRepository() + merchantKey;
	}

	/**
	 * 获取接受Chinapnr通知的地址
	 * 
	 * @return
	 */
	public String chinapnrNotifyUrl() {
		return this.globalApplicationUrl + chinapnrNotifyPath;
	}

	/**
	 * 获取接受Springcard通知的地址
	 * 
	 * @return
	 */
	public String springcardNotifyUrl() {
		return this.globalApplicationUrl + springcardNotifyPath;
	}

	/**
	 * 获取接受Tenpay通知的地址
	 * 
	 * @return
	 */
	public String tenpayNotifyUrl() {
		return this.globalApplicationUrl + tenpayNotifyPath;
	}

	/**
	 * 获取接受Bill99通知的地址
	 * 
	 * @return
	 */
	public String bill99NotifyUrl() {
		return this.globalApplicationUrl + bill99NotifyPath;
	}

	/**
	 * 获得快钱公钥文件名（含路径）
	 * 
	 * @return
	 */
	public String bill99PublicKeyClasspathResourceName() {
		return securityRepository() + bill99PublicKeyFileName;
	}

	/**
	 * 获得快钱POS公钥文件名（含路径）
	 * 
	 * @return
	 */
	public String bill99PosPublicKeyClasspathResourceName() {
		return securityRepository() + bill99PosPublicKeyFileName;
	}

	/**
	 * 获得快钱密钥文件名（含路径）
	 * 
	 * @return
	 */
	public String bill99PrivateKeyClasspathResourceName(String merchantCode) {
		return this.securityRepository() + merchantCode + "90.jks";
	}

	/**
	 * 获得快钱人民币网关支付密钥文件名（含路径）
	 * 
	 * @param merchantCode
	 * @return
	 */
	public String bill99GatewayPrivateKeyClasspathResourceName(
			String merchantCode) {
		return this.securityRepository() + merchantCode + ".pfx";
	}

	/**
	 * 获得民生银行网关支付公钥文件名（含路径）
	 * 
	 * @return
	 */
	public String cmbcPublicKeyClasspathResourceName() {
		return securityRepository() + cmbcPublicKeyFileName;
	}

	/**
	 * 获得民生银行网关支付私钥文件名（含路径）
	 * 
	 * @return
	 */
	public String cmbcPrivateKeyClasspathResourceName() {
		return securityRepository() + cmbcPrivateKeyFileName;
	}

	public String bill99GatewayPublicKeyClasspathResourceName() {
		return securityRepository() + bill99GatewayPublicKeyFileName;
	}

	public String bill99GatewayNotifyUrl() {
		return this.globalApplicationUrl + bill99GatewayNotifyPath;
	}

	/**
	 * 获得工行支付证书文件名（含路径）
	 * 
	 * @return
	 */
	public String icbcCertFileResourceName() {
		return securityRepository() + icbcCertFileName;
	}

	/**
	 * 获得工行支付私钥文件名（含路径）
	 * 
	 * @return
	 */
	public String icbcKeyFileResourceName() {
		return securityRepository() + icbcKeyFileName;
	}

	/**
	 * 获得工行验签公钥文件名（含路径）
	 * 
	 * @return
	 */
	public String icbcVerifySignPubCertResourceName() {
		return securityRepository() + icbcVerifySignPubFileName;
	}

	public String getIcbcJksFileName() {
		return securityRepository() + icbcJksFileName;
	}

	public void setChinapnrPublicKeyFileName(String chinapnrPublicKeyFileName) {
		this.chinapnrPublicKeyFileName = chinapnrPublicKeyFileName;
	}

	public void setChinapnrNotifyPath(String chinapnrNotifyPath) {
		this.chinapnrNotifyPath = chinapnrNotifyPath;
	}

	public void setChinapnrRequestHost(String chinapnrRequestHost) {
		this.chinapnrRequestHost = chinapnrRequestHost;
	}

	public void setChinapnrRequestPath(String chinapnrRequestPath) {
		this.chinapnrRequestPath = chinapnrRequestPath;
	}

	public void setChinapnrRequestPort(String chinapnrRequestPort) {
		this.chinapnrRequestPort = chinapnrRequestPort;
	}

	public void setChinapnrRequestScheme(String chinapnrRequestScheme) {
		this.chinapnrRequestScheme = chinapnrRequestScheme;
	}

	public String getChinapnrPublicKeyFileName() {
		return chinapnrPublicKeyFileName;
	}

	public String getChinapnrNotifyPath() {
		return chinapnrNotifyPath;
	}

	public String getChinapnrRequestHost() {
		return chinapnrRequestHost;
	}

	public String getChinapnrRequestPath() {
		return chinapnrRequestPath;
	}

	public String getChinapnrRequestPort() {
		return chinapnrRequestPort;
	}

	public String getChinapnrRequestScheme() {
		return chinapnrRequestScheme;
	}

	public void setGlobalApplicationUrl(String globalApplicationUrl) {
		this.globalApplicationUrl = globalApplicationUrl;
	}

	public void setLocalApplicationUrl(String localApplicationUrl) {
		this.localApplicationUrl = localApplicationUrl;
	}

	public String getSpringcardNotifyPath() {
		return springcardNotifyPath;
	}

	public void setSpringcardNotifyPath(String springcardNotifyPath) {
		this.springcardNotifyPath = springcardNotifyPath;
	}

	public String getSpringcardRequestUrl() {
		return springcardRequestUrl;
	}

	public void setSpringcardRequestUrl(String springcardRequestUrl) {
		this.springcardRequestUrl = springcardRequestUrl;
	}

	public String getTenpayNotifyPath() {
		return tenpayNotifyPath;
	}

	public void setTenpayNotifyPath(String tenpayNotifyPath) {
		this.tenpayNotifyPath = tenpayNotifyPath;
	}

	public String getTenpayRequestUrl() {
		return tenpayRequestUrl;
	}

	public void setTenpayRequestUrl(String tenpayRequestUrl) {
		this.tenpayRequestUrl = tenpayRequestUrl;
	}

	public String getBill99NotifyPath() {
		return bill99NotifyPath;
	}

	public void setBill99NotifyPath(String bill99NotifyPath) {
		this.bill99NotifyPath = bill99NotifyPath;
	}

	public String getBill99RequestUrl() {
		return bill99RequestUrl;
	}

	public void setBill99RequestUrl(String bill99RequestUrl) {
		this.bill99RequestUrl = bill99RequestUrl;
	}

	public String getAlipayRequestUrl() {
		return alipayRequestUrl;
	}

	public void setAlipayRequestUrl(String alipayRequestUrl) {
		this.alipayRequestUrl = alipayRequestUrl;
	}

	public String getCcbRequestUrl() {
		return ccbRequestUrl;
	}

	public void setCcbRequestUrl(String ccbRequestUrl) {
		this.ccbRequestUrl = ccbRequestUrl;
	}

	public String getBill99PublicKeyFileName() {
		return bill99PublicKeyFileName;
	}

	public void setBill99PublicKeyFileName(String bill99PublicKeyFileName) {
		this.bill99PublicKeyFileName = bill99PublicKeyFileName;
	}

	public String getBill99GatewayNotifyPath() {
		return bill99GatewayNotifyPath;
	}

	public void setBill99GatewayNotifyPath(String bill99GatewayNotifyPath) {
		this.bill99GatewayNotifyPath = bill99GatewayNotifyPath;
	}

	public String getBill99GatewayRequestUrl() {
		return bill99GatewayRequestUrl;
	}

	public void setBill99GatewayRequestUrl(String bill99GatewayRequestUrl) {
		this.bill99GatewayRequestUrl = bill99GatewayRequestUrl;
	}

	public String getBill99GatewayPublicKeyFileName() {
		return bill99GatewayPublicKeyFileName;
	}

	public void setBill99GatewayPublicKeyFileName(
			String bill99GatewayPublicKeyFileName) {
		this.bill99GatewayPublicKeyFileName = bill99GatewayPublicKeyFileName;
	}

}
