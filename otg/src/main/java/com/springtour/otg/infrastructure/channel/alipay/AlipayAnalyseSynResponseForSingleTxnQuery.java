package com.springtour.otg.infrastructure.channel.alipay;

import java.io.StringReader;
import java.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class AlipayAnalyseSynResponseForSingleTxnQuery {

	/**
	 * 解析返回数据
	 * 
	 * @param text
	 * @return
	 */
	public TreeMap<String, String> analyzingSynchronousResults(String text) {
		TreeMap<String, String> messageReceived = new TreeMap<String, String>();
		try {
			StringReader reader = new StringReader(text);
			InputSource is = new InputSource(reader);
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(is);
			Element root = doc.getRootElement();
			Element successFlagElement = root.element("is_success");
			if (!AlipayConstants.QUERY_RESULT_SUCCESS.equals(successFlagElement
					.getTextTrim())) {
				throw new IllegalArgumentException(
						"query response from alipay is a failure: result code is ["
								+ successFlagElement.getTextTrim() + "]");
			}
			checkIfSignTypeSupported(root);
			// continue check
			Element tradeElement = root.element("response").element("trade");
			for (Object element : tradeElement.elements()) {
				messageReceived.put(((Element) element).getName(),
						((Element) element).getTextTrim());
			}
			messageReceived.put("sign", root.element("sign").getTextTrim());
		} catch (DocumentException e) {
			throw new IllegalArgumentException(
					"fail to parse from response XML");
		}
		return messageReceived;

	}

	private void checkIfSignTypeSupported(Element root) {
		String signType = root.element("sign_type").getTextTrim();
		if (!AlipayConstants.SIGN_TYPE.equals(signType)) {
			throw new IllegalArgumentException("Unsupported sign type:"
					+ signType);
		}
	}
}
