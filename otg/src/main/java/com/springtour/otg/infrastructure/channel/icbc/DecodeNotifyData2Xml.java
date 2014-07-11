/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import cn.com.infosec.icbc.ReturnValue;

import com.springtour.otg.infrastructure.channel.icbc.object.IcbcPayResponse;
import com.thoughtworks.xstream.XStream;

/**
 * @author 005073
 * 
 */
public class DecodeNotifyData2Xml {
	public IcbcPayResponse decodeAndTransform(String notifyData) {
		String decNotifyData = new String(ReturnValue.base64dec(notifyData
				.getBytes()));
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("B2CRes", IcbcPayResponse.class);
		return (IcbcPayResponse)xStream.fromXML(decNotifyData);
	}
}
