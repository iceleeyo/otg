package com.springtour.otg.application.exception;

/**
 * 未找到商户异常
 * 
 * @author Hippoom
 * 
 */
public class DuplicateMerchantException extends RuntimeException {
	private static final long serialVersionUID = 1L;
        //TODO 继承AbsractApplicationExceptionWithStatusCode
	public DuplicateMerchantException(String channelId, String orgId) {
		super(new StringBuilder("duplicate merchant:[channelId:").append(
				channelId).append(",").append("orgId:").append(orgId).append(
				"]").toString());
	}

}
