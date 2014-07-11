package com.springtour.otg.infrastructure.channel.icbc.object;


import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("pub")
public class IcbcQueryApiInfo {
	@XStreamAlias("APIName")
	private String apiName;
	@XStreamAlias("APIVersion")
	private String apiVersion;
}
