package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("ICBCAPI")
public class IcbcQueryResponse {
	
	private IcbcQueryApiInfo pub;
	
	private IcbcQueryRequest in;
	
	private IcbcQueryResponseInfo out;

}
