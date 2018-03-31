package com.chinadrtv.common.exception;

public class PaffJmsException extends RuntimeException {

    private static final long serialVersionUID = -7791510797224107029L;

    public PaffJmsException() {
        super();
    }

    public PaffJmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaffJmsException(String message) {
        super(message);
    }

    public PaffJmsException(Throwable cause) {
        super(cause);
    }

}
