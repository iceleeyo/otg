package com.springtour.otg.application.exception;

/**
 * 
 * 应用异常
 * 
 */
public abstract class AbstractCheckedApplicationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AbstractCheckedApplicationException(String message) {
        super(message);
    }
    
    public AbstractCheckedApplicationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
