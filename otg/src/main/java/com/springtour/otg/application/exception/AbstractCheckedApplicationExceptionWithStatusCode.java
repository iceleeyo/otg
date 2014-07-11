package com.springtour.otg.application.exception;

/**
 * 
 * 应用异常
 * 
 */
public abstract class AbstractCheckedApplicationExceptionWithStatusCode extends AbstractCheckedApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AbstractCheckedApplicationExceptionWithStatusCode(String message) {
        super(message);
    }

    public AbstractCheckedApplicationExceptionWithStatusCode(String msg, Throwable cause) {
        super(msg, cause);
    }

    public abstract String getStatusCode();
}
