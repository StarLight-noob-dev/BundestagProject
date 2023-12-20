package org.texttechnologylab.nicolas.data.exceptions;

public class BundestagParameterException extends Exception {
    public BundestagParameterException() {
    }

    public BundestagParameterException(Throwable pCause) {
        super(pCause);
    }

    public BundestagParameterException(String pMessage) {
        super(pMessage);
    }

    public BundestagParameterException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
