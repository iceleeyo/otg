/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.exception;

/**
 *
 * @author Future
 */
public class CannotLaunchSecurityProcedureException extends AbstractCheckedApplicationExceptionWithStatusCode {
   
    
    public CannotLaunchSecurityProcedureException(String msg) {
        super(msg);
    }
    
    public CannotLaunchSecurityProcedureException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.CANNOT_LAUNCH_SECURITY_PROCEDURE;
    }
}
