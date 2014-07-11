/**
 * 
 */
package com.springtour.stub;

import javax.servlet.http.*;

import com.springtour.otg.infrastructure.security.SecurityGateway;
import com.springtour.otg.infrastructure.security.SessionUserSnapshot;


/**
 * @author Hippoom
 *
 */
public class FuncItemActStub implements SecurityGateway {

	/* (non-Javadoc)
	 * @see com.spring.derbyhotel.defaultadapter.SpringtourFuncItemActService#authByLinkPage(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public boolean authByLinkPage(HttpServletRequest request, String linkPage) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUserProperty(HttpServletRequest request, String getMethod) {
		// TODO Auto-generated method stub
		return "111";
	}

	@Override
	public boolean simpleauthorize(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SessionUserSnapshot getUserFrom(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnAuthorized(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAnUnexpired(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
