package com.springtour.otg.infrastructure.channel.tenpay;

import java.io.StringReader;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class TenpayAnalyseSynResponseForSingleTxnQuery {

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
			Element successFlagElement = root.element("retcode");
			if (!TenpayConstants.CHARGED_SUCCESS.equals(successFlagElement
					.getTextTrim())) {
				Element retmsg = root.element("retmsg");
				throw new IllegalArgumentException(
						"query response from tenpay is a failure: result code is ["
								+ successFlagElement.getTextTrim()
								+ "], result message is: ["
								+ retmsg.getTextTrim() + "]");
			}
			// continue check
			for (Object element : root.elements()) {
				if (!((Element) element).getTextTrim().equals("")) {
					messageReceived.put(((Element) element).getName(),
							((Element) element).getTextTrim());
				}

			}
		} catch (DocumentException e) {
			throw new IllegalArgumentException(
					"fail to parse from response XML");
		}
		return messageReceived;

	}

}
