package org.texttechnologylab.nicolas.data.exceptions;

public class DBException extends Exception{

    public DBException() {
    }

    public DBException(Throwable pCause) {
        super(pCause);
    }

    public DBException(String pMessage) {
        super(pMessage);
    }

    public DBException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
