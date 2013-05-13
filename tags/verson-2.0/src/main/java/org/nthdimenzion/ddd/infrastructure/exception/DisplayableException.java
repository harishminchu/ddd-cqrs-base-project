package org.nthdimenzion.ddd.infrastructure.exception;

import org.springframework.orm.hibernate3.HibernateSystemException;

public final class DisplayableException extends RuntimeException implements IBaseException{
    private final ErrorDetails errorDetails;
    public final static DisplayableException DEFAULT_UI_EXCEPTION = new DisplayableException();

    public DisplayableException() {
        this.errorDetails = new ErrorDetails.Builder("001").build();
    }

    public DisplayableException(IBaseException exception) {
        this.errorDetails = exception.getErrorDetails();
    }

    public DisplayableException havingCause(Throwable cause){
        this.errorDetails.setException(cause);
        if(cause instanceof HibernateSystemException){
            this.errorDetails.setErrorMessage(cause.getMessage());
        }
        return this;
    }

    @Override
    public Throwable getCause(){
        return errorDetails.getException();
    }

    @Override
    public ErrorDetails getErrorDetails() {
        return (ErrorDetails)errorDetails.clone();
    }

    @Override
    public String toString() {
        return errorDetails.toString();
    }
}
