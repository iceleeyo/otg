package com.springtour.otg.infrastructure.channel.alipaywap;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.springtour.otg.infrastructure.channel.alipaywap.httpclient.HttpProtocolHandler;
import com.springtour.otg.infrastructure.channel.alipaywap.httpclient.HttpRequest;
import com.springtour.otg.infrastructure.channel.alipaywap.httpclient.HttpResponse;
import com.springtour.otg.infrastructure.channel.alipaywap.httpclient.HttpResultType;
import com.springtour.otg.infrastructure.channel.alipaywap.sign.MD5;
import com.springtour.otg.infrastructure.channel.alipaywap.sign.RSA;

public class AlipayWapSubmit {

	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara,
			String key) {
		String prestr = AlipayWapCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		if (AlipayWapConfig.sign_type.equals("MD5")) {
			mysign = MD5.sign(prestr, key, AlipayWapConfig.input_charset);
		}
		if (AlipayWapConfig.sign_type.equals("0001")) {
			mysign = RSA.sign(prestr, AlipayWapConfig.private_key,
					AlipayWapConfig.input_charset);
		}
		return mysign;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	public static Map<String, String> buildRequestPara(
			Map<String, String> sParaTemp, String key) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayWapCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara, key);

		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign",mysign);
		if (!sPara.get("service").equals("alipay.wap.trade.create.direct")
				&& !sPara.get("service").equals(
						"alipay.wap.auth.authAndExecute")) {
			sPara.put("sign_type", AlipayWapConfig.sign_type);
		}

		return sPara;
	}

	public static String buildRequest(Map<String, String> sParaTemp, String key) {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, key);
		return AlipayWapCore.createLinkString(sPara);
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 * 
	 * @paramALIPAY_GATEWAY_NEW 支付宝网关地址
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 支付宝处理结果
	 * @throws Exception
	 */
	public static String buildRequest(String ALIPAY_GATEWAY_NEW, String key,
			String strParaFileName, String strFilePath,
			Map<String, String> sParaTemp) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, key);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(AlipayWapConfig.input_charset);

		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(ALIPAY_GATEWAY_NEW + "_input_charset="
				+ AlipayWapConfig.input_charset);

		HttpResponse response = httpProtocolHandler.execute(request,
				strParaFileName, strFilePath);
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(
			Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(),
					entry.getValue());
		}

		return nameValuePair;
	}

	/**
	 * 解析远程模拟提交后返回的信息，获得token
	 * 
	 * @param text
	 *            要解析的字符串
	 * @return 解析结果
	 * @throws Exception
	 */
	public static String getRequestToken(String text) throws Exception {
		String request_token = "";
		// 以“&”字符切割字符串
		String[] strSplitText = text.split("&");
		// 把切割后的字符串数组变成变量与数值组合的字典数组
		Map<String, String> paraText = new HashMap<String, String>();
		for (int i = 0; i < strSplitText.length; i++) {

			// 获得第一个=字符的位置
			int nPos = strSplitText[i].indexOf("=");
			// 获得字符串长度
			int nLen = strSplitText[i].length();
			// 获得变量名
			String strKey = strSplitText[i].substring(0, nPos);
			// 获得数值
			String strValue = strSplitText[i].substring(nPos + 1, nLen);
			// 放入MAP类中
			paraText.put(strKey, strValue);
		}

		if (paraText.get("res_data") != null) {
			String res_data = paraText.get("res_data");
			// 解析加密部分字符串（RSA与MD5区别仅此一句）
			if (AlipayWapConfig.sign_type.equals("0001")) {
				res_data = RSA.decrypt(res_data, AlipayWapConfig.private_key,
						AlipayWapConfig.input_charset);
			}

			// token从res_data中解析出来（也就是说res_data中已经包含token的内容）
			Document document = DocumentHelper.parseText(res_data);
			request_token = document.selectSingleNode(
					"//direct_trade_create_res/request_token").getText();
		}
		return request_token;
	}

	public static String buildQueryRequest(String queryUrl, String key,
			Map<String, String> sParaTemp) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, key);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(AlipayWapConfig.input_charset);

		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(queryUrl + "?_input_charset="
				+ AlipayWapConfig.input_charset);

		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}
}
