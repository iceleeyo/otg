package com.springtour.otg.interfaces.transacting.facade.result;

import java.io.Serializable;

public class ChinapnrVoicePayRequestResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private String statusCode;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public ChinapnrVoicePayRequestResult(String statusCode) {
		this.statusCode = statusCode;
	}

	public ChinapnrVoicePayRequestResult() {
	}

}
