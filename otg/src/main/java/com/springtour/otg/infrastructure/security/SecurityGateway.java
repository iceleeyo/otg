package com.springtour.otg.infrastructure.security;

import javax.servlet.http.HttpServletRequest;

public interface SecurityGateway {
	public boolean authByLinkPage(HttpServletRequest request, String linkPage);

	public String getUserProperty(HttpServletRequest request, String getMethod);

	public boolean simpleauthorize(HttpServletRequest request);

	SessionUserSnapshot getUserFrom(HttpServletRequest request);

	boolean isAnAuthorized(HttpServletRequest request);

	boolean isAnUnexpired(HttpServletRequest request);
}
