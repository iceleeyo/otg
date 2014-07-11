package com.springtour.otg.infrastructure.channel.alipaywap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.channel.alipaywap.sign.MD5;
import com.springtour.otg.infrastructure.channel.alipaywap.sign.RSA;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayWapNotificationValidatorImpl implements
		NotificationValidator {

	@Setter
	private Configurations configurations;

	Logger log = LoggerFactory.getLogger();

	@Override
	public boolean validate(Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException {
		if (AlipayWapConfig.sign_type.equals("0001")) {
			originalNotification = decrypt(originalNotification);
		}
		// 获取是否是支付宝服务器发来的请求的验证结果
		String responseTxt = "true";
		try {
			// XML解析notify_data数据，获取notify_id
			Document document = DocumentHelper.parseText(originalNotification
					.get("notify_data"));
			String notifyId = document.selectSingleNode("//notify/notify_id")
					.getText();
			String partner = originalNotification.get("merchantCode");
			responseTxt = verifyResponse(notifyId, partner);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 获取返回时的签名验证结果
		String sign = "";
		if (originalNotification.get("sign") != null) {
			sign = originalNotification.get("sign");
		}
		boolean isSign = getSignVeryfy(originalNotification, sign, false);

		if (isSign || responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 解密
	 * 
	 * @param inputPara
	 *            要解密数据
	 * @return 解密后结果
	 */
	public static Map<String, String> decrypt(Map<String, String> inputPara) {
		try {
			inputPara.put("notify_data", RSA.decrypt(inputPara.get("notify_data"),
					AlipayWapConfig.private_key, AlipayWapConfig.input_charset));
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(),e);
		}
		return inputPara;
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @param isSort
	 *            是否排序
	 * @return 生成的签名结果
	 */
	private static boolean getSignVeryfy(Map<String, String> Params,
			String sign, boolean isSort) {
		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = AlipayWapCore.paraFilter(Params);
		// 获取待签名字符串
		String preSignStr = "";
		if (isSort) {
			preSignStr = AlipayWapCore.createLinkString(sParaNew);
		} else {
			preSignStr = AlipayWapCore.createLinkStringNoSort(sParaNew);
		}
		// 获得签名验证结果
		boolean isSign = false;
		if(AlipayWapConfig.sign_type.equals("MD5") ) {
        	isSign = MD5.verify(preSignStr, sign, Params.get("merchantKey"), AlipayWapConfig.input_charset);
        }
		if (AlipayWapConfig.sign_type.equals("0001")) {
			isSign = RSA.verify(preSignStr, sign,
					AlipayWapConfig.ali_public_key,
					AlipayWapConfig.input_charset);
		}
		return isSign;
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String verifyResponse(String notify_id, String partner) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String veryfy_url = configurations.getAlipayWapVerfyUrl() + "partner="
				+ partner + "&notify_id=" + notify_id;
		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String checkUrl(String urlvalue) {
		String inputLine = "";
		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.info("请进入weblogic 控制台，将主机验证名设置为无");
		}
		return inputLine;
	}

}
